package org.grejpfrut.tiller.entities.impl;

import java.util.ArrayList;
import java.util.List;

import org.grejpfrut.tiller.entities.Sentence;
import org.grejpfrut.tiller.entities.Token;

/**
 * This is a simple implementation of {@link Sentence}.
 * 
 * @author Adam Dudczak
 * 
 */
public class SentenceImpl implements Sentence {

	/**
	 * Default end of sentence sign in Polish.
	 */
	private static final String DEFAULT_SENTENCE_END = ".";

	/**
	 * Tokens which builds up this {@link Sentence}.
	 */
	private final List<Token> tokens = new ArrayList<Token>();

	/**
	 * If sentence ends with semicolon this should be true.
	 */
	private final String endChar;

	/**
	 * @param tokens -
	 *            tokens.
	 */
	public SentenceImpl(List<Token> tokens) {
		this.tokens.addAll(tokens);
		this.endChar = DEFAULT_SENTENCE_END;
	}

	/**
	 * @param tokens -
	 *            tokens.
	 * @param isEquivalent -
	 *            if sentence ends with semicolon this should be true.
	 */
	public SentenceImpl(List<Token> tokens, String endChar) {
		this.tokens.addAll(tokens);
		this.endChar = endChar;
	}

	/**
	 * Gets stemmed text for this {@link Sentence}.
	 */
	public String getStemmedText() {

		StringBuffer sb = new StringBuffer();
		final int length = this.tokens.size();
		for (int i = 0; i < length; i++) {
			if (this.tokens.get(i).isStopWord())
				continue;
			sb.append(tokens.get(i).getBaseForms().get(0));
			if (i < (length - 1))
				sb.append(" ");

		}
		return sb.toString();

	}

	/**
	 * Gets human-readable text of this {@link Sentence}.
	 */
	public String getText() {
		StringBuffer sb = new StringBuffer();
		final int length = this.tokens.size();
		for (int i = 0; i < length; i++) {
			sb.append(this.tokens.get(i).getText());
			if (i < (length - 1))
				sb.append(" ");
		}
		String sentence = sb.toString();
		return sentence.endsWith(".") ? sentence : sentence + this.endChar;
	}

	/**
	 * Gets tokens.
	 */
	public List<Token> getTokens() {
		List<Token> tks = new ArrayList<Token>();
		tks.addAll(this.tokens);
		return tks;
	}

	/**
	 * Gets number of occurance of given token.
	 */
	public int getNumberOfOccur(Token token) {

		int count = 0;
		for (Token tkn : this.tokens) {
			if (tkn.equals(token))
				count++;
		}
		return count;

	}

	@Override
	public String toString() {
		return this.getText();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SentenceImpl) {
			return ((SentenceImpl) obj).getText().equals(this.getText());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getText().hashCode();
	}

	
}
