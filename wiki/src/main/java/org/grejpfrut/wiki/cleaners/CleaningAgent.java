package org.grejpfrut.wiki.cleaners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * Class manages markup cleaners, gets class list from
 * configuration files and saves separate xml files to
 * disk.
 * 
 * @author Adam Dudczak
 */
public class CleaningAgent {

    private final static String CLEANERS_PACKAGE = "org.grejpfrut.wiki.cleaners";

    private final static Logger logger = Logger.getLogger(CleaningAgent.class);

    private final static String configuration = "/conf.properties";

    private Properties conf = new Properties();

    private List<MarkupCleaner> cleaners = new ArrayList<MarkupCleaner>();

    public CleaningAgent() {
        try {
            conf.load(getClass().getResourceAsStream(configuration));
            String usedCleaners = conf.getProperty("used.cleaners");
            StringTokenizer st = new StringTokenizer(usedCleaners, ",");
            while (st.hasMoreTokens()) {
                String className = CLEANERS_PACKAGE + "." + st.nextToken();
                cleaners.add(createCleaner(className));
            }
        } catch (IOException e) {
            logger.fatal("Exception while initializing CleaningAgent: ",e);
            throw new RuntimeException(e);
        }
    }

    private MarkupCleaner createCleaner(String className) {
        final MarkupCleaner result;
        try {
            Class cleaner = Thread.currentThread().getContextClassLoader().loadClass(className);
            result = (MarkupCleaner) cleaner.newInstance();
        } catch (ClassNotFoundException e) {
            logger.fatal("Class not found " + className);
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            logger.fatal("Exception during creating wiki markup clenears");
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            logger.fatal("Exception during creating wiki markup clenears");
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Cleans given String from markup.
     * @param markup - String with markup.
     * @return clean String.
     */
    public String cleanMarkup(String markup){
        for (final MarkupCleaner cl : cleaners){
            markup = cl.processMatchingMarkup(markup);
        }
        return markup;
    }
}
