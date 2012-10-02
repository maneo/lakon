package org.grejpfrut.lakon.summarizer.methods.weights;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings.SignatureWords;
import org.grejpfrut.tiller.entities.Article;

/**
 * Abstract class providing skeleton for all term weigthening methods.
 * 
 * @author Adam Dudczak
 */
public abstract class AbstractWeight {

	protected static final String DEFAULT_TERM_FIELD_NAME = "text";

	protected static Logger logger = Logger.getLogger(AbstractWeight.class);

	protected IndexReader isWiki;

	protected Article article;

	protected String wikiIndex;
	
	protected SignatureWords wordsType = SignatureWords.NOUNS;

	/**
	 * Default number of keywords to find.
	 */
	public static final int HOW_MANY_KEYWORDS = -1;

	protected int howManyKeywords = HOW_MANY_KEYWORDS;

	public AbstractWeight(Settings settings, Article article) {
		this.article = article;
		if (settings.get(WeightSettings.P_NO_OF_KEYWORDS) != null)
			this.howManyKeywords = (Integer) settings
					.get(WeightSettings.P_NO_OF_KEYWORDS);
		this.wikiIndex = (String) settings
				.get(WeightSettings.P_REFERENCE_INDEX_DIR);

		String sigStr = (String) settings
				.get(WeightSettings.P_SIGNATURE_WORDS_TYPE);
		if (sigStr != null)
			this.wordsType = SignatureWords.valueOf(sigStr);

	}

	/**
	 * Gets keywords.
	 * 
	 * @return List of TermScore, list length is indicated by howManyKeywords
	 *         value.
	 */
	public abstract TermRanking getKeywords();

	/**
	 * @return Gets {@link IndexReader}.
	 * @throws IOException
	 */
	protected IndexReader getIndexReader() throws IOException {
		Directory wikiDir = FSDirectory.getDirectory(wikiIndex, false);
		return IndexReader.open(wikiDir);
	}

}
