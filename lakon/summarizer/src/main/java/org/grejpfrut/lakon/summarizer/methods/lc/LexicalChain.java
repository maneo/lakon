package org.grejpfrut.lakon.summarizer.methods.lc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Class representing lexical chain.
 * 
 * @author Adam Dudczak
 */
public class LexicalChain implements Comparable<LexicalChain> {

	private List<ChainElement> elements = new ArrayList<ChainElement>();

	private int synsetId;

	/**
	 * See {@link LexicalChainsBuilder}.
	 */
	LexicalChain(Collection<ChainElement> elements) {
		this.elements.addAll(elements);
		this.synsetId = this.elements.get(0).getSynsetId();
	}

	/**
	 * See {@link LexicalChainsBuilder}.
	 */
	LexicalChain(ChainElement element) {
		this.elements.add(element);
	}

	/**
	 * See {@link LexicalChainsBuilder}.
	 */
	LexicalChain(LexicalChain chain) {
		this.elements.addAll(chain.getElements());
	}

	/**
	 * @return Unmodifiable list with elements of this chain.
	 */
	public List<ChainElement> getElements() {
		return Collections.unmodifiableList(elements);
	}

	/**
	 * Gets list of representative elements. Element is a representative when it
	 * occurrs more than average number of occurences for members of this chain.
	 * 
	 * @return list of representative {@link ChainElement}s.
	 */
	public List<ChainElement> getRepresentativeElements() {

		List<ChainElement> res = new ArrayList<ChainElement>();
		if (this.elements.size() == 0)
			return res;

		int aveOccur = 0;
		for (ChainElement elem : this.elements) {
			aveOccur += elem.getPositions().size();
		}
		aveOccur = Math.round(aveOccur / this.elements.size());

		for (ChainElement elem : this.elements) {
			if (elem.getPositions().size() >= aveOccur)
				res.add(elem);
		}
		return res;

	}

	/**
	 * @return Gets score for this chain.
	 */
	public double getScore() {

		double length = 0;
		for (ChainElement element : this.elements) {
			length += element.getPositions().size();
		}
		if (length != 0) {
			int d = this.elements.size();
			double homogenity = 1.0 - (d / length);
			return length * homogenity + d;
		}
		return 0.0;

	}

	/**
	 * Allows to addition of list of {@link ChainElement}s.
	 * 
	 * @param elems
	 */
	void addElements(List<ChainElement> elems) {
		for (ChainElement elem : elems) {
			this.addElement(elem);
		}
	}

	/**
	 * If given element exists in this {@link LexicalChain} this method will add
	 * new element's positions entries to existing ones.
	 * 
	 * If given element is new, this method will search for entry related to
	 * this element, when related entry will be found, method will add element
	 * to this {@link LexicalChain}.
	 * 
	 * @param element
	 */
	boolean addElement(ChainElement element) {

		int index = this.elements.indexOf(element);
		if (index != -1) {
			ChainElement oldEntry = this.elements.get(index);
			if (oldEntry.getSynsetId() != element.getSynsetId())
				return false;
			oldEntry.addPositions(element.getPositions());
			return true;
		} else {
			List<ChainElement> toAdd = new ArrayList<ChainElement>();
			for (ChainElement oldElem : this.elements) {
				if (oldElem.getSynsetId() == element.getSynsetId()) {
					toAdd.add(element);
					break;
				}
			}
			if (toAdd.size() == 0)
				return false;
			this.elements.addAll(toAdd);
		}
		return true;
	}

	@Override
	public String toString() {
		return this.elements.toString();
	}

	/**
	 * Simple comparator.
	 */
	public int compareTo(LexicalChain o) {
		double oScore = o.getScore();
		double thisScore = this.getScore();
		if (oScore > thisScore)
			return 1;
		else if (oScore == thisScore)
			return 0;
		return -1;
	}

	/**
	 * @return Gets synset id which represents elements of this chain.
	 */
	public int getSynsetId() {
		return synsetId;
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof LexicalChain))
			return false;
		LexicalChain second = (LexicalChain) obj;
		if (second.getSynsetId() != this.synsetId)
			return false;

		List<ChainElement> otherElements = second.getElements();
		if (otherElements.size() != this.elements.size())
			return false;

		if (this.elements.containsAll(otherElements))
			return true;

		return false;
	}

	@Override
	public int hashCode() {
		return (this.elements.toString() + this.synsetId + "").hashCode();
	}

}
