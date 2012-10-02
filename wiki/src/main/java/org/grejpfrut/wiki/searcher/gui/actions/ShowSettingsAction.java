package org.grejpfrut.wiki.searcher.gui.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;

import org.grejpfrut.wiki.searcher.gui.SearchingPanel;

/**
 * Shows and hides searching dialog.
 * 
 * @author Adam Dudczak
 * 
 */
public class ShowSettingsAction extends AbstractAction implements
		ChangeParentAction {

	private SearchingPanel parent;

	public ShowSettingsAction(SearchingPanel parent) {
		this.parent = parent;
	}

	public void actionPerformed(ActionEvent e) {

		if (this.parent != null) {
			SwingUtilities.invokeLater(new Runnable() {

				public void run() {
					parent.settingsVisible(!parent.isSettingsVisible());
				}

			});
		}
	}

	public void setParent(Component parent) {
		this.parent = (SearchingPanel) parent;
	}

}
