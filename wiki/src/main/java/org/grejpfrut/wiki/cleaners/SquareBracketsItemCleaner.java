package org.grejpfrut.wiki.cleaners;

import org.apache.log4j.Logger;

public class SquareBracketsItemCleaner implements MarkupCleaner {
    
    private final static Logger logger = Logger.getLogger(SquareBracketsItemCleaner.class);

    private final static String SQUARE_SIMPLE_ITEM = "\\[{2}simple:[^\\]\\|]+\\]{2}";
    private final static String SQUARE_GFX_ITEM = "\\[{2}Grafika:[^\\]\\|]+\\]{2}";
    
    private final BasicCleanerImpl SQUARE_SIMPLE_ITEM_C = new BasicCleanerImpl(SQUARE_SIMPLE_ITEM) {
        public String processTokens(String token) {
            return " ";
        }
    };

    private final BasicCleanerImpl SQUARE_GFX_ITEM_C = new BasicCleanerImpl(SQUARE_GFX_ITEM) {
        public String processTokens(String token) {
            return " ";
        }
    };

    public String processMatchingMarkup(String text) {
        final String intermediate = SQUARE_GFX_ITEM_C.processMatchingMarkup(text);
        return SQUARE_SIMPLE_ITEM_C.processMatchingMarkup(intermediate);
    }
}
