package org.grejpfrut.lakon.gui.methods;

import java.awt.Frame;
import java.util.Properties;

import javax.swing.JTextArea;

import org.grejpfrut.lakon.gui.settings.LexicalChainsMethodSettings;
import org.grejpfrut.lakon.gui.settings.ProcessSettingsListener;
import org.grejpfrut.lakon.summarizer.Summarizer;
import org.grejpfrut.lakon.summarizer.methods.LexicalChainsSummarizer;
import org.grejpfrut.lakon.summarizer.settings.LexicalChainsSettings;
import org.grejpfrut.lakon.summarizer.settings.Settings;

/**
 * 
 * @author Adam Dudczak
 */
public class LexicalChainSummMethod extends SummarizationMethod {

	public LexicalChainSummMethod(Properties conf, JTextArea input, Frame frame) {
		super(conf, input, frame);
	}

	@Override
	protected Settings getSettings(Properties conf) {
		return new LexicalChainsSettings(conf);
	}

	@Override
	protected Summarizer getSummarizer() {
		return new LexicalChainsSummarizer(super.settings);
	}

	@Override
	protected void setMethodSettings() {
		settingsPanel = new LexicalChainsMethodSettings(settings);
		settingsPanel.addListener(new ProcessSettingsListener() {
			public void settingsChanged(Settings newSettings) {
				settings = newSettings;
				execute();
			}
		});
	}

	
}
