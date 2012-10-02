package org.grejpfrut.lakon.gui.methods;

import java.awt.Component;
import java.awt.Frame;
import java.util.List;
import java.util.Properties;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.grejpfrut.lakon.gui.components.ProgressDialog;
import org.grejpfrut.lakon.gui.components.SummaryResultComponent;
import org.grejpfrut.lakon.gui.settings.MethodSettings;
import org.grejpfrut.lakon.gui.settings.ProcessSettingsListener;
import org.grejpfrut.lakon.gui.utils.SwingWorker;
import org.grejpfrut.lakon.summarizer.Summarizer;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.tiller.entities.Sentence;

/**
 * @author Adam Dudczak
 */
public abstract class SummarizationMethod extends MethodBase {

	protected Settings settings;

	protected MethodSettings settingsPanel;

	protected SummaryResultComponent resultPanel = new SummaryResultComponent();

	public SummarizationMethod(Properties conf, JTextArea input, Frame parent) {
		super(input, parent);
		this.settings = getSettings(conf);
		setMethodSettings();
	}

	protected void setMethodSettings() {
		settingsPanel = new MethodSettings(settings);
		settingsPanel.addListener(new ProcessSettingsListener() {
			public void settingsChanged(Settings newSettings) {
				settings = newSettings;
				if ( getInput() != null )
				execute();
			}
		});
	}

	@Override
	public JComponent getSettingsPanel() {
		return settingsPanel.getSettingsComponent(null);
	}

	protected abstract Settings getSettings(Properties conf);

	protected abstract Summarizer getSummarizer();

	public void execute() {

		final Summarizer summ = getSummarizer();
		final String input = super.getInput();
		if (input != null && !"".equals(input) ) {

			this.resultPanel.setArticle(input);
			final ProgressDialog progressDialog = new ProgressDialog(parent);

			new SwingWorker() {
				public Object construct() {
					ProgressDialog.showDialog(progressDialog);
					List<Sentence> summary = summ.summarize(input);
					StringBuffer sb = new StringBuffer();
					for (Sentence sent : summary) {
						sb.append(sent.getText() + " ");
					}
					return sb.toString();
				}

				public void finished() {
					final String res = (String) this.get();
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							progressDialog.setVisible(false);
							setResult(res);
						}
					});
				}
			}.start();

		}

	}

	@Override
	public Component getResultPanel() {
		return resultPanel;
	}

	@Override
	protected void setResult(String result) {
		this.resultPanel.setSummary(result);
	}

	@Override
	public void setInput(String input) {
		super.setInput(input);
		this.resultPanel.setArticle(input);
	}

}
