package org.grejpfrut.wiki.extractor.cleaner;

import junit.framework.TestCase;

import org.grejpfrut.wiki.cleaners.ExternalLinkCleaner;
import org.grejpfrut.wiki.cleaners.MarkupCleaner;

public class ExternalLinkCleanerTest extends TestCase {
    
   public void testWithoutWhiteSpaces(){
        
        String markup = "[http://www.wp.pl niedobra strona]";
        MarkupCleaner mc = new ExternalLinkCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"niedobra strona");
    }
    public void testWithWhiteSpaces(){
        
        String markup = "[http://www.wp.pl nie dobra strona]";
        MarkupCleaner mc = new ExternalLinkCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res,"nie dobra strona");
        
    }
    
    public void testFewOccurs(){

        String markup = "[http://www.wp.pl nie dobra strona] ble [http://grejpfrut.org dobra strona]";
        MarkupCleaner mc = new ExternalLinkCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"nie dobra strona ble dobra strona");
        
    }
    
    public void testMarkupFromWiki(){
        
        String markup = "[http://www.wp.pl polska strona opisuj¹ca system AmigaOS 4]";
        MarkupCleaner mc = new ExternalLinkCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"polska strona opisuj¹ca system AmigaOS 4");

            
    }

}
