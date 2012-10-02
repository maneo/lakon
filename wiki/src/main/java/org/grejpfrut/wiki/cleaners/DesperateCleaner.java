package org.grejpfrut.wiki.cleaners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * TODO: Javadoc?
 * TODO: This kind of cleaner would be more efficient if implemented
 * with a complex regular expression (1 pass over the data set). 
 */
public class DesperateCleaner implements MarkupCleaner {

    private static final String REPLACE_BY = " ";

    private static Logger logger = Logger.getLogger(DesperateCleaner.class);

    private final static String configuration = "/conf.properties";

    private Properties conf = new Properties();
    
    private List<String> subs = new ArrayList<String>();

    public String processTokens(String token) {
        return null;
    }

    public DesperateCleaner(){
        init();
    }

    public String processMatchingMarkup(String text) {
        for (String sub: subs){
            text = text.replace(sub, REPLACE_BY);
        }
        text = text.replace("\n"," ");
        return text;
    }

    private void init() {
        try {
            conf.load(getClass().getResourceAsStream(configuration));
            StringTokenizer st = 
                new StringTokenizer(
                        conf.getProperty("desperate.cleaner.chars"), " ");
            while (st.hasMoreTokens()) {
                subs.add(st.nextToken());
            }
        } catch (IOException e) {
            logger.fatal("Error while loading configuration properties.");
            throw new RuntimeException();
        }
    }


}
