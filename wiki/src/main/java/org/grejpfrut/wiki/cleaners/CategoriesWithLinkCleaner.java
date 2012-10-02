package org.grejpfrut.wiki.cleaners;

/**
 * TODO: Javadoc?
 */
public class CategoriesWithLinkCleaner extends BasicCleanerImpl {

    private final static String CATEGORY_WL_REGEXP = "\\[{2}Kategoria:[^\\]]+\\|\\*\\]{2}";
    
    public CategoriesWithLinkCleaner() {
        super(CATEGORY_WL_REGEXP);
    }

    public String processTokens(String token) {
        token = token.substring(12);
        token = token.substring(0, token.length()-4);
        return token;
     }
}
