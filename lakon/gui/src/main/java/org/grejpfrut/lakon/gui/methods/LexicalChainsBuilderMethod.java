package org.grejpfrut.lakon.gui.methods;

import java.awt.Component;
import java.awt.Frame;
import java.util.List;
import java.util.Properties;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.grejpfrut.lakon.gui.components.ProgressDialog;
import org.grejpfrut.lakon.gui.settings.LexicalChainsMethodSettings;
import org.grejpfrut.lakon.gui.settings.ProcessSettingsListener;
import org.grejpfrut.lakon.gui.utils.SwingWorker;
import org.grejpfrut.lakon.summarizer.methods.LexicalChainsSummarizer;
import org.grejpfrut.lakon.summarizer.methods.lc.LexicalChain;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.tiller.builders.ArticleBuilder;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.utils.TillerConfiguration;

/**
 *
 * @author Adam Dudczak
 */
public class LexicalChainsBuilderMethod extends LexicalChainSummMethod {
	
	private ArticleBuilder artBuilder = new ArticleBuilder(new TillerConfiguration());

	public LexicalChainsBuilderMethod(Properties conf, JTextArea input, Frame parent) {
		super(conf, input, parent);
	}

	@Override
	protected String getInput() {
		return this.inputTextArea.getText();
	}

	@Override
	protected void setResult(String result) {
		this.resultsTextPane.setText(result);
	}
	

	@Override
	public void execute() {
		final LexicalChainsSummarizer summ = (LexicalChainsSummarizer) getSummarizer();
		final String input = super.getInput();
		
		if (input != null && !"".equals(input) ) {

			final ProgressDialog progressDialog = new ProgressDialog(parent);

			new SwingWorker() {
				public Object construct() {
					ProgressDialog.showDialog(progressDialog);
					Article art = artBuilder.getArcticle(input);
					List<LexicalChain> chains = summ.getChains(art); 
					StringBuffer sb = new StringBuffer();
					for (LexicalChain chain : chains) {
						sb.append("<ul> ");
						sb.append("<li> score: "+ chain.getScore() +" </li>");
						//sb.append("<li> synset id: "+chain.getSynsetId() +" </li>");
						sb.append("<li> elements: "+chain.getElements() +" </li>");
						sb.append("<li> representatives : "+chain.getRepresentativeElements() +" </li>");
						sb.append("</ul>");
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

	public Component getResultPanel() {
		return this.getPanel(this.resultsTextPane);
	}

	@Override
	protected void setMethodSettings() {
		settingsPanel = new LexicalChainsMethodSettings(settings, true);
		settingsPanel.addListener(new ProcessSettingsListener() {
			public void settingsChanged(Settings newSettings) {
				settings = newSettings;
				execute();
			}
		});
	}
	
}
