package org.grejpfrut.lakon.summarizer.evaluation;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.grejpfrut.lakon.summarizer.Summarizer;
import org.grejpfrut.lakon.summarizer.methods.LexicalChainsSummarizer;
import org.grejpfrut.lakon.summarizer.methods.LocationBasedSummarizer;
import org.grejpfrut.lakon.summarizer.methods.RandomSummarizer;
import org.grejpfrut.lakon.summarizer.methods.WeightsBasedSummarizer;
import org.grejpfrut.lakon.summarizer.settings.LexicalChainsSettings;
import org.grejpfrut.lakon.summarizer.settings.LocationSettings;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings.SignatureWords;
import org.grejpfrut.tiller.builders.ArticleBuilder;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Sentence;
import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.utils.TillerConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 
 * @author Adam Dudczak
 */
public class EvaluationTool {

	private final static Logger logger = Logger.getLogger(EvaluationTool.class);

	/**
	 * text id to summary length for this given article.
	 */
	private Map<Integer, Integer> summariesLength = new HashMap<Integer, Integer>();

	/**
	 * text id to summary length for this given article.
	 */
	private Map<Integer, Article> articles = new HashMap<Integer, Article>();

	private EvaluationsParser evaluations;

	private ArticleBuilder articleBuilder;

	public static void main(String[] args) {

		if (args.length != 1) {
			logger.fatal("please specify thesauri dir");
			return;
		}

		EvaluationTool tool = new EvaluationTool();
		Settings settings = new Settings();
		settings.put(LexicalChainsSettings.P_PATH_TO_THESAURI, args[0]);
		List<ReportEntry> report = tool.evaluate(settings);
		QualityReport qr = new QualityReport(report);
		// logger.info(qr.toString());

		// printUsers(tool);
		// printNoOfEvals(tool);

		// for ( int i = 1; i<11;i++)
		// tool.printSummary(i);

	}

	private void printSummary(int artId) {

		Article art = this.articles.get(artId);
		List<Evaluation> evalsForText = this.evaluations
				.getEvaluationsForText(artId);
		SummaryEvaluation se = new SummaryEvaluation(art, evalsForText);
		logger.info("Relevant sentences set size : "
				+ se.getRelevenatSentences().size());

		List<Sentence> senteces = art.getSentences();
		for (Entry<Integer, List<Integer>> entry : se.getSentenceRanking()
				.entrySet()) {
			for (Integer sentNo : entry.getValue()) {
				logger.info("(" + entry.getKey() + ") " + senteces.get(sentNo));
			}
		}

	}

	private static void printUsers(EvaluationTool tool) {
		List<String> users = tool.getEvaluations().getUsers();
		logger.info("users:" + users.size());
		for (String user : users) {
			logger.info(user);
		}
	}

	private static void printNoOfEvals(EvaluationTool tool) {
		Map<Integer, Integer> evaluations = tool.getEvaluations()
				.getEvaluationsSummary();
		logger.info("artId\t No ");
		for (Entry<Integer, Integer> entry : evaluations.entrySet()) {
			logger.info(entry.getKey() + "\t" + entry.getValue());
		}
	}

	public EvaluationTool() {

		this.articleBuilder = new ArticleBuilder(new TillerConfiguration());
		try {
			parse();
		} catch (Exception e) {
			throw new RuntimeException(
					"Exception while parsing evaluations-text.xml", e);
		}
		this.evaluations = new EvaluationsParser(this.articles, summariesLength);

	}

	public List<ReportEntry> evaluate(Settings settings) {

		List<ReportEntry> report = new ArrayList<ReportEntry>();
		for (Entry<Integer, Article> entry : this.articles.entrySet()) {

			if  ( entry.getKey() != 8) continue;
			
			int summaryLength = this.summariesLength.get(entry.getKey());

			settings.put(Settings.P_SUMMARY_LENGTH, new Integer(summaryLength));

			ReportEntry reportEntry = new ReportEntry(entry.getKey(),
					summaryLength);

			Article art = entry.getValue();
			List<Summarizer> summs = this.getSummarizers(settings, art);

			List<Evaluation> evalsForText = this.evaluations
					.getEvaluationsForText(entry.getKey());
			SummaryEvaluation se = new SummaryEvaluation(art, evalsForText);

			for (Summarizer summ : summs) {
				List<Sentence> summary = summ.summarize(art);
				List<Sentence> topSentences = se.getTopSentences(summaryLength);
				reportEntry.put(summ.name(), summary);
				reportEntry.calculateCoverage(summ.name(), topSentences);
			}
			report.add(reportEntry);
		}
		logger.info(new QualityReport(report).toString());
		return report;

	}

	private void parse() throws ParserConfigurationException, SAXException,
			IOException {

		InputStream is = this.getClass().getResourceAsStream(
				"/evaluations-texts.xml");

		if (is != null) {

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();

			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(new InputSource(is));

			NodeList nodes = document.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node aNode = nodes.item(i);
				if ("article".equals(aNode.getNodeName())) {

					int id = Integer.parseInt(((Element) aNode)
							.getAttribute("id"));
					NodeList childs = aNode.getChildNodes();

					String title = null;
					String text = null;
					int length = -1;

					for (int j = 0; j < childs.getLength(); j++) {
						Node node = childs.item(j);
						if (node.getNodeType() != Node.ELEMENT_NODE)
							continue;
						String value = node.getFirstChild().getTextContent();
						if ("length".equals(node.getNodeName())) {
							length = Integer.parseInt(value);
						} else if ("text".equals(node.getNodeName())) {
							text = value;
						} else if ("title".equals(node.getNodeName())) {
							title = value;
						}
					}
					Article art = this.articleBuilder.getArcticle(text, title);
					this.articles.put(id, art);
					this.summariesLength.put(id, length);
				}
			}
		}

	}

	/**
	 * Creates {@link Summarizer}s.
	 * 
	 * @param art
	 * 
	 * @return Gets list of {@link Summarizer} used in this evaluation.
	 */
	public List<Summarizer> getSummarizers(Settings settings, Article art) {

		List<Summarizer> summs = new ArrayList<Summarizer>();

		Summarizer random = new RandomSummarizer(settings);
		summs.add(random);

		Settings firstSettings = new LocationSettings(settings.getMap());
		firstSettings.put(LocationSettings.P_LOCATION_MODE,
				LocationSettings.LocationMode.FIRST.toString());
		Summarizer lead = new LocationBasedSummarizer(firstSettings);
		summs.add(lead);

		Settings lsettings = new LocationSettings(settings.getMap());
		lsettings.put(LocationSettings.P_LOCATION_MODE,
				LocationSettings.LocationMode.FIRST_IN_PARAGRAPHS.toString());
		Summarizer location = new LocationBasedSummarizer(lsettings);
		summs.add(location);

		Settings bm25 = new WeightSettings(settings.getMap());
		bm25.put(WeightSettings.P_WEIGHT_TYPE, WeightSettings.WeightType.BM_25
				.toString());
		bm25.put(WeightSettings.P_SIGNATURE_WORDS_TYPE, SignatureWords.ALL
				.toString());
		Summarizer bm25summ = new WeightsBasedSummarizer(bm25);
		summs.add(bm25summ);

		WeightSettings tfidf = new WeightSettings(settings.getMap());
		tfidf.put(WeightSettings.P_WEIGHT_TYPE, WeightSettings.WeightType.TFIDF
				.toString());
		tfidf.put(WeightSettings.P_SIGNATURE_WORDS_TYPE, SignatureWords.ALL
				.toString());
		Summarizer tfidfSumm = new WeightsBasedSummarizer(tfidf);
		summs.add(tfidfSumm);

		Settings lc = new LexicalChainsSettings(settings.getMap());
		lc.put(LexicalChainsSettings.P_REPRESENTATIVE_MODE,
				LexicalChainsSettings.Representatives.OFF.toString());
		lc.put(LexicalChainsSettings.P_ONLY_NOUNS, Boolean.FALSE.toString());
		Summarizer chainer = new LexicalChainsSummarizer(lc);
		summs.add(chainer);

		return summs;

	}

	public EvaluationsParser getEvaluations() {
		return this.evaluations;
	}

}
