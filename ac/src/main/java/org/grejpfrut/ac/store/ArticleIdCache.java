package org.grejpfrut.ac.store;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.grejpfrut.ac.data.Item;

/**
 * This is used to compare articles in processed rss with those already saved to
 * disk. Rememeber only a certain amount of past articles. Class defines method
 * to get info about saved articles from disk. Loader for this information can
 * be customized in conf.properties.
 * 
 * @author ad
 * 
 */
public class ArticleIdCache {
	
	private final static Logger logger = Logger.getLogger(ArticleIdCache.class);

    private List<String> ids;

    private int maxSize;

    private IdLoader loader;

    public ArticleIdCache(int size, String loaderClass) {

    	this.ids = new ArrayList<String>();
        this.maxSize = size;
        this.loader = getLoader(loaderClass);

    }

    private IdLoader getLoader(String clazz) {
        final IdLoader result;
        try {
            Class loader = Thread.currentThread().getContextClassLoader().loadClass(clazz);
            result = (IdLoader) loader.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Check wheter id is on the list.
     * 
     * @param id
     * @return true if object is already on the list. otherwise false
     */
    public boolean isInCache(String id) {
        if (ids.contains(id))
            return true;
        return false;
    }

    /**
     * Acts like isInCache, true if object is already on the list
     * 
     * @param id
     * @return
     */
    public void addId(String id) {
        ids.add(0, id);
        if (ids.size() > this.maxSize)
            ids = ids.subList(0, this.maxSize);
    }

    /**
     * Gets number of ids in cache.
     * @return 
     */
    public int cacheSize() {
        return ids.size();
    }


    /**
     * Gets already saved identifiers from filesystem.
     * @param path - working dir path.
     */
    public void loadFromDisk(String path) {
        
        ids = loader.load(path);
        if(ids.size() > maxSize) ids = ids.subList(0,maxSize);

    }
    
    /**
     * Method deal with already saved articles, when article is on disk
     * coresponding item won't be returned.
     * @param items - harvested items
     * @return list of new items.
     */
    public List<Item> getNewItems(List<Item> items){
        
        final List<Item> result = new ArrayList<Item>(items);
        int newItems = 0;
        for(Item item : items){
            final String id = item.getIdentifier();
            if(this.isInCache(id)) result.remove(item);
            else {
            	this.addId(id);
            	newItems++;
            }
        }
        logger.info("Number of new items:"+newItems);
        return result;
    }

}
