package org.grejpfrut.wiki.cleaners;

public class ExternalLinkCleaner extends BasicCleanerImpl {

    private final static String EXTERNAL_LINK_REGEXP = "\\[http://\\S+\\s[^\\]]+\\]";
    
    public ExternalLinkCleaner() {
        super(EXTERNAL_LINK_REGEXP);
    }

    public String processTokens(String token) {
        token = token.substring(1);
        token = token.substring(0,token.length()-1);
        int space = token.indexOf(" ");
        token = token.substring(space+1, token.length());
        return token;
    }
}
