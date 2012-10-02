package org.grejpfrut.tiller.entities;

import java.util.List;

/**
 * Sentence interface provides API to get sentence's tokens. It allows to get
 * base text, as well as stemmed text without stop words.
 * {@link Sentence#getTokens()} should return {@link List} with {@link Token}s in
 * their original order. 
 * 
 * @author Adam Dudczak
 * 
 */
public interface Sentence {

	/**
	 * @return Gets {@link List} of {@link Token} without stop words.
	 */
	public List<Token> getTokens();
	
	/**
	 * @return Gets stemmed text as (without stop words) String.
	 */
	public String getStemmedText();

	/**
	 * @return Gets text as String.
	 */
	public String getText();

	/**
	 * @param token -
	 *            String.
	 * @return Gets given token frequency in this sentence.
	 */
	public int getNumberOfOccur(Token token);
	




}
