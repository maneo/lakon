package org.grejpfrut.wiki.utils;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.store.Directory;

/**
 * Utility class to calculate tfidf rank for the whole article.
 * 
 * @author ad
 * 
 */
public class TFIDFCalc {

	private static Logger logger = Logger.getLogger(TFIDFCalc.class);

	private IndexReader isRam;

	private IndexReader isWiki;

	private Directory articleDir;

	private Directory wikiDir;

	/**
	 * Default number of keywords to find.
	 */
	public static final int HOW_MANY_KEYWORDS = 20;

	/**
	 * Default minimum value of TFIDF
	 */
	public static final double DEFAULT_MIN_TFIDF = 0.001;

	private int homManyKeywords = HOW_MANY_KEYWORDS;

	private double minTfidf = DEFAULT_MIN_TFIDF;

	/**
	 * 
	 * @param articleDir -
	 *            lucene directory with indexed article.
	 * @param globalDir -
	 *            lucene directory with index for wiki pages.
	 * @param howManyKeywords -
	 *            number of keywords to find.
	 */
	public TFIDFCalc(Directory articleDir, Directory globalDir,
			int howManyKeywords, double minTFIDF) {

		this.articleDir = articleDir;
		this.wikiDir = globalDir;
		this.homManyKeywords = howManyKeywords;
		this.minTfidf = minTFIDF;
	}

	/**
	 * Gets keywords.
	 * 
	 * @return List of TermScore, list length is indicated by howManyKeywords
	 *         value.
	 */
	public List<TermScore> getKeywords() {

		TFIDFRank ranks = new TFIDFRank();
		try {
			this.isRam = IndexReader.open(this.articleDir);
			this.isWiki = IndexReader.open(this.wikiDir);

			TermEnum tenum = isRam.terms();
			TermFreqVector tfreq = isRam.getTermFreqVector(0, "text");
			int numberOfTermsInIndex = tfreq.size();

			while (tenum.next()) {
				if (!ranks.containsTerm(tenum.term().text())) {
					double termfreq = tfreq.getTermFrequencies()[tfreq
							.indexOf(tenum.term().text())];
					double rank = tfidf(tenum.term(), termfreq,
							numberOfTermsInIndex);
					if (rank >= this.minTfidf) {
						TermScore newRank = new TermScore(tenum.term().text(),
								rank);
						ranks.addToken(newRank);
					}
				}
			}
			this.isRam.close();
			this.isWiki.close();

		} catch (IOException e) {
			throw new RuntimeException("Something is wrong with RamDirectory");
		}

		return ranks.getKeywords(this.homManyKeywords);

	}

	private double tfidf(Term token, double tokenFreq, int numberOfTerms) {

		double tf = 0;
		double idf = 0;
		try {
			double noOfDocs = isWiki.maxDoc();
			double howManyContains = isWiki.docFreq(token);

			double noOfWords = numberOfTerms;

			if (noOfWords != 0)
				tf = tokenFreq / noOfWords;
			else
				noOfWords = 0;
			if ((howManyContains != 0) && (noOfDocs != 0))
				idf = Math.log(noOfDocs / howManyContains);
			else
				idf = 0;

		} catch (IOException e) {
			logger.error("tfidf: error during searching index "
					+ e.getMessage());
		}
		return tf * idf;

	}

	/**
	 * Simple getter for howManyKeywords.
	 * 
	 * @return how many keywords.
	 */
	public int getHomManyKeywords() {
		return homManyKeywords;
	}

	/**
	 * Simple setter.
	 * 
	 * @param homManyKeywords
	 */
	public void setHomManyKeywords(int homManyKeywords) {
		this.homManyKeywords = homManyKeywords;
	}

}
