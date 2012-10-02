package org.grejpfrut.wiki.extractor.cleaner;

import junit.framework.TestCase;

import org.grejpfrut.wiki.cleaners.LangVersCleaner;
import org.grejpfrut.wiki.cleaners.MarkupCleaner;

public class LangVersCleanerTest extends TestCase {
    
    
    public void test_cleaner(){
        
        String langvers = "[[cs:Astronomie]] ";
        MarkupCleaner mc = new LangVersCleaner();
        String res = mc.processMatchingMarkup(langvers);
        assertEquals(res," ");
        
    }

}
