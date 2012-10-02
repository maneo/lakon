package org.grejpfrut.wiki.process;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.index.IndexWriter;
import org.grejpfrut.wiki.data.WikiSite;

/**
 * Wiki page processing.
 * 
 * @author Adam Dudczak
 * 
 */
public class WikiIndexingProcess extends IndexingProcess {

	private final static Logger logger = Logger
			.getLogger(WikiIndexingProcess.class);

	public WikiIndexingProcess(IndexingSettings settings, IndexWriter writer) {
		super(settings, writer);

	}

	@Override
	public void process(Object object) {

		WikiSite ws = (WikiSite) object;

		String content = cleanDocument(ws.getArticle());

		ws.setArticle(content);
		if (ws.getTitle().startsWith("Wikipedia:")) {
			ws.setTitle(ws.getTitle().substring("Wikipedia:".length()).trim());
		}
		try {

			writer.addDocument(ws.getLuceneDocument());

		} catch (IOException e) {
			logger.error("Error during adding document to index: "
					+ e.getMessage());
		}

	}

}
