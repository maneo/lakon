package org.grejpfrut.lakon.gui.methods;

import java.awt.Frame;
import java.util.Properties;

import javax.swing.JTextArea;

import org.grejpfrut.lakon.gui.settings.LocationMethodSettings;
import org.grejpfrut.lakon.gui.settings.ProcessSettingsListener;
import org.grejpfrut.lakon.summarizer.Summarizer;
import org.grejpfrut.lakon.summarizer.methods.LocationBasedSummarizer;
import org.grejpfrut.lakon.summarizer.settings.LocationSettings;
import org.grejpfrut.lakon.summarizer.settings.Settings;

/**
 * 
 * @author Adam Dudczak
 */
public class LocationBasedSummMethod extends  SummarizationMethod{

	public LocationBasedSummMethod(Properties conf, JTextArea input, Frame parent) {
		super(conf, input, parent);
	}

	@Override
	protected Settings getSettings(Properties conf) {
		return new LocationSettings(conf);
	}

	@Override
	protected Summarizer getSummarizer() {
		return new LocationBasedSummarizer(super.settings);
	}

	@Override
	protected void setMethodSettings() {
		settingsPanel = new LocationMethodSettings(settings);
		settingsPanel.addListener(new ProcessSettingsListener() {
			public void settingsChanged(Settings newSettings) {
				settings = newSettings;
				execute();
			}
		});
	}
	



}
