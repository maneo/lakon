package org.grejpfrut.wiki.extractor.cleaner;

import junit.framework.TestCase;

import org.grejpfrut.wiki.cleaners.HTMLCleaner;
import org.grejpfrut.wiki.cleaners.MarkupCleaner;

public class HTMLCleanerTest extends TestCase {
    
    public void testOne(){
        
        String langvers = "</table border=1>";
        MarkupCleaner mc = new HTMLCleaner();
        String res = mc.processMatchingMarkup(langvers);
        assertEquals(res," ");
        
    }
    public void testTwo(){
        String test = "<div xmlns=\"http://wwww.3.org/1999/xhtml\">I'd like to do ";
        MarkupCleaner mc = new HTMLCleaner();
        String res = mc.processMatchingMarkup(test);
        assertEquals(res," I'd like to do ");
        
    }

}
