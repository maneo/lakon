package org.grejpfrut.lakon.summarizer.methods.lc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.grejpfrut.lakon.summarizer.utils.TokenPosition;
import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.utils.thesaurus.SynSet;
import org.grejpfrut.tiller.utils.thesaurus.SynSet.TermRelation;

/**
 * This class is used to build {@link LexicalChain}s. It represents distinct
 * interpretation of meaning of words used in text.
 * 
 * @author Adam Dudczak
 */
public class Interpretation implements Cloneable, Comparable<Interpretation> {

	private List<ChainElement> entries = new ArrayList<ChainElement>();

	/**
	 * synset -> set of chainElement connected by this sysnset
	 */
	private Map<Integer, Set<ChainElement>> connections = null;

	private boolean refreshConnections = false;

	/**
	 * Simple constructor, just to allow for construction of new
	 * {@link Interpretation}.
	 */
	public Interpretation(Token token, SynSet synset, int position) {
		ChainElement ce = new ChainElement(token, synset, position);
		this.entries.add(ce);
	}

	/**
	 * Simple constructor, just to allow for construction of new
	 * {@link Interpretation}.
	 */
	public Interpretation() {

	}

	/**
	 * This constructor used only by clone method.
	 * 
	 * @param entries
	 */
	private Interpretation(List<ChainElement> entries) {
		for (ChainElement ie : entries) {
			this.entries.add(ie.clone());
		}
	}

	@Override
	public Interpretation clone() {
		synchronized (this) {
			return new Interpretation(this.entries);
		}
	}

	/**
	 * @param token
	 * @param synset
	 * @return True if given token in given meaning is in this interpretation.
	 */
	public boolean containsMeaningOfToken(Token token, SynSet synset) {
		int index = this.entries.indexOf(new ChainElement(token, synset));
		if ( index == -1 ) return false;
		return this.entries.get(index).getSynsetId() == synset.getId();
	}

	/**
	 * @return Gets list of {@link ChainElement}s. Used for tests.
	 */
	List<ChainElement> getChainElements() {
		return this.entries;
	}

	/**
	 * This one checks whether given {@link Token} is in {@link Interpretation},
	 * if not it adds new entry to this interpretation. If exists
	 * 
	 * @param token -
	 *            token.
	 * @param synset -
	 *            token's synset.
	 * @param position - Integer.
	 */
	public boolean update(Token token, SynSet synset, int position) {
		ChainElement ie = new ChainElement(token, synset);
		int index = this.entries.indexOf(ie);
		if (index == -1) {
			// if this is new element you can add it
			ie.addPosition(position);
			this.entries.add(ie);
		} else {
			ChainElement oldIe = this.entries.get(index);
			// to avoid adding two meanings of the same word (see
			// ChainElement.equals)
			if (oldIe.getSynsetId() != synset.getId() )
				return false;
			oldIe.addPosition(position);
		}
		setRefreshConnections(true);
		return true;
	}

	/**
	 * Count connections between elements in this {@link Interpretation}.
	 * 
	 * @return number of connections.
	 */
	public int countConnections() {
		int count = 0;
		for (Set<ChainElement> ce : this.getConnections().values()) {
			count += ce.size();
		}
		return count;
	}
	/**
	 * 	Different interpretations from one paragraph will have similar
	 *	words frequencies (so we will ommit elements number of positions)
	 *	the most important thing is number of connected elements (per synset).
	 * @return 
	 */
	public int getScore(){
		int score = 0;
		for (Set<ChainElement> ces : this.getConnections().values()) {
			score += ces.size() - 1; 
		}
		return score;
	}
	
	/**
	 * This methods creates connections map representing relations in this
	 * {@link Interpretation}. This map contains pairs of related
	 * {@link ChainElement}, relation is resolved using {@link TermRelation}.
	 * 
	 * @return Connections between elements of this {@link Interpretation}.
	 */
	public Map<Integer, Set<ChainElement>> getConnections() {

		if (!refreshConnections && (this.connections != null))
			return this.connections;

		Map<Integer, Set<ChainElement>> connBuffer = new HashMap<Integer, Set<ChainElement>>();
		
		for (ChainElement elem : this.entries) {
			final int synsetId = elem.getSynsetId();
			Set<ChainElement> idForElem = connBuffer.get(synsetId);
			if (idForElem == null) {
				idForElem = new HashSet<ChainElement>();
				connBuffer.put(synsetId, idForElem);
			}
			idForElem.add(elem);
		}
		this.connections = connBuffer; 
//			new HashMap<Integer, Set<ChainElement>>();
//		for (Entry<Integer, Set<ChainElement>> entry : connBuffer.entrySet()) {
//			if (entry.getValue().size() > 1)
//				this.connections.put(entry.getKey(), entry.getValue());
//		}
		setRefreshConnections(false); // connections refreshed.
		return this.connections;
	}

	/**
	 * Sets {@link Interpretation#refreshConnections} to given value.
	 * 
	 * @param b
	 */
	private void setRefreshConnections(boolean b) {
		synchronized (this) {
			this.refreshConnections = b;
		}
	}

	@Override
	public String toString() {
		return this.entries.toString();
	}

	public int compareTo(Interpretation o) {

		int thisScore = this.getScore();
		int oScore = this.getScore();
		if (thisScore > oScore)
			return 1;
		else if (thisScore == oScore)
			return 0;
		return -1;

	}

}
