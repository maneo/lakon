package org.grejpfrut.tiller.utils.thesaurus;

import java.util.List;


/**
 * This class represents thesaurus synset entry, it makes distinction between
 * synonym, hypernym and hyponyms. Allows to get sysnset id.
 * 
 * @author Adam Dudczak
 */
public interface SynSet {

	/**
	 * Main types of relation between terms.
	 * 
	 * OTHER includes HYPONYMS and HYPERNYMS.
	 * 
	 * @author Adam Dudczak
	 */
	public enum TermRelation {
		SYNONYMS, OTHER, UNKNOWN
	};

	/**
	 * Gets relation between this {@link SynSet} and the one given in parameter.
	 * 
	 * @param synset.
	 * @return {@link TermRelation}.
	 */
	TermRelation getRelation(SynSet synset);

	/**
	 * Gets relation between this {@link SynSet} and the one given in parameter.
	 * 
	 * @param term -
	 *            String.
	 * @return {@link TermRelation}
	 */
	TermRelation getRelation(String term);

	/**
	 * @return Gets list of synonyms from this {@link SynSet}.
	 */
	List<String> getSyns();

	/**
	 * @return Gets list of hyponyms from this {@link SynSet}.
	 */
	List<String> getHyponyms();

	/**
	 * @return Gets list of hypernyms from this {@link SynSet}.
	 */
	List<String> getHypernyms();

	/**
	 * @return Gets list of hypernyms, hyponyms and synonyms from this
	 *         {@link SynSet}.
	 */
	List<String> getAll();

	/**
	 * Gets {@link SynSet} id in {@link Thesaurus}.
	 * @return
	 */
	int getId();

	/**
	 * Adds token as synonym to this {@link SynSet}.
	 * @param token
	 */
	void addSyn(String token);

	/**
	 * Adds token as hypernym to this {@link SynSet}.
	 * @param token
	 */
	void addHypernym(String token);

	/**
	 * Adds token as hyponym to this {@link SynSet}.
	 * @param token
	 */
	void addHyponym(String token);
	
	/**
	 * @return Gets list with hypernyms and hyponyms for this {@link SynSet}.
	 */
	public List<String> getOther();

}
