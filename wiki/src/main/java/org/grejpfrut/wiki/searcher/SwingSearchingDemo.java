package org.grejpfrut.wiki.searcher;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.grejpfrut.wiki.searcher.gui.SearchingGui;

/**
 * This class launches GUI and gets configuration.
 * @author Adam Dudczak
 *
 */
public class SwingSearchingDemo {

	private static Logger logger = Logger.getLogger(SwingSearchingDemo.class);

	private static final String DEFAULT_CONF = "/conf.properties";

	public static void main(String[] args) {

		Properties conf = new Properties();
		if (args.length == 1) {
			String config  = args[0];
			try {
				conf.load(new FileInputStream(config));
			} catch (IOException e) {
				logger.fatal("Cannot find conf properties ", e);
			}
		}
		else {
			logger.info("custom configuration should be defined "
					+ "as a first argument. getting defaults");
		}
		SearchingGui gui = new SearchingGui(conf);
		gui.display();

	}

}
