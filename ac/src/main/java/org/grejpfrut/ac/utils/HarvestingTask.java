package org.grejpfrut.ac.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.grejpfrut.ac.data.Item;
import org.grejpfrut.ac.store.ArticleIdCache;
import org.grejpfrut.ac.store.FileArticleStorer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class HarvestingTask extends Thread {

	private final static Logger logger = Logger.getLogger(HarvestingTask.class);

	private final String LOADER_CLASS_KEY = "id.loader.class";

	private final String WORKING_DIR_KEY = "working.dir";

	private final static int RANDOM_SLEEP_RANGE = 1000 * 60 * 100;

	private final ArticleIdCache cache;

	private final int minDelay;

	private final String target;

	private final HttpTool httpTool;

	private final Properties conf;

	public HarvestingTask(Properties conf) {

		this.conf = conf;
		cache = new ArticleIdCache(Integer.parseInt(conf
				.getProperty("cache.size")), conf.getProperty(LOADER_CLASS_KEY));
		cache.loadFromDisk(conf.getProperty("working.dir"));
		httpTool = new HttpTool(conf);

		minDelay = Integer.parseInt(conf.getProperty("min.delay"));
		target = conf.getProperty("target.url");

	}

	public void run() {

		while (true) {
			logger.info("Starting to harvest....");
			try {

				long start = System.currentTimeMillis();

				List<Item> items = getNewItems();
				storeItems(items);

				long time = Math
						.round((System.currentTimeMillis() - start) / 1000);
				logger.info("Harvesting time: " + time + "s");

				int sleepTime = minDelay
						+ (int) (Math.random() * RANDOM_SLEEP_RANGE);
				logger.info("Next harvesting in: " + sleepTime / 1000 + "s");
				Thread.sleep(sleepTime);

			} catch (InterruptedException e) {
				logger.info("Harvesting thread says: nii!");
				return;
			}

		}

	}

	private List<Item> getNewItems() {

		final SAXParser parser;
		List<Item> items = new ArrayList<Item>();
		try {

			parser = SAXParserFactory.newInstance().newSAXParser();
			final XMLReader reader = parser.getXMLReader();
			final RssParser p = new RssParser();
			reader.setContentHandler(p);
			reader.parse(new InputSource(new ByteArrayInputStream(httpTool.get(
					target, "ISO-8859-2"))));
			items = cache.getNewItems(p.getItems());
			items = getArticles(items);

		} catch (ParserConfigurationException e) {
			throw new RuntimeException("Fatal during configuration of parser",
					e);
		} catch (SAXException e) {
			logger.error("Sax parser exception..", e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			logger.error("Error while reading input ", e);
			throw new RuntimeException(e);
		}
		return items;
	}

	private List<Item> getArticles(final List<Item> items)
			throws UnsupportedEncodingException {

		final List<Item> aitems = new ArrayList<Item>();
		for (Item item : items) {
			String result = new String(httpTool.get(item.getLink(),
					"ISO-8859-2"));
			final HTMLParser parser = new HTMLParser();
			item.appendArticle(parser.getArticle(result));
			logger.debug(item.getArticle());
			aitems.add(item);
		}
		return aitems;
	}

	private void storeItems(List<Item> items) {
		final FileArticleStorer storer = new FileArticleStorer(conf
				.getProperty(WORKING_DIR_KEY));
		for (Item item : items) {
			storer.store(item);
		}
	}
}
