package org.grejpfrut.tiller.entities;

import java.util.List;

import org.apache.lucene.document.Document;

/**
 * Article interface allows to manipulate text, cut it into separate paragraphs,
 * and transform into lucene's {@link Document}. Implementation may provide way
 * back and allow to transform Lucene document into {@link Article} objects.
 * 
 * @author Adam Dudczak
 * 
 */
public interface Article {

	/**
	 * Sets title for this article.
	 * @param title - title for this article.
	 */
	public void setTitle(String title);

	/**
	 * This method allows to get title.
	 */
	public String getTitle();

	/**
	 * @return Gets {@link List} of {@link Paragraph}s in original order.
	 */
	public List<Paragraph> getParagraphs();

	/**
	 * @return Gets {@link List} of {@link Sentence}s in original order.
	 */
	public List<Sentence> getSentences();

	/**
	 * @return Gets Lucene {@link Document} for this article.
	 */
	public Document getDocument();

	/**
	 * @return Gets {@link List} of {@link Token} without stop words.
	 */
	public List<Token> getTokens();

	/**
	 * @return Gets stemmed text as String.
	 */
	public String getStemmedText();

	/**
	 * @return Gets original text.
	 */
	public String getText();

	/**
	 * @param token
	 * @return Gets given token frequency in this sentence.
	 */
	public int getNumberOfOccur(Token token);
	


}
