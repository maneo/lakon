package org.grejpfrut.wiki.searcher.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.carrot2.demo.settings.ThresholdHelper;
import org.grejpfrut.wiki.process.SearchingSettings;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Settings dialog.
 * @author Adam Dudczak
 *
 */
public class SearchingSettingsDialog extends JPanel {

	private SearchingSettings settings;

	public SearchingSettingsDialog(SearchingSettings settings) {

		this.settings = settings;
		buildGui();

	}

	private void buildGui() {

		this.setLayout(new BorderLayout());

		final DefaultFormBuilder builder = new DefaultFormBuilder(
				new FormLayout("pref", ""));

		builder.appendSeparator("Topic Searching");

		builder.append(ThresholdHelper.createIntegerThreshold(settings,
				SearchingSettings.NUMBER_OF_KEYWORDS, "Number of keywords", 2,
				52, 1, 10));
		builder.append(ThresholdHelper.createDoubleThreshold(settings,
				SearchingSettings.MIN_TFIDF_VALUE, "Minimal TFIDF value:", 0.0,
				1.0, 0.1, 0.2));
		builder.append(ThresholdHelper.createIntegerThreshold(settings,
				SearchingSettings.NUMBER_OF_DOCS_IN_RESULT, "Result set size", 2,
				52, 1, 10));

		this.add(builder.getPanel(), BorderLayout.CENTER);

	}

}
