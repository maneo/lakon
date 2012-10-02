package org.grejpfrut.wiki.searcher.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.carrot2.demo.ProcessSettings;
import org.carrot2.demo.ProcessSettingsListener;
import org.grejpfrut.wiki.data.SimpleResult;
import org.grejpfrut.wiki.process.SearchingSettings;
import org.grejpfrut.wiki.searcher.gui.actions.SearchAction;

/**
 * Searching Panel is a Swing component with all that is necessary to visualize
 * results.
 * 
 * @author Adam Dudczak
 * 
 */
public class SearchingPanel extends JPanel {

	private JSplitPane mainPanel;

	private JTextArea text;

	private JList resultTitles;

	private JTable resKeywordsTable;

	private JComponent settingsComponent;

	private SearchingSettings settings;

	private final Frame parent;

	public SearchingPanel(Frame parent, SearchingSettings settings) {

		this.setLayout(new BorderLayout());
		this.parent = parent;
		this.settings = settings;

		this.resKeywordsTable = new JTable();
		this.resKeywordsTable.setBackground(this.getBackground());

		this.mainPanel = setMainPanel();

		this.add(this.mainPanel, BorderLayout.CENTER);
		this.add(getSettingsPane(parent), BorderLayout.EAST);

		final SearchingPanel sp = this;
		this.settings.addListener(new ProcessSettingsListener() {

			public void settingsChanged(ProcessSettings settings) {
				new SearchAction(sp).actionPerformed(new ActionEvent(settings,
						0, "go"));
			}

		});

	}

	private JSplitPane setMainPanel() {
		JSplitPane mPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		int location = (int) Math.round(Toolkit.getDefaultToolkit()
				.getScreenSize().getHeight() / 4);
		mPanel.setDividerLocation(location);
		mPanel.setTopComponent(getTextAreaScrollPane());
		mPanel.setBottomComponent(getBottomPane());
		return mPanel;
	}

	private JComponent getSettingsPane(Frame parent) {

		this.settingsComponent = this.settings.getSettingsComponent(parent);
		return this.settingsComponent;
	}

	private JScrollPane getTextAreaScrollPane() {

		this.text = new JTextArea(10, 20);
		text.setWrapStyleWord(true);
		text.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(this.text,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		return scrollPane;
	}

	private JComponent getBottomPane() {

		KeywordsTableModel resKeywordsModel = new KeywordsTableModel();
		this.resKeywordsTable.setModel(resKeywordsModel);

		JScrollPane lscroll = new JScrollPane(this.resKeywordsTable);

		this.resultTitles = new JList();
		this.resultTitles.setBackground(this.getBackground());
		JScrollPane rscroll = new JScrollPane(this.resultTitles);

		JPanel left = new JPanel(new BorderLayout());
		left
				.add(new JLabel("<html><b>Keywords</b></html>"),
						BorderLayout.NORTH);
		left.add(lscroll, BorderLayout.CENTER);

		JPanel right = new JPanel(new BorderLayout());
		right.add(new JLabel("<html><b>Similar articles</b></html>"),
				BorderLayout.NORTH);
		right.add(rscroll, BorderLayout.CENTER);

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left,
				right);

		return split;
	}

	/**
	 * Puts new text into textarea component.
	 * 
	 * @param text
	 *            new text.
	 */
	public void setText(String newText) {
		this.text.setText(newText);
		this.text.invalidate();
	}

	/**
	 * Gets text from textarea.
	 * 
	 * @return String with text.
	 */
	public String getText() {
		return this.text.getText();
	}

	/**
	 * Checks if textarea is empy.
	 * 
	 * @return true if textarea is empty.
	 */
	public boolean isEmpty() {
		final String text = this.text.getText();
		if ((text == null) || (text.length() == 0))
			return true;
		return false;
	}

	public void settingsVisible(boolean visibility) {
		this.settingsComponent.setVisible(visibility);
		this.settingsComponent.invalidate();
	}

	public boolean isSettingsVisible() {
		return this.settingsComponent.isVisible();
	}

	public SearchingSettings getSettings() {
		return settings;
	}

	public void setResults(final SimpleResult res) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				final KeywordsTableModel keywords = (KeywordsTableModel) resKeywordsTable
						.getModel();
				keywords.setTermScores(res.getKeywords());
				resultTitles.setListData(res.getTitles());

			}

		});

	}

}
