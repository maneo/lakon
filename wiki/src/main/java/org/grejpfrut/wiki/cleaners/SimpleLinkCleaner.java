package org.grejpfrut.wiki.cleaners;


/**
 * TODO: JavaDoc 
 */
public class SimpleLinkCleaner extends BasicCleanerImpl {
    
    private final static String SIMPLE_LINK_REGEXP = "\\[{2}[^\\]:]+\\]{2}";
    
    public SimpleLinkCleaner() {
        super(SIMPLE_LINK_REGEXP);
    }

    public String processTokens(String token) {
        token = token.substring(2);
        token = token.substring(0,token.length()-2);
        return token;
    }
}
