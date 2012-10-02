package org.grejpfrut.wiki.searcher.gui.actions;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;

import org.carrot2.demo.swing.SwingUtils;
import org.grejpfrut.wiki.process.SearchingSettings;
import org.grejpfrut.wiki.searcher.gui.SearchingPanel;

/**
 * It opens UTF-8 file and puts its content into parent container.
 * 
 * @author Adam Dudczak
 * 
 */
public class FileOpenAction extends AbstractAction {

	private static final String WORKING_DIR = "working.dir";

	private JTabbedPane parent;

	private Frame frame;

	private SearchingSettings settings;

	public FileOpenAction(JTabbedPane parent, Frame frame,
			SearchingSettings settings) {
		super();
		this.parent = parent;
		this.frame = frame;
		this.settings = settings;
	}

	/**
	 * If there are any opened SearchingPanels this method checks whether they
	 * have text in their textareas, if not method places choosen file in this
	 * empty tab, and puts name of file as title of the tab.
	 */
	public void actionPerformed(ActionEvent e) {

		Preferences prefs = Preferences.systemNodeForPackage(this.getClass());
		JFileChooser chooser = new JFileChooser(new File(prefs.get(
				WORKING_DIR, "/")));
		chooser.showOpenDialog(parent);

		File file = chooser.getSelectedFile();
		if (file != null) {
			prefs.put(WORKING_DIR, file.getAbsolutePath());
			final String text = getSelectedFile(file);
			final SearchingPanel sp = (SearchingPanel) parent
					.getSelectedComponent();
			if ((sp != null) && (sp.isEmpty())) {
				sp.setText(text);
				parent.setTitleAt(parent.getSelectedIndex(), file.getName());
			} else {
				AbstractAction aa = new NewTabAction(this.parent, this.frame,
						this.settings);
				aa.actionPerformed(new ActionEvent(this, 0, file.getName()));
				SearchingPanel newsp = (SearchingPanel) parent
						.getSelectedComponent();
				newsp.setText(text);
			}
		}

	}

	private String getSelectedFile(File file) {

		BufferedReader in;
		StringBuffer sb = new StringBuffer();
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), "UTF8"));
			String str = null;
			while ((str = in.readLine()) != null) {
				sb.append(str + "\n");
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Exception while loading text file", e);
		} catch (FileNotFoundException e) {
			SwingUtils.showExceptionDialog(parent, "File not found "
					+ file.getName(), e);
		} catch (IOException e) {
			SwingUtils.showExceptionDialog(parent, "Error while reading file "
					+ file.getName(), e);
		}
		return sb.toString();
	}

}
