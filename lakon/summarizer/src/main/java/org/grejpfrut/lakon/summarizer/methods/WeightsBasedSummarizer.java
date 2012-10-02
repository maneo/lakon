package org.grejpfrut.lakon.summarizer.methods;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.grejpfrut.lakon.summarizer.SummarizerBase;
import org.grejpfrut.lakon.summarizer.methods.weights.AbstractWeight;
import org.grejpfrut.lakon.summarizer.methods.weights.BM25Weight;
import org.grejpfrut.lakon.summarizer.methods.weights.TFIDFWeight;
import org.grejpfrut.lakon.summarizer.methods.weights.TermRanking;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings.SignatureWords;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings.WeightType;
import org.grejpfrut.tiller.builders.ArticleBuilder;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Sentence;
import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.entities.Token.PartOfSpeech;
import org.grejpfrut.tiller.utils.TillerConfiguration;

/**
 * Summarizer which uses term weights technics to get important phrases.
 * 
 * @author Adam Dudczak
 */
public class WeightsBasedSummarizer extends SummarizerBase {
	
	private final static Logger logger = Logger.getLogger(WeightsBasedSummarizer.class);

	private static final String TOKENIZER_CLASS = "org.grejpfrut.tiller.analysis.MorpheusTokenizer";

	private WeightType weightType = WeightType.TFIDF;


	public WeightsBasedSummarizer(Settings settings) {
		super(settings);

		WeightType weightT = WeightType.valueOf((String) super.settings
				.get(WeightSettings.P_WEIGHT_TYPE));
		if (weightT != null)
			this.weightType = weightT;

	}

	@Override
	protected List<Sentence> prepareSummary(Article article) {

		List<Sentence> sentences = article.getSentences();
		List<Sentence> result = new ArrayList<Sentence>();

		// calculate tfidf for selected tokens
		AbstractWeight weight = getWeight(article);

		TermRanking ranks = weight.getKeywords();
		logger.debug(ranks);
		logger.debug(this.weightType);

		// calculate sentence weights
		SortedMap<Double, List<Integer>> weights = new TreeMap<Double, List<Integer>>(
				SummarizerBase.DESC_COMPARATOR);

		for (int i = 0; i < sentences.size(); i++) {
			double sentenceWeight = 0.0;
			// sum weights of tokens in sentence
			for (Token token : sentences.get(i).getTokens()) {
				sentenceWeight += ranks.getTokenWeight(token);
			}
			// save sentence weight
			List<Integer> weightsEntry = weights.get(sentenceWeight);
			if (weightsEntry == null) {
				weightsEntry = new ArrayList<Integer>();
				weights.put(sentenceWeight, weightsEntry);
			}
			weightsEntry.add(i);
			logger.debug(" ("+sentenceWeight+")"+sentences.get(i));

		}
		// get first n sentences numbers, and sort them in original order.
		TreeSet<Integer> sortedSentences = new TreeSet<Integer>();
		for (Entry<Double, List<Integer>> entry : weights.entrySet()) {
			for (Integer value : entry.getValue()) {
				sortedSentences.add(value);
				if ((--length) == 0)
					break;
			}
			if (length == 0)
				break;
		}
		// sentences are now sorted, put them to result
		for (Integer i : sortedSentences) {
			result.add(sentences.get(i));
		}

		return result;

	}

	/**
	 * Gets {@link AbstractWeight}implementation.
	 * 
	 * @param art -
	 *            {@link Article}.
	 * @return Gets {@link AbstractWeight}implementation.
	 */
	private AbstractWeight getWeight(Article art) {
		if (this.weightType == WeightType.BM_25)
			return new BM25Weight(super.settings, art);
		else {
			return new TFIDFWeight(super.settings, art);
		}
	}

	@Override
	protected Article getArticle(String text) {
		TillerConfiguration conf = new TillerConfiguration();
		conf.setTokenizerClassName(TOKENIZER_CLASS);
		ArticleBuilder ab = new ArticleBuilder(conf);
		return ab.getArcticle(text);
	}

	@Override
	public String name() {
		return super.name() + "-" + weightType;
	}

}
