package org.grejpfrut.ac.cleaners;

/**
 * Removes all HTML tags except <br>
 * which are assumed to be end of paragraph.
 * 
 * @author ad
 */
public class ArticleCleaner {

    private final static String HTML_REGEXP = "<[\\w\\s=/\"'+-:;~#\\&\\.]+>";

    private final static String TAB_REGEXP = "\t+";

//    private final static String NEW_LINE_REGEXP = "(\\s*(<br>)+(\\s*(<br>)*)?)+?";
    private final static String NEW_LINE_REGEXP = "(\\s*(<br>)+\\s*)+";


    private final static BasicCleanerImpl TAB_CLEANER = new BasicCleanerImpl(
            TAB_REGEXP) {
        @Override
        public String processTokens(String token) {
            return " ";
        }
    };

    private final static BasicCleanerImpl REPEATING_BR = new BasicCleanerImpl(
            NEW_LINE_REGEXP) {

        @Override
        public String processTokens(String token) {
            return "\n";
        }

    };

    private final static BasicCleanerImpl HTML_CLEANER = new BasicCleanerImpl(
            HTML_REGEXP) {

        @Override
        public String processTokens(String token) {
            if (token.indexOf("<br>") == -1)
                return "";
            else
                return "<br>";
        }

    };


    public static String clean(String text) {

        text = HTML_CLEANER.processMatchingMarkup(text);
        text = TAB_CLEANER.processMatchingMarkup(text);
        return REPEATING_BR.processMatchingMarkup(text);

    }

}
