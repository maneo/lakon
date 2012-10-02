package org.grejpfrut.ac.utils;

import java.util.ArrayList;
import java.util.List;

import org.grejpfrut.ac.data.Item;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * This class gets items from RSS XML. We are interested only in a few aspects
 * of news feed sand this simple parser handles them.
 * 
 * @author ad
 * 
 */

public class RssParser extends ArticleContentHandler {

	

	private final List<Item> items = new ArrayList<Item>();



	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		if ("item".equals(qName)) {
			state = STATE.S_ITEM;
			item = new Item();
		} else if ("link".equals(qName) && (state != STATE.S_OTHER)) {
			state = STATE.S_LINK;
		} else if ("title".equals(qName) && (state != STATE.S_OTHER)) {
			state = STATE.S_TITLE;
		} else if ("category".equals(qName) && (state != STATE.S_OTHER)) {
			state = STATE.S_CATEGORY;
		} else if ("description".equals(qName) && (state != STATE.S_OTHER)) {
			state = STATE.S_SUM;
		} else if ("pubDate".equals(qName) && (state != STATE.S_OTHER)) {
			state = STATE.S_DATE;
		} else if ("enclosure".equals(qName) && (state != STATE.S_OTHER)) {
			state = STATE.S_ITEM;
		} else if ("guid".equals(qName) && (state != STATE.S_OTHER)) {
			state = STATE.S_ITEM;
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if ("item".equals(qName)) {
			state = STATE.S_OTHER;
			item.cleanData();
			items.add(item);
		}
	}

	public List<Item> getItems() {
		return items;
	}



}
