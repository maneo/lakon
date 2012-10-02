package org.grejpfrut.wiki.extractor.cleaner;

import junit.framework.TestCase;

import org.grejpfrut.wiki.cleaners.CurlyBracketsCleaner;
import org.grejpfrut.wiki.cleaners.MarkupCleaner;

public class CurlyBracketsCleanerTest extends TestCase {
    
 public void testWithoutWhiteSpaces(){
        
        String markup = "{{bleeee}}";
        MarkupCleaner mc = new CurlyBracketsCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"bleeee");
    }
    public void testWithWhiteSpaces(){
        
        String markup = "{{ble eee}}";
        MarkupCleaner mc = new CurlyBracketsCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res,"ble eee");
        
    }
    
    public void testFewOccurs(){

        String markup = "{{bleeee}} asd {{a}}";
        MarkupCleaner mc = new CurlyBracketsCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"bleeee asd a");
        
    }

    public void testPolishText(){

        String markup = "{{kniaü}} asd {{z≥y}}";
        MarkupCleaner mc = new CurlyBracketsCleaner();
        String res = mc.processMatchingMarkup(markup);
        assertEquals(res ,"kniaü asd z≥y");

        
    }
    
}
