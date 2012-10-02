package org.grejpfrut.tiller.utils;

/**
 * Simple interface with method which allows to perform various operation 
 * to clean markup.
 * @author Adam Dudczak
 *
 */
public interface MarkupCleaner {   
    /**
     * Finds matching markup tokens procceses them and returns
     * text cleaned from markup.
     * 
     * @param text
     * @return clean text.
     */
    public String processMatchingMarkup(String text);
}