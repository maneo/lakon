package org.grejpfrut.wiki.cleaners;

/**
 * TODO: JavaDoc 
 */
public class HTMLCleaner extends BasicCleanerImpl {
    private final static String HTML_REGEXP = "<[\\w\\s=/\"'+-:;~#\\&\\.]+>";

    public HTMLCleaner() {
        super(HTML_REGEXP);
    }

    public String processTokens(String token) {
        return " ";
    }
}
