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

        String markup = "[[Komponent (informatyka)|komponentów]]";
        MarkupCleaner mc = new LinkWithTextCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"komponentów");
        
    }
    
    public void testDoubleMatch(){
        
        String markup = "[[dien]]y skumulowane) to [[wêglowodór|wêglowodory]]";
        MarkupCleaner mc = new LinkWithTextCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"[[dien]]y skumulowane) to wêglowodory");

    }
    
}
