package org.grejpfrut.tiller.utils.thesaurus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of {@link SynSet} interface.
 * 
 * @author Adam Dudczak
 */
public class SynSetImpl implements SynSet, Serializable {

	/**
	 * This class is used as a part of {@link Thesaurus} class, so it can be
	 * serialized with whole instnace of {@link Thesaurus}. See
	 * {@link ThesaurusFactory#saveThesaurus(Thesaurus, String). 
	 */
	private static final long serialVersionUID = -7690184936033648953L;

	/**
	 * List of synonyms.
	 */
	private List<String> syns = new ArrayList<String>();

	/**
	 * List of hypernyms.
	 */
	private List<String> hypernyms = new ArrayList<String>();

	/**
	 * List of hyponyms.
	 */
	private List<String> hyponyms = new ArrayList<String>();

	/**
	 * Should be unique {@link SynSet} identifier - based on position in
	 * {@link Thesaurus}.
	 */
	private int id = -1;

	/**
	 * Creates {@link SynSetImpl} instance.
	 * 
	 * @param entry -
	 *            leading token.
	 * @param id -
	 *            id for this {@link SynSet}.
	 */
	public SynSetImpl(String entry, int id) {
		syns.add(entry);
		this.id = id;
	}

	public List<String> getAll() {
		List<String> all = new ArrayList<String>();
		all.addAll(this.syns);
		all.addAll(this.hypernyms);
		all.addAll(this.hyponyms);
		return all;
	}

	public List<String> getHyponyms() {
		List<String> hps = new ArrayList<String>();
		hps.addAll(hyponyms);
		return hps;
	}

	public List<String> getHypernyms() {
		List<String> hyps = new ArrayList<String>();
		hyps.addAll(this.hypernyms);
		return hyps;
	}

	public List<String> getSyns() {
		List<String> synsCp = new ArrayList<String>();
		synsCp.addAll(this.syns);
		return synsCp;
	}

	/**
	 * @return Gets list with hypernyms and hyponyms for this {@link SynSet}.
	 */
	public List<String> getOther() {
		List<String> otherCp = new ArrayList<String>();
		otherCp.addAll(this.hypernyms);
		otherCp.addAll(this.hyponyms);
		return otherCp;
	}

	public void addHypernym(String token) {
		if (!this.hypernyms.contains(token))
			this.hypernyms.add(token);
	}

	public void addHyponym(String token) {
		if (!this.hyponyms.contains(token))
			this.hyponyms.add(token);
	}

	public void addSyn(String token) {
		if (!this.syns.contains(token))
			this.syns.add(token);
	}

	public int getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return this.getAll().toString();
	}

	public TermRelation getRelation(SynSet synset) {

		if (this.id != synset.getId())
			return TermRelation.UNKNOWN;

		List<String> buffer = this.getSyns();
		buffer.retainAll(synset.getSyns());
		if (buffer.size() != 0)
			return TermRelation.SYNONYMS;

		return TermRelation.OTHER;
	}

	public TermRelation getRelation(String term) {
		if (this.syns.contains(term))
			return TermRelation.SYNONYMS;
		else if (this.hypernyms.contains(term))
			return TermRelation.OTHER;
		else if (this.hyponyms.contains(term))
			return TermRelation.OTHER;
		return TermRelation.UNKNOWN;
	}

}
