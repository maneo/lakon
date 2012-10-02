package org.grejpfrut.tiller.utils.thesaurus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class represents thesaurus,
 * 
 * @author Adam Dudczak
 */
public class Thesaurus implements Serializable {

	/**
	 * Because of long time of initialization this class can be serialized. See
	 * {@link ThesaurusFactory#saveThesaurus(Thesaurus, String)}.
	 */
	private static final long serialVersionUID = 3515115915206807240L;

	/**
	 * Index with all terms appearing in thesaurus.
	 */
	private Map<String, List<Integer>> index = new HashMap<String, List<Integer>>();

	/**
	 * List of all synsets.
	 */
	private List<SynSet> synsets;

	/**
	 * Allows only for in-package access, use {@link ThesaurusFactory} methods
	 * to create instance.
	 * 
	 * This constructor creates reverse index for all term in thesauri, it can
	 * take a while...
	 * 
	 * @param synsets -
	 *            list of synsets.
	 */
	Thesaurus(List<SynSet> synsets, Map<String, List<Integer>> index) {

		this.synsets = synsets;
		this.index.putAll(index);

	}

	/**
	 * @param term -
	 *            term.
	 * @return Gets synset for given term.
	 */
	public List<SynSet> getSynSets(String term) {
		List<Integer> occurs = this.index.get(term);
		List<SynSet> syns = new ArrayList<SynSet>();
		if (occurs == null)
			return syns;
		for (Integer occur : occurs) {
			syns.add(this.synsets.get(occur));
		}
		return syns;
	}

	/**
	 * @param id -
	 *            {@link SynSet} id.
	 * @return Gets {@link SynSet} by his id.
	 */
	public SynSet getById(int id) {
		return this.synsets.get(id);
	}
}
