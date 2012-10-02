package org.grejpfrut.wiki.process;

import java.awt.Frame;
import java.util.Map;
import java.util.Properties;

import javax.swing.JComponent;

import org.carrot2.demo.ProcessSettings;
import org.grejpfrut.wiki.searcher.gui.SearchingSettingsDialog;

/**
 * Searching settings extends Indexing settings.
 * @author Adam Dudczak
 *
 */
public class SearchingSettings extends IndexingSettings implements ProcessSettings{

	public static final String MIN_TFIDF_VALUE = "min.tfidf.value";

	public static final String NUMBER_OF_KEYWORDS = "number.of.keywords";

	public static final String NUMBER_OF_DOCS_IN_RESULT = "number.of.doc.in.result";
	

	public SearchingSettings(Properties conf) {
		super(conf);
		
		params.put(NUMBER_OF_KEYWORDS, Integer.toString(new Integer(conf
				.getProperty(NUMBER_OF_KEYWORDS))));

		params.put(MIN_TFIDF_VALUE, Double.toString(new Double(conf
				.getProperty(MIN_TFIDF_VALUE))));
		
		params.put(NUMBER_OF_DOCS_IN_RESULT, Integer.toString(new Integer(conf
				.getProperty(NUMBER_OF_DOCS_IN_RESULT) ) )) ;
		
		this.setLiveUpdate(true);
	}

	public SearchingSettings(Map params) {
		super(params);
	}

	@Override
	public ProcessSettings createClone() {
		synchronized (this) {
			return new SearchingSettings(params);
		}
	}

	@Override
	public JComponent getSettingsComponent(Frame arg0) {
		return new SearchingSettingsDialog(this);
	}

	@Override
	public boolean isConfigured() {
		return true;
	}

}
