package org.grejpfrut.wiki.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Used to create keywords ranking and get top keywords. This class
 * is used by {@link TFIDFCalc} to calculate  article's top keywords. 
 * @author ad
 *
 */
public class TFIDFRank {

    private List<TermScore> ranks = new ArrayList<TermScore>();

    /**
     * Checks if ranking contains given term.
     * @param term 
     * @return
     */
    public boolean containsTerm(String term) {
        for (TermScore tr : ranks) {
            if (tr.getTerm().equals(term))
                return true;
        }
        return false;
    }

    /**
     * Adds token to the ranking.
     * @param tr
     */
    public void addToken(TermScore tr) {
        ranks.add(tr);
    }

    /**
     * Gets top keywords from keywords list.
     * @param count - how many words.
     * @return
     */
    public List<TermScore> getKeywords(int count) {
        Collections.sort(ranks);
        if ( count > ranks.size() ) return ranks;
        return ranks.subList(0, count);
    }
}
