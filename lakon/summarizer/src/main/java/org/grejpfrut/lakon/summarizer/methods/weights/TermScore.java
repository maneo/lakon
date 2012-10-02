package org.grejpfrut.lakon.summarizer.methods.weights;

import org.grejpfrut.tiller.entities.Token;

/**
 * Basic class used to store term and his tfidf score. Allows to compare term scores, used by 
 * {@link TermRanking} to create ranking.
 * @author ad
 *
 */
public class TermScore implements Comparable{

    private Token token;
    private double score;
    
    public TermScore(Token token, double result){
        this.token = token;
        score = result;
    }
    
    public int compareTo(Object o) {
        TermScore tr = (TermScore)o;
        if(this.getScore() > tr.getScore()) return -1;
        else if ( this.getScore() == tr.getScore() ) return 0;
        return 1;
    }
    
    
    /**
     * @return Gets {@link Token} associated with this {@link TermScore}.
     */
    public Token getToken(){
        return token;
    }
    
    /**
     * @return Score (as double) associated with this {@link TermScore}.
     */
    public double getScore(){
        return score;
    }

    @Override
    public String toString() {
        return token+ " score:"+score;
    }

}
