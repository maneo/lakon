package org.grejpfrut.wiki.searcher.gui.actions;

import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTabbedPane;

import org.grejpfrut.wiki.process.SearchingSettings;
import org.grejpfrut.wiki.searcher.gui.CloseTabIcon;
import org.grejpfrut.wiki.searcher.gui.SearchingPanel;

/**
 * Opens new tab, creates new SearchingPanel instance.
 * @author Adam Dudczak
 *
 */
public class NewTabAction extends AbstractAction {

	private final JTabbedPane tabs;

	private final Frame frame;

	private final SearchingSettings settings;

	private int counter = 1;

	public NewTabAction(JTabbedPane tabs, Frame frame,
			SearchingSettings settings) {
		this.tabs = tabs;
		this.frame = frame;
		this.settings = settings;
	}

	public void actionPerformed(ActionEvent e) {
		String tabName = "text " + this.counter;
		if (e.getActionCommand() != null)
			tabName = e.getActionCommand();
		final SearchingSettings sett = (SearchingSettings) this.settings
				.createClone();
		final SearchingPanel sp = new SearchingPanel(this.frame, sett);
		tabs.addTab(tabName, new CloseTabIcon(), sp);
		tabs.setSelectedIndex(tabs.getTabCount() - 1);
		this.counter++;

	}

}
