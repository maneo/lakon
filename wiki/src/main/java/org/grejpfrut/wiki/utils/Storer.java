package org.grejpfrut.wiki.utils;

import org.grejpfrut.wiki.data.WikiSite;

/**
 * Basic interface for storing wikisites. 
 * @author ad
 *
 */
public interface Storer {
    
    public void save(WikiSite ws);

}
