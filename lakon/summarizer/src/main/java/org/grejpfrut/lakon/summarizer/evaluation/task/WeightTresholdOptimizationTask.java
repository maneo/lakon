package org.grejpfrut.lakon.summarizer.evaluation.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.grejpfrut.lakon.summarizer.Summarizer;
import org.grejpfrut.lakon.summarizer.methods.WeightsBasedSummarizer;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings.SignatureWords;
import org.grejpfrut.tiller.entities.Article;

/**
 * Used to find the best value for minimal treshold of weights. 
 * @author Adam Dudczak
 */
public class WeightTresholdOptimizationTask extends EvaluateSummariesTask {
	
	protected double fraction = 1.0; 
	
	
	protected static Logger logger = Logger.getLogger(WeightTresholdOptimizationTask.class);

	public WeightTresholdOptimizationTask(Map values) {
		super(values);
		
	}

	@Override
	public String execute(int articleId) {
		
		this.fraction = 0.0;
		StringBuffer sb = new StringBuffer();
		while (Double.compare(fraction, 0.1) < 0) {
			logger.debug("fraction is:" + fraction);
			sb.append("fraction is:" + fraction+"\n");
			sb.append(super.execute(articleId)+"\n");
			fraction += 0.005;
		}
		return sb.toString();
	}

	@Override
	protected List<Summarizer> getSummarizers(Article art) {
		
		List<Summarizer> summs = new ArrayList<Summarizer>();
		
//		int length = (int) Math.round(art.getTokens().size() * fraction);
//		length =  length == 0 ? 1 : length;
//		logger.debug("fraction is :"+fraction+" number of keywords is: "+length);
//		
//		settings.put(WeightSettings.P_NO_OF_KEYWORDS, length);
		
		
		Settings bm25 = new WeightSettings(settings.getMap());
		bm25.put(WeightSettings.P_BM25_TRESHOLD, fraction);
		bm25.put(WeightSettings.P_WEIGHT_TYPE, WeightSettings.WeightType.BM_25
				.toString());
		bm25.put(WeightSettings.P_SIGNATURE_WORDS_TYPE, SignatureWords.NOUNS
				.toString());
		Summarizer bm25summ = new WeightsBasedSummarizer(bm25);
		summs.add(bm25summ);

		WeightSettings tfidf = new WeightSettings(settings.getMap());
		tfidf.put(WeightSettings.P_TFIDF_TRESHOLD, fraction);
		tfidf.put(WeightSettings.P_WEIGHT_TYPE, WeightSettings.WeightType.TFIDF
				.toString());
		tfidf.put(WeightSettings.P_SIGNATURE_WORDS_TYPE, SignatureWords.NOUNS
				.toString());
		Summarizer tfidfSumm = new WeightsBasedSummarizer(tfidf);
		summs.add(tfidfSumm);
		
		return summs;
	}

}
