package org.grejpfrut.tiller.entities;

import java.util.List;

/**
 * Paragraph interface holds all {@link Article}'s {@link Sentence}s.
 * Implementation should provide solution for splitting paragraph into
 * sentences.
 * 
 * @author Adam Dudczak
 * 
 */
public interface Paragraph {
	
	/**
	 * Title is optional for paragraph. 
	 * @return String with title of paragraph.
	 */
	public String getTitle();

	/**
	 * @return Gets {@link List} of {@link Sentence}s in original order.
	 */
	public List<Sentence> getSentences();
	
	/**
	 * @return Gets {@link List} of {@link Token} without stop words.
	 */
	public List<Token> getTokens();
	
	
	/**
	 * @return Gets stemmed text as String.
	 */
	public String getStemmedText();

	/**
	 * @return Gets original text as String.
	 */
	public String getText();

	/**
	 * @param token -
	 *            String.
	 * @return Gets given token frequency in this sentence.
	 */
	public int getNumberOfOccur(Token token);
	


	

}
