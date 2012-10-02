package org.grejpfrut.wiki.extractor.cleaner;

import junit.framework.TestCase;

import org.grejpfrut.wiki.cleaners.MarkupCleaner;
import org.grejpfrut.wiki.cleaners.XMLCommentsCleaner;

public class XMLCommentsCleanerTest extends TestCase {
    
    public void test1(){
        
        String testowa = "<!-- asdasdasd -->ble<!-- adad -->";
        MarkupCleaner mc = new XMLCommentsCleaner();
        String res = mc.processMatchingMarkup(testowa);
        assertEquals(" ble ",res);
        
        
    }

}
