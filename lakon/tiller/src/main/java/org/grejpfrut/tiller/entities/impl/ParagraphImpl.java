package org.grejpfrut.tiller.entities.impl;

import java.util.ArrayList;
import java.util.List;

import org.grejpfrut.tiller.entities.Paragraph;
import org.grejpfrut.tiller.entities.Sentence;
import org.grejpfrut.tiller.entities.Token;

/**
 * Simple implementation of {@link Paragraph} interface.
 * 
 * @author Adam Dudczak
 * 
 */
public class ParagraphImpl implements Paragraph {

	/**
	 * An array of {@link Sentence}s which builds up this {@link Paragraph}.
	 */
	private final List<Sentence> sentences = new ArrayList<Sentence>();

	/**
	 * An array of {@link Token}s which builds up this {@link Paragraph}.
	 */
	private List<Token> tokens = null;

	/**
	 * Optional title of this {@link Paragraph}.
	 */
	private String title = null;

	/**
	 * @param sentences -
	 *            sentences
	 */
	public ParagraphImpl(List<Sentence> sentences) {

		this.sentences.addAll(sentences);

	}

	/**
	 * 
	 * @param sentences -
	 *            sentences
	 * @param title -
	 *            title for this paragaph.
	 */
	public ParagraphImpl(List<Sentence> sentences, String title) {

		this.sentences.addAll(sentences);
		this.title = title;

	}

	/**
	 * Gets an {@link ParagraphImpl#sentences}.
	 */
	public List<Sentence> getSentences() {
		return this.sentences;
	}

	/**
	 * Gets stemmed text for this {@link Paragraph}.
	 */
	public String getStemmedText() {
		StringBuffer sb = new StringBuffer();
		final int length = this.sentences.size();
		for (int i = 0; i < length; i++) {
			sb.append(this.sentences.get(i).getStemmedText());
			if (i < (length - 1))
				sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * Gets human-readable text for this {@link Paragraph}.
	 */
	public String getText() {
		StringBuffer sb = new StringBuffer();
		if ( this.title != null )
			sb.append(title + "\n");
		final int length = this.sentences.size();
		for (int i = 0; i < this.sentences.size(); i++) {
			sb.append(this.sentences.get(i).getText());
			if (i < (length - 1))
				sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * Gets number of occurance of given token.
	 */
	public int getNumberOfOccur(Token token) {

		if (this.tokens == null)
			setTokens();

		int count = 0;
		for (Token tok : this.tokens) {
			if (tok.equals(token))
				count++;
		}
		return count;
	}

	/**
	 * Gets an array of {@link Token}. If {@link ParagraphImpl#tokens} is null
	 * method invokes {@link ParagraphImpl#setTokens()} to create tokens array.
	 */
	public List<Token> getTokens() {
		if (this.tokens == null)
			setTokens();
		return this.tokens;
	}

	/**
	 * Gathers all {@link Tokens} from paragraph's {@link Sentence}s and sets
	 * {@link ParagraphImpl#tokens} field.
	 */

	private void setTokens() {
		List<Token> tks = new ArrayList<Token>();
		for (Sentence sentence : this.sentences) {
			tks.addAll(sentence.getTokens());
		}
		this.tokens = tks;
	}

	/**
	 * Gets paragraph title. 
	 */
	public String getTitle() {
		return this.title;
	}
	
	@Override
	public String toString() {
		return this.getText();
	}

}
