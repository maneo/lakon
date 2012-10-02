package org.grejpfrut.wiki.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


/**
 * A simple class for counting and periodically dumping
 * the progress of a sequential process.
 *  
 * @author Dawid Weiss
 */
public class Counter {
    private final static long INTERVAL = 5000;

    private final long memBefore;
    
    private final long start;
    private long nextInfo;

    private final Logger logger;
    private String title;
    private String itemsName;
    
    private long steps = 0;

    public Counter(final Logger logger, final String title, final String itemsName) {
        System.gc();
        memBefore = Runtime.getRuntime().freeMemory();
        start = System.currentTimeMillis();
        this.logger = logger;
        this.title = title;
        this.itemsName = itemsName;
        this.nextInfo = System.currentTimeMillis() + INTERVAL;
    }

    public void step() {
        steps++;
        final long current = System.currentTimeMillis(); 
        if (current > nextInfo) {
            dumpInfo();
            nextInfo = current + INTERVAL;
        }
    }

    public void dumpInfo() {
        final double seconds = ((System.currentTimeMillis() - start) / 1000.0d);
        final double rate = steps / seconds;

        logger.log(Level.INFO, title + ": "
                + steps + " "
                + itemsName + " (" + rate + " " + itemsName + "/sec.,"
                + " " + Math.floor(seconds) + " sec. elapsed"
                + ")");
    }

    public void totals() {
        final double totalTime = (System.currentTimeMillis() - start) / 1000.0d;
        dumpInfo();
        memInfo();
        logger.log(Level.INFO, title + " (Total time): "
                + totalTime);
    }

    public void memInfo() {
        System.gc();

        final long memAfter = Runtime.getRuntime().freeMemory();
        logger.log(Level.INFO, title + " (Memory): "
                + (memAfter - memBefore) / (1024 * 1024) + " MB memory used.");
    }
}