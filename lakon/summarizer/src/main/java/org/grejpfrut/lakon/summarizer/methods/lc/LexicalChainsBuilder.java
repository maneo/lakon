package org.grejpfrut.lakon.summarizer.methods.lc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Operation on {@link LexicalChain}s.
 * 
 * @author Adam Dudczak
 */
public class LexicalChainsBuilder {

	/**
	 * This method creates {@link LexicalChain}s using given
	 * {@link Interpretation}.
	 * 
	 * @param interpretation.
	 * @return List of {@link LexicalChain}s.
	 */
	public static Collection<LexicalChain> getChains(
			Interpretation interpretation) {

		Map<Integer, Set<ChainElement>> connections = interpretation
				.getConnections();
		Set<LexicalChain> distinctChains = new HashSet<LexicalChain>();
		if (connections.size() == 0)
			return distinctChains;

		for (Set<ChainElement> elements : connections.values()) {
			LexicalChain lc = new LexicalChain(elements);
			distinctChains.add(lc);
		}
		return distinctChains;
	}

	/**
	 * We assume that Strong Chains are those whose score is higher than average
	 * score (from all chains in given list).
	 * 
	 * @param chains -
	 *            list of {@link LexicalChain}.
	 * @return List with Strong Chains.
	 */
	public static List<LexicalChain> getStrongChains(Set<LexicalChain> chains) {

		if (chains.size() == 0)
			return new ArrayList<LexicalChain>(chains);

		double summScores = 0.0;
		for (LexicalChain chain : chains) {
			summScores += chain.getScore();
		}
		double aveScore = summScores / chains.size();
		List<LexicalChain> strongChains = new ArrayList<LexicalChain>();
		for (LexicalChain chain : chains) {
			if (chain.getScore() >= aveScore)
				strongChains.add(chain);
		}
		return strongChains;
	}

	/**
	 * Tries to connect two {@link LexicalChain}s if fails it will return null;
	 * 
	 * @param chain1
	 * @param chain2
	 * @return new {@link LexicalChain} or null.
	 */
	public static LexicalChain connectChains(LexicalChain chain1,
			LexicalChain chain2) {

		LexicalChain chain = new LexicalChain(chain1);
		boolean connection = false;
		for (ChainElement entry : chain2.getElements()) {
			if (chain.addElement(entry))
				connection = true;
		}
		if (connection)
			return chain;
		return null;
	}

	/**
	 * This method removes all chains which are part of longer ones eg. castle -
	 * fortress is a subchain of castle - fortress - stronghold.
	 * 
	 * @param chains
	 * @return Set of chains without subchains.
	 */
	public static Set<LexicalChain> removeSubchains(Set<LexicalChain> chains) {

		Set<LexicalChain> toRemove = new HashSet<LexicalChain>();
		for (LexicalChain chain : chains) {
			for (LexicalChain subchain : chains) {
				if (subchain == chain)
					continue;

				if (subchain.getSynsetId() == chain.getSynsetId()) {
					LexicalChain duplicate = subchain.getScore() > chain
							.getScore() ? subchain : chain;
					toRemove.add(duplicate);
				}
			}
		}
		chains.removeAll(toRemove);
		return chains;
	}

}
