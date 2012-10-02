package org.grejpfrut.wiki.extractor.cleaner;

import junit.framework.TestCase;

import org.grejpfrut.wiki.cleaners.CategoriesCleaner;
import org.grejpfrut.wiki.cleaners.CategoriesWithLinkCleaner;
import org.grejpfrut.wiki.cleaners.MarkupCleaner;

public class CategoriesCleanerTest extends TestCase {
    
   public void testOrdinary(){
        
        String markup = "[[Kategoria:kosmos]]y";
        MarkupCleaner mc = new CategoriesCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"kosmosy");
    }
    
    public void testFewOccurs(){

        String markup = "[[Kategoria:kosmos]] [[Kategoria:kosmos]]";
        MarkupCleaner mc = new CategoriesCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"kosmos kosmos");
        
    }
    
    public void testPolishText(){
        
        String markup = "[[Kategoria:komórka j¹drowa]]";
        MarkupCleaner mc = new CategoriesCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"komórka j¹drowa");
        
    }
    
    public void testCategoriesWithLink(){

        String markup = "[[Kategoria:J¹drowa|*]]";
        MarkupCleaner mc = new CategoriesCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"[[Kategoria:J¹drowa|*]]");
        mc = new CategoriesWithLinkCleaner();
        res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"J¹drowa");
        
    }

}
