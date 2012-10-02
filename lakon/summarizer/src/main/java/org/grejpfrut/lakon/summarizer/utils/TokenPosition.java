package org.grejpfrut.lakon.summarizer.utils;

import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Paragraph;
import org.grejpfrut.tiller.entities.Token;

/**
 * Simple pojo, which stores the information about position of {@link Token} in
 * {@link Article}.
 * 
 * @author Adam Dudczak
 */
public class TokenPosition implements Cloneable, Comparable<TokenPosition> {

	private int sentence;

	private int position;

	/**
	 * @param paragraph -
	 *            position of {@link Token}'s {@link Paragraph}.
	 * @param sentence -
	 *            position of {@link Token}'s (@link Sentence}.
	 */
	public TokenPosition(int sentence, int position) {
		this.sentence = sentence;
		this.position = position;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TokenPosition) {
			TokenPosition tp = (TokenPosition) obj;
			if ((tp.getSentence() == this.getSentence())
					&& (tp.getPosition() == this.getPosition()))
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (" " + sentence + " " + position + " ").hashCode();
	}

	@Override
	public TokenPosition clone() {
		return new TokenPosition(this.sentence, this.position);
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getSentence() {
		return sentence;
	}

	public void setSentence(int sentence) {
		this.sentence = sentence;
	}

	public int compareTo(TokenPosition o) {
		if (this.sentence < o.sentence)
			return 1;
		if (this.sentence == o.getSentence()) {
			if (this.position < o.getPosition())
				return 1;
			if (this.position == o.getPosition())
				return 0;
		}
		return -1;
	}

}
