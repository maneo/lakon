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
 * Calculate BM25 weights based term ranking.
 * 
 * @author Adam Dudczak
 */
public class BM25Weight extends AbstractWeight {

	private static final double DEFAULT_KFACTOR = 1.2;

	private double kFactor = DEFAULT_KFACTOR;

	private static final double DEFAULT_B_FACTOR = 0.75;

	private double bFactor = DEFAULT_B_FACTOR;

	private double aveDocLength;

	private double minBM25 = WeightSettings.DEFAULT_MIN_BM25;

	/**
	 * 
	 * @param articleTokens -
	 *            lucene directory with indexed article.
	 * @param globalDir -
	 *            lucene directory with index for wiki pages.
	 * @param howManyKeywords -
	 *            number of keywords to find.
	 */
	public BM25Weight(Settings settings, Article article) {
		super(settings, article);

		if (settings.get(WeightSettings.P_BM25_TRESHOLD) != null)
			this.minBM25 = (Double) settings
					.get(WeightSettings.P_BM25_TRESHOLD);

		if (settings.get(WeightSettings.P_AVE_DOC_LENGTH) != null)
			this.aveDocLength = (Double) settings
					.get(WeightSettings.P_AVE_DOC_LENGTH);
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

			// calc |D|
			final List<Token> tokens = article.getTokens();
			final int numberOfTermsInDocument = tokens.size();

			for (Token token : tokens) {

				if (!ranks.containsTerm(token)) {

					if ((this.wordsType == SignatureWords.NOUNS)
							&& (token.getInfo().get(0) != PartOfSpeech.NOUN))
						continue;

					double noOfOccur = article.getNumberOfOccur(token);
					double noOfDocs = isWiki.numDocs();
					double howManyContains = isWiki.docFreq(token
							.getTerm(DEFAULT_TERM_FIELD_NAME));
					double avgDocLength = this.aveDocLength;

					double termFreq = 0.0;
					double idf = 0.0;

					// tfidf
					if (numberOfTermsInDocument != 0)
						termFreq = noOfOccur / numberOfTermsInDocument;

					if ((howManyContains != 0) && (noOfDocs != 0))
						idf = Math.log((noOfDocs - howManyContains + 0.5)
								/ (howManyContains + 0.5));

					// bm25
					double numerator = termFreq * (kFactor + 1);
					double denominator = 0.0;
					if (avgDocLength != 0.0)
						denominator = termFreq
								+ kFactor
								* (1 - bFactor + bFactor
										* numberOfTermsInDocument
										/ avgDocLength);
					double bm25 = 0.0;
					if (denominator != 0.0)
						bm25 = idf * numerator / denominator;

					if (bm25 >= this.minBM25) {
						TermScore newRank = new TermScore(token, bm25);
						ranks.addToken(newRank);
					}
				}
			}
			this.isWiki.close();
		} catch (IOException e) {
			throw new RuntimeException("Exception while calculating tfidf.", e);
		}
		logger.debug("Number of keywords : "+ranks.getSize());
		return howManyKeywords == -1 ? ranks : new TermRanking(ranks
				.getKeywords(this.howManyKeywords));
	}

}
