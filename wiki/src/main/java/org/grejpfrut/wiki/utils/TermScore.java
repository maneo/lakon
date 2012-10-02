package org.grejpfrut.wiki.utils;

/**
 * Basic class used to store term and his tfidf score. Allows to compare term scores, used by 
 * {@link TFIDFRank} to create ranking.
 * @author ad
 *
 */
public class TermScore implements Comparable{

    private String term;
    private double tfidf;
    
    public TermScore(String term, double result){
        this.term = term;
        tfidf = result;
    }
    
    public int compareTo(Object o) {
        TermScore tr = (TermScore)o;
        if(this.getTfidf() > tr.getTfidf()) return -1;
        else if ( this.getTfidf() == tr.getTfidf() ) return 0;
        return 1;
    }
    
    public String getTerm(){
        return term;
    }
    
    public double getTfidf(){
        return tfidf;
    }

    @Override
    public String toString() {
        return term+ " score:"+tfidf;
    }

}
