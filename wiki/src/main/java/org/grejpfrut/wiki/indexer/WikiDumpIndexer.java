package org.grejpfrut.wiki.indexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.grejpfrut.wiki.utils.AbstractIndexingHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * Class that manages the process of data dump parsing. It can
 * parse abstracts or full wiki site dump.
 */
public class WikiDumpIndexer {

    private static Logger logger = Logger.getLogger(WikiDumpIndexer.class);

    private Properties conf = new Properties();

    private InputStream data;

    private String sourcePath = null;

    private String outputPath = null;

    private boolean indexAbstracts = false;

    /**
     * Constructor of managing class.
     * @param source - data dump file path.
     * @param output - path to lucene index dir.
     * @param confStream - Input stream with configuration in properties. 
     * @param indexAbs - if true AbstractsDumpIndexingHandler'll be used
     */
    public WikiDumpIndexer(String source, String output,
            InputStream confStream, boolean indexAbs) {
        indexAbstracts = indexAbs;
        try {
            conf.load(confStream);
            logger.info("configuration loaded.");

            sourcePath = source;
            outputPath = output;

            if (sourcePath.startsWith("/test")) {
                data = getClass().getResourceAsStream(sourcePath);
            } else {
                data = new FileInputStream(new File(sourcePath));
            }

        } catch (IOException e) {
            logger.fatal("Error while loading configuration", e);
        }
    }
    
    /**
     * Use this to start indexing process. 
     */
    public void index() {

        logger.info("Starting Wiki dump parsing...");
        final long start = System.currentTimeMillis();

        try {
            final Analyzer analyzer = new StandardAnalyzer();
            final IndexWriter writer = new IndexWriter(outputPath, analyzer,
                    true);
            writer.setMergeFactor(Integer.parseInt(conf
                    .getProperty("lucene.mergeFactor")));
            writer.setMaxMergeDocs(Integer.parseInt(conf
                    .getProperty("lucene.maxMergeDocs")));
            writer.setMaxBufferedDocs(Integer.parseInt(conf
                    .getProperty("lucene.maxBufferedDocs")));

            final SAXParser parser = SAXParserFactory.newInstance()
                    .newSAXParser();
            final XMLReader reader = parser.getXMLReader();

            final AbstractIndexingHandler p;
            if (!indexAbstracts) {
                p = new DataDumpIndexingHandler(conf,writer);
            } else {
                p = new AbstractsDumpIndexingHandler(conf, writer);
            }

            reader.setContentHandler(p);
            reader.parse(new InputSource(data));

            logger.info("Articles added. Optimizing index...");
            writer.optimize();
            writer.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        final long end = System.currentTimeMillis();
        logger.info(MessageFormat.format(
                "Articles extracted in {0,number,#.##} s.",
                new Object[] { new Double((end - start) / 1000.0d) }));
    }

}
