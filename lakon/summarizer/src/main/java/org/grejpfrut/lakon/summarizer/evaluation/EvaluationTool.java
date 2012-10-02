package org.grejpfrut.lakon.summarizer.evaluation;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.grejpfrut.lakon.summarizer.evaluation.task.EvaluationTask;
import org.grejpfrut.lakon.summarizer.evaluation.task.ListRelevantSetTask;
import org.grejpfrut.lakon.summarizer.settings.LexicalChainsSettings;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.tiller.builders.ArticleBuilder;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.utils.TillerConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This class runs all  evalution tasks
 * @author Adam Dudczak
 */
public class EvaluationTool {

	/**
	 * text id to summary length for this given article.
	 */
	private Map<Integer, Integer> summariesLength = new HashMap<Integer, Integer>();

	/**
	 * text id to summary length for this given article.
	 */
	private Map<Integer, Article> articles = new HashMap<Integer, Article>();

	/**
	 * Used to parse evaluations.
	 */
	private EvaluationsParser evaluations;

	/**
	 * Article builder used to create articles.
	 */
	private ArticleBuilder articleBuilder;
	
	/**
	 * Logger for this class.
	 */
	private final static Logger logger = Logger.getLogger(EvaluationTool.class);


	public static void main(String[] args) {

		if (args.length != 1) {
			logger.fatal("please specify thesauri dir");
			return;
		}

		EvaluationTool tool = new EvaluationTool();
		Settings settings = new Settings();
		settings.put(LexicalChainsSettings.P_PATH_TO_THESAURI, args[0]);
		tool.evaluate(args[0]);

	}

	/**
	 * Simple constructor.
	 */
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
	
	private void evaluate(String pathToThesauri){
	
		Map values = new HashMap();
		values.put(EvaluationTask.PARSER_KEY, evaluations);
		values.put(EvaluationTask.LENGTHS_KEY, summariesLength);
		values.put(EvaluationTask.ARTICLES_KEY, articles);
		values.put(LexicalChainsSettings.P_PATH_TO_THESAURI, pathToThesauri);
	
//    	ListUsersTask ls = new ListUsersTask(values);
//    	logger.info(ls.execute(1));
//
		logger.info("----------------------------\n relevant set with sentences \n-------------------------------\n");
		ListRelevantSetTask lrst = new ListRelevantSetTask(values);
		for ( int i = 1; i <= 10; i++ )	
			logger.info(lrst.execute(i));
//    	
//    	NumberOfEvalsTask noet = new NumberOfEvalsTask(values);
//    	logger.info(noet.execute(1));
//
//    	RelevanceSetDistributionTask rsd = new RelevanceSetDistributionTask(values);
//    	logger.info(rsd.execute(1));
//
//		logger.info("----------------------------\n evaluating summaries \n-------------------------------\n");
//    	EvaluateSummariesTask est = new EvaluateSummariesTask(values);
//    	logger.info(est.execute(1));
    	

//		logger.info("----------------------------\n users ranking \n-------------------------------\n");
//		ListUsersRankingTask lurt = new ListUsersRankingTask(values);
//    	logger.info(lurt.execute(1));


//		logger.info("-------------------------------\n weight optimization \n-------------------------------\n");
//		WeightTresholdOptimizationTask wto = new WeightTresholdOptimizationTask(values);
//			logger.info(wto.execute(1));
//		

//		logger.info("----------------------------\n no of interpretation \n-------------------------------\n");
//		NoInterpretationsTask noot = new NoInterpretationsTask(values);
//		logger.info(noot.execute(1));

//		EvaluationsForArticleTask efat = new EvaluationsForArticleTask(values);
//		logger.info(efat.execute(1));
		
//		UserOverlapTask uot = new UserOverlapTask(values);
//		for ( int i = 1; i <= 10; i++ )
//			logger.info(uot.execute(i));
    	
		
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

	public EvaluationsParser getEvaluations() {
		return this.evaluations;
	}

}
