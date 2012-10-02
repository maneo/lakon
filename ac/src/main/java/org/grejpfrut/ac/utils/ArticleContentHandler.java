package org.grejpfrut.ac.utils;

import org.apache.log4j.Logger;
import org.grejpfrut.ac.data.Item;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public abstract class ArticleContentHandler implements ContentHandler {

	/**
	 * Current processing state (element being processed)
	 */
	protected enum STATE {
		S_LINK, S_TITLE, S_CATEGORY, S_SUM, S_ITEM, S_DATE, S_OTHER, S_FULLTEXT
	};

	protected STATE state = STATE.S_OTHER;

	protected Item item;

	private final static Logger logger = Logger
			.getLogger(ArticleContentHandler.class);

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (state == STATE.S_CATEGORY)
			item.appendCategory(new String(ch, start, length));
		else if (state == STATE.S_LINK)
			item.appendLink(new String(ch, start, length));
		else if (state == STATE.S_TITLE)
			item.appendTitle(new String(ch, start, length));
		else if (state == STATE.S_SUM)
			item.appendSummary(new String(ch, start, length));
		else if (state == STATE.S_DATE)
			item.appendDate(new String(ch, start, length));
	}

	public void startDocument() throws SAXException {
		
	}

	public void endDocument() throws SAXException {
		
	}

	public void setDocumentLocator(Locator locator) {
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
	}

	public void endPrefixMapping(String prefix) throws SAXException {
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
	}

	public void skippedEntity(String name) throws SAXException {
	}

}
