package org.grejpfrut.wiki.searcher.gui.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.grejpfrut.wiki.data.SimpleResult;
import org.grejpfrut.wiki.process.SearchingProcess;
import org.grejpfrut.wiki.searcher.gui.SearchingPanel;

/**
 * Launches searching process.
 * 
 * @author Adam Dudczak
 * 
 */
public class SearchAction extends AbstractAction implements ChangeParentAction {

	private SearchingPanel parent;

	public SearchAction(SearchingPanel parent) {
		this.parent = parent;
	}

	public void actionPerformed(ActionEvent e) {

		if (this.parent != null) {
			SearchingProcess sprocces = new SearchingProcess(this.parent
					.getSettings());
			final String text = parent.getText();
			if ((text != null) && (!"".equals(text))) {

				try {
					SimpleResult result = sprocces.process(text);
					parent.setResults(result);
				} catch (IOException io) {
					JOptionPane
							.showMessageDialog(
									null,
									"Check configuration probably lucene index dir is corrupted",
									"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	public void setParent(Component parent) {
		this.parent = (SearchingPanel) parent;
	}

}
