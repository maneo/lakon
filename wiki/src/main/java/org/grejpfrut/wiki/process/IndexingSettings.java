package org.grejpfrut.wiki.process;

import java.awt.Frame;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JComponent;

import org.carrot2.demo.ProcessSettings;
import org.carrot2.demo.ProcessSettingsBase;

/**
 * Class represents indexing settings.  
 * @author Adam Dudczak
 *
 */
public class IndexingSettings extends ProcessSettingsBase {

	@Override
	public boolean hasSettings() {
		return true;
	}

	public static final String LUCENE_DATADUMP_INDEX_DIR = "lucene.index.dir";

	public static final String INDEXER_PUT_STEMS_INSTEAD_OF_ARTICLES = "indexer.putStems.insteadOf.articles";

	public static final String INDEXER_IGNORED_WORDS = "indexer.ignored.words";

	public static final String CLEAN_WIKI_MARKUP = "clean.wiki.markup";

	public static final String INDEXER_STEMMER_CLASS = "indexer.stemmer.class";

	public IndexingSettings(Properties conf) {

		params = new HashMap();
		params.put(INDEXER_PUT_STEMS_INSTEAD_OF_ARTICLES, new Boolean(conf
				.getProperty(INDEXER_PUT_STEMS_INSTEAD_OF_ARTICLES)));
		params.put(CLEAN_WIKI_MARKUP, new Boolean(conf
				.getProperty(CLEAN_WIKI_MARKUP)));

		params.put(INDEXER_IGNORED_WORDS, getStopWords(conf
				.getProperty(INDEXER_IGNORED_WORDS)));

		params.put(INDEXER_STEMMER_CLASS, conf
				.getProperty(INDEXER_STEMMER_CLASS));

		params.put(LUCENE_DATADUMP_INDEX_DIR, conf
				.getProperty(LUCENE_DATADUMP_INDEX_DIR));

	}

	public IndexingSettings(Map params) {
		this.params = params;
	}

	@Override
	public ProcessSettings createClone() {
		synchronized (this) {
			return new IndexingSettings(params);
		}
	}

	@Override
	public JComponent getSettingsComponent(Frame owner) {
		return null;
	}

	@Override
	public boolean isConfigured() {
		return true;
	}

	private Set<String> getStopWords(String stopWordsList) {

		Set<String> ignoredWords = new HashSet<String>();

		StringTokenizer st = new StringTokenizer(stopWordsList, ",");
		while (st.hasMoreTokens()) {
			ignoredWords.add(st.nextToken());
		}
		return ignoredWords;
	}

	public Object getParam(String paramName) {
		return this.params.get(paramName);
	}

}
