package org.grejpfrut.wiki.cleaners;

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