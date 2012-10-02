package org.grejpfrut.lakon.summarizer.methods.weights;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.grejpfrut.tiller.entities.Token;

/**
 * Used to create keywords ranking and get top keywords. This class is used by
 * {@link TFIDFWeight} to calculate article's top keywords.
 * 
 * @author Adam Dudczak
 * 
 */
public class TermRanking {

	private Set<TermScore> ranks = new TreeSet<TermScore>();
	
	/**
	 *
	 */
	public TermRanking() {
		
	}
	
	/**
	 * @param scores
	 */
	public TermRanking(Collection<TermScore> scores ){
		
		ranks.addAll(scores);
		
	}

	/**
	 * Checks if ranking contains given term.
	 * @param token
	 * @return false if not contains
	 */
	public boolean containsTerm(Token token) {
		for (TermScore tr : ranks) {
			if (tr.getToken().equals(token))
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param token
	 * @return 0.0 if {@link TermRanking} does not contain given {@link Token}.
	 */
	public double getTokenWeight(Token token) {
		for (TermScore tr : ranks) {
			if (tr.getToken().equals(token))
				return tr.getScore();
		}
		return 0.0;
		
	}

	/**
	 * Adds token to the ranking.
	 * 
	 * @param tr
	 */
	public void addToken(TermScore tr) {
		ranks.add(tr);
	}

	/**
	 * Gets top keywords from keywords list.
	 * 
	 * @param count -
	 *            how many words.
	 * @return top count keywords.
	 */
	public List<TermScore> getKeywords(long count) {
		final List<TermScore> scores = new ArrayList<TermScore>();
		if (count > ranks.size()) {
			scores.addAll(ranks);
			return scores;
		}
		Iterator<TermScore> iter = ranks.iterator();
		while (count > 0) {
			scores.add(iter.next());
			count--;
		}
		return scores;
	}
	
	/**
	 * @return Gets the number of keywords in this ranking.
	 */
	public int getSize() {
		return this.ranks.size();
	}

	/**
	 * @return gets all keywords.
	 */
	public List<TermScore> getKeywords() {
		final List<TermScore> scores = new ArrayList<TermScore>();
		scores.addAll(ranks);
		return scores;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for ( TermScore score : this.ranks){
			sb.append ("("+score.getToken()+" : "+score.getScore()+") ");
		}
		return sb.toString();
	}

}
