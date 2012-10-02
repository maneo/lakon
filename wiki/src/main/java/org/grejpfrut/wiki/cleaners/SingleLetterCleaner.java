package org.grejpfrut.wiki.cleaners;

/**
 * TODO: JavaDoc 
 */
public class SingleLetterCleaner extends BasicCleanerImpl {

    private final static String SINGLE_LETTER_CLEANER = "\\s[a-zA-Z]\\s";
    
    public SingleLetterCleaner() {
        super(SINGLE_LETTER_CLEANER);
    }
    
    @Override
    public String processTokens(String token) {
        return " ";
    }
}
