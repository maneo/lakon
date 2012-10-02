package org.grejpfrut.tiller.analysis;

import org.grejpfrut.tiller.entities.Token;

/**
 * Allows to create {@link Token}s out of given words.
 * 
 * @author Adam Dudczak
 *
 */
public interface TillerTokenizer {
	
	/**
	 * Allows to create {@link Token}s out of given words.
	 * @param word - word.
	 * @return {@link Token} for given word.
	 */
	public Token getToken(String word);
	
}
