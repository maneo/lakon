package org.grejpfrut.ac.data;

import junit.framework.TestCase;

public class ItemTest extends TestCase {
    
    public void testIdentifier(){
        Item item = new Item();
        item.appendLink("http://wiadomosci.gazeta.pl/wiadomosci/1,53600,3481099.html?skad=rss");
        String id = item.getIdentifier();
        assertEquals(id,"1,53600,3481099");
    }

}
