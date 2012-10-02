package org.grejpfrut.wiki.cleaners;


/**
 * TODO: Javadoc?
 */
public class CurlyBracketsCleaner extends BasicCleanerImpl {

    private final static String CURLY_BRACKET_REGEXP = "\\{{2}[^\\}]+\\}{2}";

    public CurlyBracketsCleaner() {
        super(CURLY_BRACKET_REGEXP);
    }

    public String processTokens(String token) {
        token = token.substring(2);
        token = token.substring(0, token.length() - 2);
        return token;
    }
}
