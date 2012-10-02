package org.grejpfrut.tiller.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.entities.impl.TokenImpl;
import org.grejpfrut.tiller.utils.StringTools;
import org.grejpfrut.tiller.utils.TillerConfiguration;

/**
 * This implementation of {@link TillerTokenizer} doesn't stems input words it
 * only marks given words as stop words if they are at
 * {@link StopWordsTokenizer#stopWords} list.
 * 
 * @author ad
 * 
 */
public class StopWordsTokenizer implements TillerTokenizer {

	/**
	 * List of stop words.
	 */
	private final Set<String> stopWords;

	/**
	 * Creates an instance of {@link StopWordsTokenizer}. It creates
	 * {@link StopWordsTokenizer#stopWords} list.
	 * 
	 * @param conf -
	 *            should be {@link TillerConfiguration}.
	 */
	public StopWordsTokenizer(TillerConfiguration conf) {

		this.stopWords = conf.getStopWords();

	}

	/**
	 * Gets {@link Token} from given words. This method sets token's base form
	 * with input word (in lower case), and puts no information about word. If
	 * word is a stop word it marks this.
	 */
	public Token getToken(String word) {

		List<String> baseForms = new ArrayList<String>();
		baseForms.add(StringTools.cleanToken(word));
		Token tk = new TokenImpl(word, baseForms, null);
		if (isStopWord(baseForms.get(0)))
			tk.setStopWord(true);
		return tk;

	}

	/**
	 * Checks whehter given word is a stop word.
	 * 
	 * @param word
	 * @return true if input is a stop word, false otherwise.
	 */
	protected boolean isStopWord(String word) {
		return this.stopWords.contains(word);
	}


}
