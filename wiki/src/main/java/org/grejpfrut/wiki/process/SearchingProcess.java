package org.grejpfrut.wiki.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hit;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.grejpfrut.wiki.data.SimpleResult;
import org.grejpfrut.wiki.utils.TFIDFCalc;
import org.grejpfrut.wiki.utils.TermScore;

/**
 * Class represents searching process.
 * 
 * @author Adam Dudczak
 * 
 */
public class SearchingProcess {

	private IndexingSettings settings;

	public SearchingProcess(IndexingSettings settings) {

		this.settings = settings;

	}

	/**
	 * Use this method if you want to perform searching process.
	 * 
	 * @param text -
	 *            String with text of article.
	 * @return SimpleResult of analyze.
	 * @throws IOException -
	 *             if index dir is corrupted.
	 */
	public SimpleResult process(String text) throws IOException {

		final Set<String> stopWords = (Set<String>) settings
				.getParam(IndexingSettings.INDEXER_IGNORED_WORDS);
		final Integer numberOfDocInRes = new Integer((String) settings
				.getParam(SearchingSettings.NUMBER_OF_DOCS_IN_RESULT));

		final Analyzer analyzer = new StandardAnalyzer(stopWords);
		final RAMDirectory ram = new RAMDirectory();

		index(text, analyzer, ram);

		final String wikiIndex = (String) settings
				.getParam(IndexingSettings.LUCENE_DATADUMP_INDEX_DIR);
		Directory wikiIndexDir = null;
		wikiIndexDir = FSDirectory.getDirectory(wikiIndex, false);

		TFIDFCalc keywords = new TFIDFCalc(ram, wikiIndexDir, new Integer(
				(String) settings
						.getParam(SearchingSettings.NUMBER_OF_KEYWORDS)),
				new Double((String) settings
						.getParam(SearchingSettings.MIN_TFIDF_VALUE)));
		List<TermScore> kw = keywords.getKeywords();

		BooleanQuery query = new BooleanQuery();
		for (final TermScore ts : kw) {
			final TermQuery tq = new TermQuery(new Term("text", ts.getTerm()));
			tq.setBoost((float) ts.getTfidf());
			query.add(tq, BooleanClause.Occur.SHOULD);
		}

		IndexSearcher is = new IndexSearcher(wikiIndexDir);

		Hits hits = null;
		List<String> titles = new ArrayList<String>();
		hits = is.search(query);
		Iterator it = hits.iterator();
		int count = 0;
		while (it.hasNext()) {
			Hit hit = (Hit) it.next();
			Document doc = hit.getDocument();
			titles.add(doc.getField("title").stringValue());
			if (count == numberOfDocInRes)
				break;
			count++;
		}
		is.close();
		return new SimpleResult(kw, titles);

	}

	private void index(final String text, final Analyzer analyzer,
			final RAMDirectory ram) throws IOException {
		final IndexWriter writer = new IndexWriter(ram, analyzer, true);

		IndexingProcess iproc = new IndexingProcess(this.settings, writer);
		iproc.process(text);

		writer.optimize();
		writer.close();
	}
}
