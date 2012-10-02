package org.grejpfrut.wiki.process;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.grejpfrut.wiki.cleaners.CleaningAgent;
import org.grejpfrut.wiki.utils.Stemmer;

/**
 * Basic indexing process. It takes String as a one field lucene document and perform all
 * necessary processing.
 * 
 * @author Adam Dudczak
 * 
 */
public class IndexingProcess  {

	protected IndexingSettings settings;

	protected CleaningAgent cleaner;

	protected final Stemmer st;

	protected final IndexWriter writer;
	
	protected final boolean cleanWikiMarkup;
	
	protected final boolean putArtAsStem;


	/**
	 * Creates cleaning agents, gets configuration, creates stemmer class.
	 * 
	 * @param conf
	 * @param writer
	 */
	protected IndexingProcess(IndexingSettings settings, IndexWriter writer) {

		this.settings = settings;

		this.cleaner = new CleaningAgent();

		this.st = Stemmer.getStemmer(settings);

		this.writer = writer;
		
		this.cleanWikiMarkup = (Boolean)settings.getParam(IndexingSettings.CLEAN_WIKI_MARKUP);
		
		this.putArtAsStem = (Boolean)settings.getParam(IndexingSettings.INDEXER_PUT_STEMS_INSTEAD_OF_ARTICLES);

	}

	/**
	 * Use this method if you want to index text. 
	 * @param text String with text.
	 */
	public void process(Object object){
		
		String text = (String)object;
		
        final Document doc = new Document();
        doc.add(new Field("text", text, Field.Store.YES,
                Field.Index.TOKENIZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
		try {
			writer.addDocument(doc);
		} catch (IOException e) {
			throw new RuntimeException("Error while adding document to index",e);
		}
		
		
	}
	
	protected String cleanDocument(String content){
		
		if (cleanWikiMarkup) {
			content = cleaner.cleanMarkup(content);
		}
		if (putArtAsStem) {
			content = st.getDocAsStems(content);
		}
		return content;
		
	}

}
