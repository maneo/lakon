package org.grejpfrut.wiki.cleaners;

/**
 * TODO: JavaDoc 
 */
public class XMLCommentsCleaner extends BasicCleanerImpl {

    private final static String XML_COMM_REGEXP = "<!--.+?-->";

    public XMLCommentsCleaner() {
        super(XML_COMM_REGEXP);
    }

    @Override
    public String processTokens(String token) {
        return " ";
    }
}
