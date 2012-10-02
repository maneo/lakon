package org.grejpfrut.ac.utils;

import org.grejpfrut.ac.data.Item;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ArticleParser extends ArticleContentHandler {
	
	public  ArticleParser(){
		super.item = new Item();
	}

	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {

		if ("link".equals(qName)) {
			state = STATE.S_LINK;
		} else if ("title".equals(qName)) {
			state = STATE.S_TITLE;
		} else if ("category".equals(qName)) {
			state = STATE.S_CATEGORY;
		} else if ("summary".equals(qName)) {
			state = STATE.S_SUM;
		} else if ("pubDate".equals(qName)) {
			state = STATE.S_DATE;
		} else if ("fulltext".equals(qName)) {
			state = STATE.S_FULLTEXT;
		} 
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
	}
	
	public Item getItem() {
		return super.item;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (state == STATE.S_FULLTEXT)
			item.appendArticle(new String(ch, start, length));
	}

}
