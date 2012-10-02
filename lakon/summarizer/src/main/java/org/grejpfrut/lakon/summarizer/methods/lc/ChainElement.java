package org.grejpfrut.lakon.summarizer.methods.lc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.grejpfrut.lakon.summarizer.utils.TokenPosition;
import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.utils.thesaurus.SynSet;

/**
 * This class holds information of single entry in {@link Interpretation}. It
 * is also used by {@link LexicalChainsBuilder} to create {@link LexicalChain}.
 * 
 * @author Adam Dudczak
 */
public class ChainElement implements Cloneable {

	private Token token;

	private SynSet synset;

	private List<Integer> positions = new ArrayList<Integer>();

	/**
	 * Simple constructor.
	 * 
	 * TODO : remove this constructor, find a better way to compare SynSet and
	 * Token pair with existing ChainElements
	 * 
	 * @param token
	 * @param synset
	 */
	public ChainElement(Token token, SynSet synset) {
		this.token = token;
		this.synset = synset;
	}

	/**
	 * Simple constructor.
	 * 
	 * @param token
	 * @param synset
	 * @param position -
	 */
	public ChainElement(Token token, SynSet synset, int position) {
		this.token = token;
		this.synset = synset;
		this.positions.add(position);
	}

	/**
	 * Assumes that token and synset parameter won't change - so the same
	 * instance can br passed to created instance of {@link ChainElement}. We
	 * only copy list of positions.
	 * 
	 * This constructor is used by {@link ChainElement#clone()} method.
	 * 
	 * @param token
	 * @param synset
	 * @param positions
	 */
	protected ChainElement(Token token, SynSet synset,
			Collection<Integer> positions) {
		this.token = token;
		this.synset = synset;
		this.positions.addAll(positions);

	}

	@Override
	public ChainElement clone() {
		synchronized (this) {
			return new ChainElement(this.token, this.synset, this.positions);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ChainElement) {
			ChainElement ie = (ChainElement) obj;
			if (ie.getToken().equals(this.getToken()))
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getToken().hashCode()
				+ (" " + this.getSynset().getId()).hashCode();
	}

	/**
	 * Adds new token position.
	 * 
	 * @param position
	 */
	public void addPosition(int position) {
		this.positions.add(position);
	}

	/**
	 * Adds list of {@link TokenPosition}.
	 * 
	 * @param posList
	 */
	public void addPositions(Collection<Integer> posList) {
		this.positions.addAll(posList);
	}

	/**
	 * @return Gets list of all {@link ChainElement#token} occurences.
	 */
	public List<Integer> getPositions() {
		return positions;
	}

	/**
	 * @return Gets sorted list of all {@link ChainElement#token} occurences.
	 */
	public List<Integer> getSortedPositions() {
		Collections.sort(this.positions);
		return this.positions;
	}

	public SynSet getSynset() {
		return synset;
	}

	public void setSynset(SynSet synset) {
		this.synset = synset;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return token.toString() + "(" + this.positions.size() + ","
				+ this.getSynsetId() + ")";
	}

	public int getSynsetId() {
		return this.synset.getId();
	}

}
