package org.grejpfrut.wiki.searcher.gui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;
import org.carrot2.demo.swing.SwingUtils;
import org.grejpfrut.wiki.process.SearchingSettings;
import org.grejpfrut.wiki.searcher.SearchingApp;
import org.grejpfrut.wiki.searcher.gui.actions.ChangeParentAction;
import org.grejpfrut.wiki.searcher.gui.actions.FileOpenAction;
import org.grejpfrut.wiki.searcher.gui.actions.NewTabAction;
import org.grejpfrut.wiki.searcher.gui.actions.SearchAction;
import org.grejpfrut.wiki.searcher.gui.actions.ShowSettingsAction;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.Options;
import com.jgoodies.uif_lite.panel.SimpleInternalFrame;

/**
 * Searching Swing GUI.
 * 
 * @author Adam Dudczak
 * 
 */
public class SearchingGui {

	private final static Logger logger = Logger.getLogger(SearchingGui.class);

	private static final String DEFAULT_CONF = "/conf.properties";

	private int CLOSE_OPERATION = WindowConstants.EXIT_ON_CLOSE;

	/** Main demo frame */
	private final JFrame frame = new JFrame();

	private final JTabbedPane tabs = new JTabbedPane();

	private final SearchingSettings settings;

	public SearchingGui(Properties customConf) {

		Properties conf = getConfiguration(customConf);

		this.settings = new SearchingSettings(conf);

	}

	public SearchingGui(Properties customConf, boolean isSubwindow) {

		Properties conf = getConfiguration(customConf);

		this.settings = new SearchingSettings(conf);

		if (isSubwindow)
			this.CLOSE_OPERATION = WindowConstants.DISPOSE_ON_CLOSE;

	}

	private Properties getConfiguration(Properties customConf) {
		Properties conf = new Properties();
		try {
			conf.load(SearchingApp.class.getResourceAsStream(DEFAULT_CONF));
		} catch (IOException e) {
			throw new RuntimeException("Cannot find conf properties ", e);
		}
		conf.putAll(customConf);
		return conf;
	}

	public void display() {

		String lafName = LookUtils.IS_OS_WINDOWS_XP ? Options
				.getCrossPlatformLookAndFeelClassName() : Options
				.getSystemLookAndFeelClassName();

		try {
			UIManager.setLookAndFeel(lafName);
		} catch (Exception e) {
			System.err.println("Can't set look & feel:" + e);
		}

		frame.setTitle("Lakon : wiki based searchcing app");
		frame.setDefaultCloseOperation(this.CLOSE_OPERATION);
		 frame.setIconImage(new ImageIcon(this.getClass().getResource(
		 "/images/lakon_ico.gif")).getImage());
		frame.getContentPane().add(getMainPanel());
		frame.pack();

		SwingUtils.centerFrameOnScreen(frame);
		frame.setVisible(true);

	}

	public JComponent getMainPanel() {

		SimpleInternalFrame inFrame = new SimpleInternalFrame(
				"Paste your text and i'll do the rest");
		this.tabs.addTab("text 0", new CloseTabIcon(), new SearchingPanel(
				this.frame, (SearchingSettings) this.settings.createClone()));

		inFrame.setToolBar(addToolBar());

		inFrame.add(this.tabs, BorderLayout.CENTER);

		return inFrame;

	}

	private JToolBar addToolBar() {

		JToolBar toolBar = new JToolBar("Toolbar");
		final SearchingPanel sp = (SearchingPanel) tabs.getSelectedComponent();

		final JButton searchButton = new JButton();
		searchButton.setAction(new SearchAction(sp));
		searchButton.setBorderPainted(false);
		searchButton.setIcon(new ImageIcon(this.getClass().getResource(
				"/images/run2.gif")));
		searchButton.setMnemonic(KeyEvent.VK_ENTER);
		searchButton.setToolTipText("Analyze text, find keywords..[alt+enter]");

		this.tabs.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				ChangeParentAction cpa = (ChangeParentAction) searchButton
						.getAction();
				cpa.setParent((SearchingPanel) tabs.getSelectedComponent());
			}

		});

		final JButton openFileButton = new JButton();
		openFileButton.setAction(new FileOpenAction(this.tabs, this.frame,
				this.settings));
		openFileButton.setMnemonic(KeyEvent.VK_O);
		openFileButton.setBorderPainted(false);
		openFileButton.setIcon(new ImageIcon(this.getClass().getResource(
				"/images/open.png")));
		openFileButton.setToolTipText("Open UTF-8 encoded file [alt+o]");

		final JButton showSettings = new JButton();
		showSettings.setAction(new ShowSettingsAction(sp));
		showSettings.setIcon(new ImageIcon(this.getClass().getResource(
				"/images/settings.gif")));
		showSettings.setBorderPainted(false);
		showSettings.setMnemonic(KeyEvent.VK_S);
		showSettings.setToolTipText("Show/hide settings panel [alt+s]");
		this.tabs.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				ChangeParentAction cpa = (ChangeParentAction) showSettings
						.getAction();
				cpa.setParent((SearchingPanel) tabs.getSelectedComponent());
			}
		});

		final JButton newAction = new JButton();
		newAction.setAction(new NewTabAction(this.tabs, this.frame,
				this.settings));
		newAction.setIcon(new ImageIcon(this.getClass().getResource(
				"/images/new.gif")));
		newAction.setMnemonic(KeyEvent.VK_N);
		newAction.setBorderPainted(false);
		newAction.setToolTipText("New tab [alt+n]");

		toolBar.add(searchButton);
		toolBar.add(openFileButton);
		toolBar.add(newAction);
		toolBar.add(showSettings);
		return toolBar;

	}
}
