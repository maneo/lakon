package org.grejpfrut.wiki.indexer;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.index.IndexWriter;
import org.grejpfrut.wiki.data.WikiSite;
import org.grejpfrut.wiki.utils.AbstractIndexingHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Class used to index abstracts from wikipedia, from xml data dump
 * to lucene index. 
 */
public class AbstractsDumpIndexingHandler extends AbstractIndexingHandler {

	public AbstractsDumpIndexingHandler(Properties conf, IndexWriter iw) {
		super(conf, iw);
	}

	
	public void startElement(String uri, String localName, final String qName,
			Attributes atts) throws SAXException {
		if ("doc".equals(qName)) {
	
			currWikiSite = new WikiSite();
			noOfPages++;
			starttime = System.currentTimeMillis();
		
		} else if ("title".equals(qName) && !ignorePage)
			currItem = STATE.S_TITLE;
		else if ("abstract".equals(qName) && !ignorePage)
			currItem = STATE.S_ARTICLE;
		else
			currItem = STATE.S_OTHER;
	}

	public void endElement(String uri, String localName, final String qName)
			throws SAXException {
		if ("doc".equals(qName) && !ignorePage) {
			this.process.process(this.currWikiSite);
			final long endtime = System.currentTimeMillis();
			timeForOnePage += (endtime - starttime);
			updateCounter();
		} else if ("title".equals(qName)) {

			String title = currWikiSite.getTitle();
			for (Pattern pt : ignoredPages) {
				final Matcher m = pt.matcher(title);
				if (m.matches()) {
					ignorePage = true;
				}
			}

		}

	}
}
