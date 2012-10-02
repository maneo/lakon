package org.grejpfrut.lakon.gui.methods;

import java.awt.Frame;
import java.util.Properties;

import javax.swing.JTextArea;

import org.grejpfrut.lakon.summarizer.Summarizer;
import org.grejpfrut.lakon.summarizer.methods.RandomSummarizer;
import org.grejpfrut.lakon.summarizer.settings.Settings;

/**
 *
 * @author Adam Dudczak
 */
public class RandomSummMethod extends SummarizationMethod {

	public RandomSummMethod(Properties conf, JTextArea input, Frame parent) {
		super(conf, input, parent);
	}

	@Override
	protected Settings getSettings(Properties conf) {
		return new Settings(conf);
	}

	@Override
	protected Summarizer getSummarizer() {
		return new RandomSummarizer(super.settings);
	}

}
