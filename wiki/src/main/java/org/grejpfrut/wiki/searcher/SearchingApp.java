package org.grejpfrut.wiki.searcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.grejpfrut.wiki.data.SimpleResult;
import org.grejpfrut.wiki.process.SearchingProcess;
import org.grejpfrut.wiki.process.SearchingSettings;

/**
 * Searches through index to find similar documents, based
 * on keywords. 
 * @author ad
 *
 */
public class SearchingApp {

    private static Logger logger = Logger.getLogger(SearchingApp.class);

    private final static String usage = "usage: [java jar exec part] path_to_text_file";
    
    private final static String configuration = "/conf.properties";

    public static void main(String[] args) {

        String article = getArticle(args[0]);
        
        Properties conf = new Properties();
        try {
			conf.load(SearchingApp.class.getResourceAsStream(configuration));
		} catch (IOException e) {
			logger.fatal("Cannot find conf properties ",e);
			return;
		}
        SearchingSettings settings = new SearchingSettings(conf);
        SearchingProcess sproc = new SearchingProcess(settings);
        SimpleResult sres = null;
		try {
			sres = sproc.process(article);
		} catch (IOException e) {
			logger.fatal("Exception during indexong process",e);
		}
        logger.info(sres.toString());

    }

    private static String getArticle(String string) {
        File file = new File(string);
        StringBuffer article = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),
            "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null)
                article.append(line);
            br.close();
        } catch (FileNotFoundException e) {
            logger.fatal("Error while opening article file " + e.getMessage());
        } catch (IOException e) {
            logger.fatal("Error while reading article file " + e.getMessage());
        }
        return article.toString();
    }


}
