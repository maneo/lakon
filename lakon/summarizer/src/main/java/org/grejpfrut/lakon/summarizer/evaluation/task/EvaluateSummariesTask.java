package org.grejpfrut.lakon.summarizer.evaluation.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.grejpfrut.lakon.summarizer.Summarizer;
import org.grejpfrut.lakon.summarizer.evaluation.Evaluation;
import org.grejpfrut.lakon.summarizer.evaluation.QualityReport;
import org.grejpfrut.lakon.summarizer.evaluation.ReportEntry;
import org.grejpfrut.lakon.summarizer.evaluation.SummaryEvaluation;
import org.grejpfrut.lakon.summarizer.evaluation.ReportEntry.Mesaure;
import org.grejpfrut.lakon.summarizer.evaluation.SummaryEvaluation.Relevance;
import org.grejpfrut.lakon.summarizer.methods.LexicalChainsSummarizer;
import org.grejpfrut.lakon.summarizer.methods.LocationBasedSummarizer;
import org.grejpfrut.lakon.summarizer.methods.RandomSummarizer;
import org.grejpfrut.lakon.summarizer.methods.WeightsBasedSummarizer;
import org.grejpfrut.lakon.summarizer.methods.weights.AbstractWeight;
import org.grejpfrut.lakon.summarizer.settings.LexicalChainsSettings;
import org.grejpfrut.lakon.summarizer.settings.LocationSettings;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings.SignatureWords;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Sentence;

/**
 * 
 * @author Adam Dudczak
 */
public class EvaluateSummariesTask extends EvaluationTask {

	/**
	 * Size of probe when testing random summarizer.
	 */
	public static final int PROBE_SIZE_FOR_RANDOM_SUMMARIZER = 30;
	
	protected static Logger logger = Logger.getLogger(EvaluateSummariesTask.class);

	protected Settings settings;

	public EvaluateSummariesTask(Map values) {
		super(values);

		String pathToThesauri = (String) values
				.get(LexicalChainsSettings.P_PATH_TO_THESAURI);
		String onlyNouns = (String) values
				.get(WeightSettings.P_SIGNATURE_WORDS_TYPE);
		settings = new Settings();
		settings.put(LexicalChainsSettings.P_PATH_TO_THESAURI, pathToThesauri);
		if (onlyNouns != null) {
			settings.put(WeightSettings.P_SIGNATURE_WORDS_TYPE,
					SignatureWords.NOUNS);
			settings.put(LexicalChainsSettings.P_ONLY_NOUNS, Boolean.TRUE);
		}
	}

	@Override
	public String execute(int articleId) {

		List<ReportEntry> report = new ArrayList<ReportEntry>();
		SortedMap<Integer, Map<String, Long>> executionTimes = new TreeMap<Integer, Map<String, Long>>();

		for (Entry<Integer, Article> entry : this.articles.entrySet()) {

			int summaryLength = this.summariesLength.get(entry.getKey());

			settings.put(Settings.P_SUMMARY_LENGTH, new Integer(summaryLength));

			ReportEntry reportEntry = new ReportEntry(entry.getKey(),
					summaryLength);
			
			Article art = entry.getValue();
			List<Summarizer> summs = getSummarizers(art);

			List<Evaluation> evalsForText = parser.getEvaluationsForText(entry
					.getKey());
			SummaryEvaluation se = new SummaryEvaluation(art, evalsForText);
			int probeSize = 1;
			
			Map<String, Long> executionTimesForArt = executionTimes.get(entry.getKey());
			if ( executionTimesForArt == null ){
				executionTimesForArt = new HashMap<String, Long>();
				executionTimes.put(entry.getKey(), executionTimesForArt);
			}


			for (Summarizer summ : summs) {

				long start = System.currentTimeMillis();

				if (summ instanceof RandomSummarizer)
					probeSize = PROBE_SIZE_FOR_RANDOM_SUMMARIZER;
				else
					probeSize = 1;

				logger.debug("art: "+ entry.getKey()+" method name: "+summ.name());

				double coverage = 0.0;
				double recall = 0.0;
				double precision = 0.0;

				for (int i = 0; i < probeSize; i++) {
					List<Sentence> summary = summ.summarize(art);
					reportEntry.put(summ.name(), summary);
					List<Sentence> topSentences = se
							.getTopSentences(summaryLength);
					coverage += reportEntry.calculateCoverage(summ.name(),
							topSentences);
					List<Sentence> relevantSentences = se
							.getRelevantSentences(Relevance.AVERAGE);
					precision += reportEntry.calculatePrecision(summ.name(),
							relevantSentences);
					recall += reportEntry.calculateRecall(summ.name(),
							relevantSentences);
				}
				long end = System.currentTimeMillis();
				executionTimesForArt.put(summ.name(),  end - start);
				
				reportEntry.setValue(summ.name(), Mesaure.COVERAGE, coverage
						/ probeSize);
				reportEntry.setValue(summ.name(), Mesaure.RECALL, recall
						/ probeSize);
				reportEntry.setValue(summ.name(), Mesaure.PRECISION, precision
						/ probeSize);

			}
			report.add(reportEntry);
		}
		String execTimesString = getExecTimesString(executionTimes);
		return new QualityReport(report).toString()+"\n"+execTimesString;
	}

	
	private String getExecTimesString(SortedMap<Integer, Map<String, Long>> executionTimes) {
		
		StringBuffer sb = new StringBuffer();
		boolean header = false;
		for ( Entry<Integer,Map<String, Long>> entry : executionTimes.entrySet()){
			if (!header){
				sb.append("\n execution times \nid\t");
				for ( String method : entry.getValue().keySet() ){
					sb.append(method+"\t");
				}
				sb.append("\n");
				header = true;
			}
			sb.append(entry.getKey()+"\t");
			for ( Entry<String, Long> methodEntry : entry.getValue().entrySet() )
			{
				sb.append(methodEntry.getValue()+"\t");
			}
			sb.append("\n");
		}
		return sb.toString();
		
	}

	/**
	 * Creates instances of summarizers for current task.
	 * 
	 * @param art
	 * @return
	 */
	protected List<Summarizer> getSummarizers(Article art) {

		List<Summarizer> summs = new ArrayList<Summarizer>();

//		int numberOfKeywors = (int) Math.round(art.getTokens().size() * 0.15);
//		numberOfKeywors =  numberOfKeywors == 0 ? 1 : numberOfKeywors;
		
		//settings.put(WeightSettings.P_NO_OF_KEYWORDS, -1);
		
		Settings lc = new LexicalChainsSettings(settings.getMap());
		lc.put(LexicalChainsSettings.P_REPRESENTATIVE_MODE,
				LexicalChainsSettings.Representatives.OFF.toString());
		lc.put(LexicalChainsSettings.P_ONLY_NOUNS, Boolean.TRUE.toString());
		lc.put(LexicalChainsSettings.P_MAX_NUMBER_OF_INTERPRETATIONS,
				new Integer(20));
		Summarizer chainer = new LexicalChainsSummarizer(lc);
		summs.add(chainer);
//
//		Summarizer random = new RandomSummarizer(settings.createClone());
//		summs.add(random);
//
//		Settings firstSettings = new LocationSettings(settings.getMap());
//		firstSettings.put(LocationSettings.P_LOCATION_MODE,
//				LocationSettings.LocationMode.FIRST.toString());
//		Summarizer lead = new LocationBasedSummarizer(firstSettings);
//		summs.add(lead);
//
//		Settings lsettings = new LocationSettings(settings.getMap());
//		lsettings.put(LocationSettings.P_LOCATION_MODE,
//				LocationSettings.LocationMode.FIRST_IN_PARAGRAPHS.toString());
//		Summarizer location = new LocationBasedSummarizer(lsettings);
//		summs.add(location);
//
//		Settings bm25 = new WeightSettings(settings.getMap());
//		bm25.put(WeightSettings.P_BM25_TRESHOLD, 0.005);
//		bm25.put(WeightSettings.P_WEIGHT_TYPE, WeightSettings.WeightType.BM_25
//				.toString());
//		bm25.put(WeightSettings.P_SIGNATURE_WORDS_TYPE, SignatureWords.NOUNS
//				.toString());
//		Summarizer bm25summ = new WeightsBasedSummarizer(bm25);
//		summs.add(bm25summ);

//		WeightSettings tfidf = new WeightSettings(settings.getMap());
//		tfidf.put(WeightSettings.P_WEIGHT_TYPE, WeightSettings.WeightType.TFIDF
//				.toString());
//		tfidf.put(WeightSettings.P_SIGNATURE_WORDS_TYPE, SignatureWords.NOUNS
//				.toString());
//		tfidf.put(WeightSettings.P_TFIDF_TRESHOLD, 0.04);
//		Summarizer tfidfSumm = new WeightsBasedSummarizer(tfidf);
//		summs.add(tfidfSumm);

		return summs;
	}

}
