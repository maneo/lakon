package org.grejpfrut.lakon.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;
import org.carrot2.demo.swing.SwingUtils;
import org.grejpfrut.lakon.gui.actions.BuildThesauriAction;
import org.grejpfrut.lakon.gui.actions.CalculateAveLengthAction;
import org.grejpfrut.lakon.gui.actions.ExecuteMethodAction;
import org.grejpfrut.lakon.gui.actions.FileOpenAction;
import org.grejpfrut.lakon.gui.actions.HideSettingsAction;
import org.grejpfrut.lakon.gui.actions.RunSearchingGuiAction;
import org.grejpfrut.lakon.gui.actions.RunThesauriDemo;
import org.grejpfrut.lakon.gui.methods.LexicalChainSummMethod;
import org.grejpfrut.lakon.gui.methods.LexicalChainsBuilderMethod;
import org.grejpfrut.lakon.gui.methods.LocationBasedSummMethod;
import org.grejpfrut.lakon.gui.methods.MethodBase;
import org.grejpfrut.lakon.gui.methods.RandomSummMethod;
import org.grejpfrut.lakon.gui.methods.TextInfoMethod;
import org.grejpfrut.lakon.gui.methods.TillerDemoMethod;
import org.grejpfrut.lakon.gui.methods.WeightsBasedSummMethod;
import org.grejpfrut.tiller.utils.ClassTool;
import org.grejpfrut.tiller.utils.TillerConfiguration;
import org.grejpfrut.tiller.utils.thesaurus.ThesaurusFactory;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.Options;
import com.jgoodies.looks.plastic.theme.DesertGreen;
import com.jgoodies.uif_lite.panel.SimpleInternalFrame;

/**
 * 
 * @author Adam Dudczak
 */
public class LakonGui {

	private final static Logger logger = Logger.getLogger(LakonGui.class);

	/** Main demo frame */
	private final JFrame frame = new JFrame();

	private SimpleInternalFrame inputPanel;

	private SimpleInternalFrame resultPanel;

	private SimpleInternalFrame settingsPanel;

	private SimpleInternalFrame methodPanel;

	private JList methodNames;

	private Map<String, MethodBase> methods = new HashMap<String, MethodBase>();

	private String currentMethod;

	private JButton executeButton;

	private JButton openFileButton;

	private JButton showSettings;

	private JSplitPane rightSplit;

	private JTextArea inputTextArea;

	private JSplitPane leftSplit;

	private HideSettingsAction hideAction;

	private Properties conf;

	public static void main(String[] args) {

		Properties conf = new Properties();
		try {
			if (args.length == 1) {
				try {
					InputStream is = new FileInputStream(args[0]);
					if (args[0].endsWith(".xml")) {
						conf.loadFromXML(is);
					} else {
						conf.load(is);
					}
				} catch (FileNotFoundException e) {
					logger.fatal("Given configuration file doesn't exist");
					return;
				}
			} else {
				logger.info("getting default configuration...");
				conf.loadFromXML(LakonGui.class
						.getResourceAsStream("/gui-conf.xml"));
			}
		} catch (InvalidPropertiesFormatException e) {
			logger.fatal("Invalid configuration file format");
			return;
		} catch (IOException e) {
			logger.fatal("Error while reading configuration file");
			return;
		}
		
		// preload thesaurus
		if ( Boolean.valueOf(conf.getProperty("preload.thesauri")) )
		ThesaurusFactory.getThesaurus(conf
				.getProperty(TillerConfiguration.PATH_TO_THESAURUS_PROPERTY));

		LakonGui gui = new LakonGui(conf);
		gui.display();

	}

	public LakonGui(Properties conf) {

		this.conf = conf;
		String[] data = new String[] { "Tiller demo", "Text info",
				"Location based sum.", "Random  sum.", "Weigths based sum.",
				"Lexical chains builder", "Lexical chains sum." };
		setMethodsList(data);

		this.inputTextArea = new JTextArea();
		this.inputTextArea.setLineWrap(true);
		this.inputTextArea.setWrapStyleWord(true);
		
		TillerConfiguration tconf = new TillerConfiguration(this.conf);

		methods.put(data[0], new TillerDemoMethod(tconf, this.inputTextArea, this.frame));
		methods.put(data[1], new TextInfoMethod(tconf, this.inputTextArea, this.frame));
		methods.put(data[2], new LocationBasedSummMethod(this.conf,
				this.inputTextArea, this.frame));
		methods.put(data[3],
				new RandomSummMethod(this.conf, this.inputTextArea, this.frame));
		methods.put(data[4], new WeightsBasedSummMethod(this.conf,
				this.inputTextArea, this.frame));
		methods.put(data[5], new LexicalChainsBuilderMethod(this.conf,
				this.inputTextArea, this.frame));
		methods.put(data[6], new LexicalChainSummMethod(this.conf,
				this.inputTextArea, this.frame));

		// this can take a while
		// this.inputTextArea.addCaretListener(new CaretListener() {
		//
		// public void caretUpdate(CaretEvent e) {
		// String name = (String) methodNames.getSelectedValue();
		// MethodBase base = methods.get(name);
		// base.execute();
		// }
		//			
		// });

	}

	public void display() {

		String lafName = LookUtils.IS_OS_WINDOWS_XP ? Options
				.getCrossPlatformLookAndFeelClassName() : Options
				.getSystemLookAndFeelClassName();

		try {
			LookAndFeel laf = (LookAndFeel) ClassTool.getInstance(lafName);
			LookUtils.setLookAndTheme(laf, new DesertGreen());
		} catch (Exception e) {
			System.err.println("Can't set look & feel:" + e);
		}

		frame.setTitle("Lakon");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setIconImage(new ImageIcon(this.getClass().getResource(
				"/images/run.gif")).getImage());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setPreferredSize(new Dimension(screenSize.width / 2,
				screenSize.height / 2));
		frame.setJMenuBar(getMenuBar());
		frame.getContentPane().add(getMainPanel());
		frame.pack();

		SwingUtils.centerFrameOnScreen(frame);
		frame.setVisible(true);

	}

	public JComponent getMainPanel() {

		JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		mainSplit.setDividerSize(4);
		int location = (int) Math.round(Toolkit.getDefaultToolkit()
				.getScreenSize().getWidth() / 5);
		mainSplit.setDividerLocation(location);
		mainSplit.add(getLeftPanel());
		mainSplit.add(getRightPanel());
		return mainSplit;

	}

	private Component getLeftPanel() {

		leftSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		setSplitPane(leftSplit);

		methodPanel = new SimpleInternalFrame("Method");
		settingsPanel = new SimpleInternalFrame("Settings");

		methodPanel.setToolBar(getMethodToolbar(leftSplit));
		methodPanel.add(this.methodNames);
		leftSplit.add(methodPanel);
		leftSplit.add(settingsPanel);

		return leftSplit;

	}

	private Component setMethodsList(String[] data) {

		methodNames = new JList(data);
		methodNames.setBorder(new CompoundBorder());

		methodNames
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		methodNames.setLayoutOrientation(JList.VERTICAL);
		methodNames.setVisibleRowCount(-1);
		methodNames.setSelectedIndex(0);

		return methodNames;
	}

	private Component getRightPanel() {

		rightSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		setSplitPane(rightSplit);

		if (this.methodNames != null) {

			String methodName = (String) methodNames.getSelectedValue();
			currentMethod = methodName;
			final MethodBase base = this.methods.get(methodName);

			inputPanel = getInputPanel(base);
			resultPanel = new SimpleInternalFrame("Results");

			setNewMethod(rightSplit, base);
			settingsPanel.add(base.getSettingsPanel());

			this.methodNames
					.addListSelectionListener(new ListSelectionListener() {

						public void valueChanged(final ListSelectionEvent e) {

							SwingUtilities.invokeLater(new Runnable() {

								public void run() {

									String methodName = (String) methodNames
											.getSelectedValue();
									if (methodName.equals(currentMethod))
										return;
									logger.debug("method changed");
									currentMethod = methodName;
									MethodBase method = methods.get(methodName);

									rightSplit.remove(inputPanel);
									rightSplit.remove(resultPanel);
									resultPanel = new SimpleInternalFrame(
											"Results");

									leftSplit.remove(settingsPanel);
									settingsPanel = new SimpleInternalFrame(
											"Settings");
									settingsPanel
											.add(method.getSettingsPanel());

									setNewMethod(rightSplit, method);

									leftSplit.add(settingsPanel);
									setSplitPane(rightSplit);
									setSplitPane(leftSplit);

									hideAction.setComponent(settingsPanel);

									if (!inputTextArea.getText().equals(""))
										method.execute();
								}
							});
						}

					});

		}

		return rightSplit;
	}

	private SimpleInternalFrame getInputPanel(final MethodBase base) {
		SimpleInternalFrame iPanel = new SimpleInternalFrame("Input");

		iPanel.setToolBar(this.getInputToolBar(base, iPanel, this));

		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(this.inputTextArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scrollPane);

		iPanel.add(panel);

		return iPanel;
	}

	private JSplitPane setSplitPane(JSplitPane pane) {
		pane.setDividerSize(4);
		int location = (int) Math.round(Toolkit.getDefaultToolkit()
				.getScreenSize().getHeight() / 4);
		pane.setDividerLocation(location);
		return pane;
	}

	private void setNewMethod(final JSplitPane rightSplit, final MethodBase base) {

		inputPanel.setToolBar(getInputToolBar(base, inputPanel, this));
		rightSplit.add(inputPanel);

		resultPanel.add(base.getResultPanel());
		rightSplit.add(resultPanel);

	}

	private JMenuBar getMenuBar() {

		JMenuBar mainMenu = new JMenuBar();

		mainMenu.add(getFileMenu());
		mainMenu.add(getToolsMenu());

		return mainMenu;

	}

	private Component getToolsMenu() {
		JMenu toolsMenu = new JMenu("Tools");

		JMenuItem runSearch = new JMenuItem();
		runSearch.setAction(new RunSearchingGuiAction(this.conf));
		runSearch.setText("Run searching demo");
		toolsMenu.add(runSearch);

		JMenuItem runAveDocu = new JMenuItem();
		runAveDocu.setAction(new CalculateAveLengthAction(this.frame));
		runAveDocu.setText("Average length");
		runAveDocu
				.setToolTipText("Calculates average document length in given index");
		toolsMenu.add(runAveDocu);

		JMenuItem runThesBuilder = new JMenuItem();
		runThesBuilder.setAction(new BuildThesauriAction(this.frame));
		runThesBuilder.setText("Build thesauri");
		runThesBuilder
				.setToolTipText("Gets thesauri in Lakon format from OpenOffice format");
		toolsMenu.add(runThesBuilder);

		JMenuItem runThesDemo = new JMenuItem();
		runThesDemo
				.addActionListener(new RunThesauriDemo(this.frame, this.conf));
		runThesDemo.setText("Thesauri demo");
		toolsMenu.add(runThesDemo);

		return toolsMenu;
	}

	private Component getFileMenu() {
		JMenu viewMenu = new JMenu("File");

		String name = this.methodNames.getSelectedValue().toString();
		MethodBase method = this.methods.get(name);

		JMenuItem openFile = new JMenuItem("Open file");
		openFile.setAction(new FileOpenAction(method,frame, this));
		openFile.setText("Open file");
		viewMenu.add(openFile);

		JMenuItem quit = new JMenuItem();
		quit.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
			
		});
		quit.setText("Quit");
		viewMenu.add(quit);

		return viewMenu;
	}


	private JToolBar getMethodToolbar(JSplitPane middle) {

		JToolBar toolBar = new JToolBar("Methods");
		showSettings = new JButton();
		hideAction = new HideSettingsAction(this.settingsPanel, middle);
		showSettings.setAction(hideAction);
		showSettings.setIcon(new ImageIcon(this.getClass().getResource(
				"/images/settings.gif")));
		showSettings.setBorderPainted(false);
		showSettings.setMnemonic(KeyEvent.VK_S);
		showSettings.setToolTipText("Show/hide settings panel [alt+s]");
		toolBar.add(showSettings);
		return toolBar;

	}

	private JToolBar getInputToolBar(final MethodBase base, Component parent,
			LakonGui gui) {

		JToolBar toolBar = new JToolBar("Input");

		executeButton = new JButton();
		executeButton.setAction(new ExecuteMethodAction(base));
		executeButton.setBorderPainted(false);
		executeButton.setIcon(new ImageIcon(this.getClass().getResource(
				"/images/run2.gif")));
		executeButton.setMnemonic(KeyEvent.VK_ENTER);
		executeButton
				.setToolTipText("Analyze text, find keywords..[alt+enter]");

		openFileButton = new JButton();
		openFileButton.setAction(new FileOpenAction(base, parent, gui));
		openFileButton.setMnemonic(KeyEvent.VK_O);
		openFileButton.setBorderPainted(false);
		openFileButton.setIcon(new ImageIcon(this.getClass().getResource(
				"/images/open.png")));
		openFileButton.setToolTipText("Open UTF-8 encoded file [alt+o]");

		toolBar.add(executeButton);
		toolBar.add(openFileButton);

		return toolBar;

	}

	public void clearMethodsResult() {

		synchronized (this.methods) {
			for (Entry<String, MethodBase> nmethod : this.methods.entrySet()) {
				nmethod.getValue().clearResults();
			}
		}
	}

}
