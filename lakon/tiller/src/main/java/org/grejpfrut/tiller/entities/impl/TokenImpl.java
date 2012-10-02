package org.grejpfrut.tiller.entities.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.index.Term;
import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.utils.StringTools;

/**
 * Basic {@link Token} implementation.
 * 
 * @author Adam Dudczak
 * 
 */
public class TokenImpl implements Token {

	private final List<String> baseForms = new ArrayList<String>();

	private final String text;

	private final List<PartOfSpeech> info = new ArrayList<PartOfSpeech>();

	private boolean isStopWord = false;

	/**
	 * Token info method will return {@link PartOfSpeech#UNKNOWN} and baseform
	 * will be created from given token.
	 * 
	 * @param text
	 */
	public TokenImpl(String token) {
		String clean = StringTools.cleanToken(token);
		this.baseForms.add(clean);
		this.info.add(PartOfSpeech.UNKNOWN);
		this.text = token;
	}

	/**
	 * 
	 * @param text
	 * @param baseForms
	 * @param info -
	 *            can be null if so {@link PartOfSpeech#UNKNOWN} will be added
	 *            to info list.
	 */
	public TokenImpl(String text, List<String> baseForms,
			List<PartOfSpeech> info) {

		this.text = text;

		this.baseForms.addAll(baseForms);

		if (info != null)
			this.info.addAll(info);
		else {
			this.info.add(PartOfSpeech.UNKNOWN);
		}

	}

	public TokenImpl(String text, List<String> baseForms,
			List<PartOfSpeech> info, boolean isStopWord) {

		this.text = text;

		this.baseForms.addAll(baseForms);

		if (info != null)
			this.info.addAll(info);
		else {
			this.info.add(PartOfSpeech.UNKNOWN);
		}

		this.isStopWord = isStopWord;

	}

	public String getText() {
		return this.text;
	}

	public List<String> getBaseForms() {
		List<String> list = new ArrayList<String>(this.baseForms);
		return list;
	}

	public List<PartOfSpeech> getInfo() {
		List<PartOfSpeech> list = new ArrayList<PartOfSpeech>(this.info);
		return list;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Token) {
			Token token = (Token) obj;
			if (token != null) {
				if (token.getBaseForms().size() != this.getBaseForms().size())
					return false;
				return token.getBaseForms().containsAll(this.getBaseForms());
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.text.hashCode();
	}

	public boolean isStopWord() {
		return this.isStopWord;
	}

	public boolean setStopWord(boolean isStopWord) {
		return this.isStopWord = isStopWord;
	}

	@Override
	public String toString() {
		return this.getText();
	}

	public Term getTerm(String fld) {
		return new Term(fld, this.getBaseForms().get(0));
	}

	public String getBaseForm() {
		return this.getBaseForms().get(0);
	}

	public PartOfSpeech getFirstInfo() {
		return this.getInfo().get(0);
	}

}
