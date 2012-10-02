package org.grejpfrut.wiki;

import java.util.StringTokenizer;

import junit.framework.TestCase;

public class WordSplittingTest extends TestCase {
    
    public void test1(){
        String test = "jestem jestem jestem ciekaw czy on dobrze jestem zliczy te wystapienia jestem";
        int no = countWord("jestem",test);
        assertEquals(no,5);
    }

    private int countWord(String word, String art) {

        int count = 0;
        StringTokenizer st = new StringTokenizer(art, " ");
        while (st.hasMoreElements()) {
            if (st.nextToken().equals(word))
                count++;
        }
        return count;
    }
    
}
