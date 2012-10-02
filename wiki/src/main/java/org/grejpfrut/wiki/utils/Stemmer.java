package org.grejpfrut.wiki.utils;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import org.apache.log4j.Logger;
import org.carrot2.demo.ProcessSettingsBase;

/**
 * Basic facility for StemmerWrappers.
 * @author Adam Dudczak
 *
 */
public abstract class Stemmer {
	
	public static final String INDEXER_STEMMER_CLASS = "indexer.stemmer.class";
	public static final String INDEXER_IGNORED_WORDS = "indexer.ignored.words";
    
    protected Set<String> ignoredWords;
    
    private static Logger logger = Logger.getLogger(Stemmer.class);

    /**
     * Creates String with stems separated by whitespace
     * @param article 
     * @return String with stems.
     */    
    public abstract String getDocAsStems(String article);
    
    /**
     * Returns stem from single String;
     * @param article
     * @return
     */
    public abstract String doStem(String article);
    
    public void setIgnoredWords(Set<String> iW){
    	this.ignoredWords = iW;
    }
    
    /**
     * Token is allowed when it isn't on ignoredWords list and it's not a
     * number.
     * 
     * @param token
     * @return
     */
    protected boolean isTokenAllowed(String token) {
        if (ignoredWords.contains(token))
            return false;
        if (token.matches("\\d+"))
            return false;
        return true;
    }

   
    public static Stemmer getStemmer(ProcessSettingsBase settings){
    	
    	final String clazz = (String)settings.getRequestParams().get(INDEXER_STEMMER_CLASS);
    	final Set<String> iW = (Set<String>)settings.getRequestParams().get(INDEXER_IGNORED_WORDS);
    	
    	Stemmer st = Stemmer.getStemmer(clazz);
		if (st == null) {
			st = new StempelStemmer(iW);
		} else {
			st.setIgnoredWords(iW);
		}
		return st;
    	
    }
    
    public static Stemmer getStemmer(String clazz){
        try {
            final Class stem = Thread.currentThread().getContextClassLoader().loadClass(clazz);
            final Stemmer stemmer = (Stemmer) stem.newInstance();
            return stemmer;
        } catch (ClassNotFoundException e) {
            logger.fatal("Stemmer class not found: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            logger.fatal("Instantiation exception during handler init: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            logger.fatal("Illegal arg :" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean containsInvalidChars(String token) {
        try {
            byte bytes[] = token.getBytes("iso-8859-2");
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] == 63)
                    return true;
            }
    
        } catch (UnsupportedEncodingException e) {
            logger
                    .fatal("unsuported encoding during checking for invalid characters "
                            + e.getMessage());
        }
        return false;
    }

}
