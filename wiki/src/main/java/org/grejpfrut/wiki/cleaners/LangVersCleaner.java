package org.grejpfrut.wiki.cleaners;

/**
 * TODO: JavaDoc 
 */
public class LangVersCleaner extends BasicCleanerImpl {
    private static final String LANG_VERS_REGEXP = "\\[{2}\\w{2,3}:[^\\]]+\\]{2}";

    public LangVersCleaner() {
        super(LANG_VERS_REGEXP);
    }

    public String processTokens(String token) {
        return "";
    }
}
