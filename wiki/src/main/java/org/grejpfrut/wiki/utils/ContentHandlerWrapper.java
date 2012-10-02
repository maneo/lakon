package org.grejpfrut.wiki.utils;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * Wrapper to ContentHandler.
 * @author Adam Dudczak
 *
 */

public abstract class ContentHandlerWrapper implements ContentHandler {

    public void setDocumentLocator(Locator locator) {    }

    public void startDocument() throws SAXException {    }

    public void endDocument() throws SAXException {    }

    public void startPrefixMapping(String prefix, String uri) throws SAXException {    }

    public void endPrefixMapping(String prefix) throws SAXException {    }

    public abstract void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException ;

    public abstract void endElement(String uri, String localName, String qName) throws SAXException ;

    public abstract void characters(char[] ch, int start, int length) throws SAXException ;

    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {    }

    public void processingInstruction(String target, String data) throws SAXException {    }

    public void skippedEntity(String name) throws SAXException {    }

}
