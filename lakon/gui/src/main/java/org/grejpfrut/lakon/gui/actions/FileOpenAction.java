package org.grejpfrut.lakon.gui.actions;

import java.awt.Component;
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

import org.carrot2.demo.swing.SwingUtils;
import org.grejpfrut.lakon.gui.LakonGui;
import org.grejpfrut.lakon.gui.methods.MethodBase;

/**
 * 
 * @author Adam Dudczak
 */
public class FileOpenAction extends AbstractAction  {

	public static final String WORKING_DIR = "working.dir";

	private MethodBase method;

	private final Component parent;
	
	private LakonGui gui;

	public FileOpenAction(MethodBase method, Component parent, LakonGui gui) {
		this.method = method;
		this.parent = parent;
		this.gui = gui; 
	}

	public void actionPerformed(ActionEvent e) {

		Preferences prefs = Preferences.systemNodeForPackage(this.getClass());
		JFileChooser chooser = new JFileChooser(new File(prefs.get(WORKING_DIR,
				"/")));
		chooser.showOpenDialog(null);

		File file = chooser.getSelectedFile();
		if (file != null) {
			prefs.put(WORKING_DIR, file.getAbsolutePath());
			final String text = getSelectedFile(file);
			gui.clearMethodsResult();
			method.setInput(text);
			method.execute();
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
