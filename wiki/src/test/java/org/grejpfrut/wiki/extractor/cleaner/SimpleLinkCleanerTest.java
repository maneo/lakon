package org.grejpfrut.wiki.extractor.cleaner;

import junit.framework.TestCase;

import org.grejpfrut.wiki.cleaners.MarkupCleaner;
import org.grejpfrut.wiki.cleaners.SimpleLinkCleaner;

public class SimpleLinkCleanerTest extends TestCase {
    
   public void testWithoutWhiteSpaces(){
        
        String markup = "[[kosmos]]y";
        MarkupCleaner mc = new SimpleLinkCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"kosmosy");
    }
    public void testWithWhiteSpaces(){
        
        String markup = "[[ble eeeeeee]]";
        MarkupCleaner mc = new SimpleLinkCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res,"ble eeeeeee");
        
    }
    
    public void testFewOccurs(){

        String markup = "[[bleeee]] asd [[a]] {{p}}";
        MarkupCleaner mc = new SimpleLinkCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"bleeee asd a {{p}}");
        
    }
    
    public void testPolishText(){
        
        String markup = "[[komórka j¹drowa]]";
        MarkupCleaner mc = new SimpleLinkCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"komórka j¹drowa");
        
    }
    
    public void testCategories(){

        String markup = "[[Kategoria:J¹drowa]]";
        MarkupCleaner mc = new SimpleLinkCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"[[Kategoria:J¹drowa]]");

        
    }

}
