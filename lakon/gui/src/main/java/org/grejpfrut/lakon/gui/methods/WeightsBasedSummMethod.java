package org.grejpfrut.lakon.gui.methods;

import java.awt.Frame;
import java.util.Properties;

import javax.swing.JTextArea;

import org.grejpfrut.lakon.gui.settings.ProcessSettingsListener;
import org.grejpfrut.lakon.gui.settings.WeightsMethodSettings;
import org.grejpfrut.lakon.summarizer.Summarizer;
import org.grejpfrut.lakon.summarizer.methods.WeightsBasedSummarizer;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings;

/**
 * 
 * @author Adam Dudczak
 */
public class WeightsBasedSummMethod extends SummarizationMethod {

	public WeightsBasedSummMethod(Properties conf, JTextArea input, Frame parent) {
		super(conf, input, parent);
	}

	@Override
	protected Settings getSettings(Properties conf) {
		return new WeightSettings(conf);
	}

	@Override
	protected Summarizer getSummarizer() {
		return new WeightsBasedSummarizer(super.settings);
	}

	@Override
	protected void setMethodSettings() {
		settingsPanel = new WeightsMethodSettings(settings);
		settingsPanel.addListener(new ProcessSettingsListener() {
			public void settingsChanged(Settings newSettings) {
				settings = newSettings;
				execute();
			}
		});
	}

}
