package org.grejpfrut.wiki.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.dawidweiss.stemmers.Stempelator;

public class StempelStemmer extends Stemmer{

    private static Logger logger = Logger.getLogger(StempelStemmer.class);

    private Stempelator stemmer;

    public StempelStemmer(Set<String> iwList) {
        super.ignoredWords = iwList;
        init();
    }
    
    public StempelStemmer(){
        init();
    }
    
    private void init(){
        try {
            stemmer = new Stempelator();
        } catch (IOException e) {
            logger.fatal("Exception during stemmer initialization");
            throw new RuntimeException(e);
        }
    }

    public String doStem(String article) {
        return this.getDocAsStems(article).trim();
    }
    
    public String getDocAsStems(String article){
        List<String> st = new ArrayList<String>();
        StringTokenizer stok = new StringTokenizer(article, " ");
        while (stok.hasMoreTokens()) {
            String token = stok.nextToken();
            if (isTokenAllowed(token)&&!containsInvalidChars(token)) {
                String[] res = stemmer.stem(token);
                if (res != null) {
                    try {
                        st.add(res[0]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        logger.error("No stem for this phrase: " + token);
                    }
                }
            }
        }
        StringBuffer res = new StringBuffer();
        for(String s : st){
            res.append(s+" ");
        }
        return res.toString();
    }

}
