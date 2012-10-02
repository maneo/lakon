package org.grejpfrut.wiki.cleaners;

/**
 * TODO: JavaDoc 
 */
public class LinkWithTextCleaner extends BasicCleanerImpl {

    private final static String LINK_WITH_TEXT_REGEXP = "\\[{2}[^\\|\\]]+\\|[^\\]]+\\]{2}";
    
    public LinkWithTextCleaner() {
        super(LINK_WITH_TEXT_REGEXP);
    }
    
    public String processTokens(String token) {
        token = token.substring(2);
        token = token.substring(0,token.length()-2);
        int separator = token.indexOf("|");
        token = token.substring(separator+1, token.length());
        return token;
    }
}
