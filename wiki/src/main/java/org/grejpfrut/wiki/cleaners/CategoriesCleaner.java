package org.grejpfrut.wiki.cleaners;

/**
 * TODO: Javadoc?
 */
public class CategoriesCleaner extends BasicCleanerImpl {

    private final static String CATEGORY_REGEXP = "\\[{2}[Kk]ategoria:[^\\]\\|]+\\]{2}";

    public CategoriesCleaner() {
        super(CATEGORY_REGEXP);
    }

    public String processTokens(String token) {
        token = token.substring(12);
        token = token.substring(0, token.length()-2);
        return token;
    }
}
