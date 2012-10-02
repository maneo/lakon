package org.grejpfrut.ac;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.grejpfrut.ac.utils.HarvestingTask;

/**
 * Hello world!
 * 
 */
public class App {
	private final static Logger logger = Logger.getLogger(App.class);

	private static Properties conf;

	public static void main(String[] args) {
		logger.info("Starting to do my job");
		try {
			conf = new Properties();
			if (args.length >= 1) {
				conf.load(new FileInputStream(new File(args[0])));
			} else
				conf.load(App.class.getResourceAsStream("/conf.properties"));

		} catch (IOException e) {
			logger.fatal("Cannot find properties file");
			return;
		}

		HarvestingTask ht = new HarvestingTask(conf);
		// start to harvest
		ht.start();

		while (true) {

			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				if (br.readLine().equals("exit")) {
					ht.interrupt();
					return;
				}
			} catch (IOException e) {
				logger.error("Something is wrong");
				ht.interrupt();
				return;
			}
		}
	}
}
