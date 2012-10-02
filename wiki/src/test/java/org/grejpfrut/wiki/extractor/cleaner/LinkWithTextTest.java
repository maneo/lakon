package org.grejpfrut.wiki.extractor.cleaner;

import junit.framework.TestCase;

import org.grejpfrut.wiki.cleaners.LinkWithTextCleaner;
import org.grejpfrut.wiki.cleaners.MarkupCleaner;

public class LinkWithTextTest extends TestCase {
    
   public void testWithoutWhiteSpaces(){
        
        String markup = "[[kosmosy|kosmos]]";
        MarkupCleaner mc = new LinkWithTextCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"kosmos");
    }
    public void testWithWhiteSpaces(){
        
        String markup = "[[kos m osy|k osmos]]";
        MarkupCleaner mc = new LinkWithTextCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res,"k osmos");
        
    }
    
    public void testFewOccurs(){

        String markup = "[[blee|ee]] asd [[a|b]] {{p}}";
        MarkupCleaner mc = new LinkWithTextCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"ee asd b {{p}}");
        
    }

    public void testPolishText(){

        String markup = "[[Komponent (informatyka)|komponent�w]]";
        MarkupCleaner mc = new LinkWithTextCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"komponent�w");
        
    }
    
    public void testDoubleMatch(){
        
        String markup = "[[dien]]y skumulowane) to [[w�glowod�r|w�glowodory]]";
        MarkupCleaner mc = new LinkWithTextCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"[[dien]]y skumulowane) to w�glowodory");

    }
    
}
