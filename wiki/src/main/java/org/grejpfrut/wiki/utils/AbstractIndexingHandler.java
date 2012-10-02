package org.grejpfrut.wiki.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexWriter;
import org.grejpfrut.wiki.data.WikiSite;
import org.grejpfrut.wiki.indexer.AbstractsDumpIndexingHandler;
import org.grejpfrut.wiki.indexer.DataDumpIndexingHandler;
import org.grejpfrut.wiki.process.IndexingProcess;
import org.grejpfrut.wiki.process.IndexingSettings;
import org.grejpfrut.wiki.process.WikiIndexingProcess;
import org.xml.sax.SAXException;

/**
 * Extension of ContentHandlerWrapper root of data dump handlers hierarchy 
 * ( {@link AbstractsDumpIndexingHandler},{@link DataDumpIndexingHandler} ). 
 */
public abstract class AbstractIndexingHandler  extends ContentHandlerWrapper {

    /**
     * Current processing state (element being processed)
     */
    protected enum STATE {S_TITLE, S_ARTICLE, S_OTHER};

    protected STATE currItem = STATE.S_OTHER;

    private final static Logger logger = Logger.getLogger(DataDumpIndexingHandler.class);

    protected WikiSite currWikiSite;
    
    protected long timeForOnePage = 0;
    
    protected long noOfPages = 0;
    
    protected long starttime = 0;
    
    protected final IndexingProcess process;

    protected final List<Pattern> ignoredPages; 
    
    protected final Counter counter;
    
    protected boolean ignorePage = false;
    
    protected AbstractIndexingHandler(Properties conf, IndexWriter iw){
        
        StringTokenizer regexp = new StringTokenizer(conf.getProperty("ignored.pages"),",");
        ignoredPages = new ArrayList<Pattern>();
        while(regexp.hasMoreTokens()){
        	final String igpat = regexp.nextToken().trim();
            ignoredPages.add(Pattern.compile(igpat)); 
        }
        this.counter = new Counter(logger, "Indexing", "documents");
        this.process = new WikiIndexingProcess(new IndexingSettings(conf),iw);
        
    }

    
    public void characters(char[] ch, int start, int length)
        throws SAXException {
        if (currItem == STATE.S_TITLE) {
            currWikiSite.appendTitle(ch, start, length);
        } else if (currItem == STATE.S_ARTICLE) {
            currWikiSite.appendArticle(ch, start, length);
        } else {
            // Ignore characters outside of the title/ article range.
        }
    }
    
    @Override
    public void endDocument() throws SAXException {
        logger.info("Number of pages indexed: " + noOfPages);
        logger.info("Average indexing time of one page: " + (timeForOnePage/noOfPages));
    }
    
    protected void updateCounter() {
        this.counter.step();
    }
}
