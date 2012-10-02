package org.grejpfrut.tiller.entities;

import java.util.List;

import org.apache.lucene.index.Term;

/**
 * Basic interface used to manipulate tokens. It allows to get base form of word
 * as well as stemmed form. Implementation should alos provide morphological
 * information about token (part of speech etc.)
 * 
 * @author Adam Dudczak
 * 
 */
public interface Token {
	
	public enum PartOfSpeech {
		NOUN, ADJECTIVE, VERB, UNKNOWN
	}
	
	/**
	 * @return true if this token is a stop word.
	 */
	public boolean isStopWord();
	
	/**
	 * @param isStopWord
	 * @return
	 */
	public boolean setStopWord(boolean isStopWord);
	

	/**
	 * @return Gets base from which this token was derived.
	 */
	public List<String> getBaseForms();

	/**
	 * @return Gets first base from which this token was derived.
	 */
	public String getBaseForm();

	/**
	 * @return Gets token orignal text.
	 */
	public String getText();

	/**
	 * @return Gets morphological information of base form of token.
	 */
	public List<PartOfSpeech> getInfo();
	
	
	/**
	 * @return Gets morphological information of base form of token.
	 */
	public PartOfSpeech getFirstInfo();

	
	/**
	 * 
	 * @param token
	 * @return
	 */
	public boolean equals(Object obj);
	
	/**
	 * Creates Lucene {@link Term} object.
	 * @param fld - field name where Term will be stored.
	 * @return
	 */
	public Term getTerm(String fld);


}
