package org.grejpfrut.lakon.summarizer.methods.weights;

import java.io.IOException;
import java.util.List;

import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings.SignatureWords;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.entities.Token.PartOfSpeech;

/**
 * Utility class to calculate tfidf rank for the whole article.
 * 
 * @author Adam Dudczak
 * 
 */
public class TFIDFWeight extends AbstractWeight {

	/**
	 * Default minimum value of TFIDF
	 */
	public static final double DEFAULT_MIN_TFIDF = 0.035;

	private double minTfidf = DEFAULT_MIN_TFIDF;

	/**
	 * 
	 * @param articleTokens -
	 *            lucene directory with indexed article.
	 * @param globalDir -
	 *            lucene directory with index for wiki pages.
	 * @param howManyKeywords -
	 *            number of keywords to find.
	 */
	public TFIDFWeight(Settings settings, Article article) {
		super(settings, article);
		this.article = article;
		if (settings.get(WeightSettings.P_TFIDF_TRESHOLD) != null)
			this.minTfidf = (Double) settings
					.get(WeightSettings.P_TFIDF_TRESHOLD);

		this.wikiIndex = (String) settings
				.get(WeightSettings.P_REFERENCE_INDEX_DIR);
	}

	/**
	 * Gets keywords.
	 * 
	 * @return List of TermScore, list length is indicated by howManyKeywords
	 *         value.
	 */
	public TermRanking getKeywords() {

		TermRanking ranks = new TermRanking();
		try {
			this.isWiki = super.getIndexReader();

			final List<Token> tokens = this.article.getTokens();
			int numberOfTermsInIndex = tokens.size();

			for (Token token : tokens) {
				if ( !ranks.containsTerm(token) ) {
					
					if ((this.wordsType == SignatureWords.NOUNS)
							&& (token.getInfo().get(0) != PartOfSpeech.NOUN))
						continue;
					
					double noOfOccur = article.getNumberOfOccur(token);
					double rank = tfidf(token, noOfOccur, numberOfTermsInIndex);
					if (rank >= this.minTfidf) {
						TermScore newRank = new TermScore(token, rank);
						ranks.addToken(newRank);
					}
				}
			}
			this.isWiki.close();
		} catch (IOException e) {
			throw new RuntimeException("Exception while calculating tfidf.", e);
		}
		logger.debug("Number of keywords : "+ranks.getSize());
		return howManyKeywords == -1 ? ranks : new TermRanking(ranks.getKeywords(this.howManyKeywords));
	}

	private double tfidf(Token token, double tokenFreq, int numberOfTerms) {

		double tf = 0;
		double idf = 0;
		try {
			double noOfDocs = isWiki.numDocs();
			double howManyContains = isWiki.docFreq(token
					.getTerm(DEFAULT_TERM_FIELD_NAME));

			if (numberOfTerms != 0)
				tf = tokenFreq / numberOfTerms;
			
			if ((howManyContains != 0) && (noOfDocs != 0))
				idf = Math.log(noOfDocs / howManyContains);

		} catch (IOException e) {
			logger.error("tfidf: error during searching index "
					+ e.getMessage());
		}
		return tf * idf;
	}

}
