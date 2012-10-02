package org.grejpfrut.lakon.gui.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.text.MessageFormat;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.carrot2.demo.swing.SwingUtils;
import org.grejpfrut.lakon.gui.components.ProgressDialog;
import org.grejpfrut.lakon.gui.utils.SwingWorker;
import org.grejpfrut.lakon.summarizer.AveDocumentLength;

/**
 * Runs document average length calculator.
 * 
 * @author Adam Dudczak
 */
public class CalculateAveLengthAction extends AbstractAction {

	private JFrame parent;

	private double ave;

	public CalculateAveLengthAction(JFrame parent) {
		this.parent = parent;
	}

	public void actionPerformed(ActionEvent e) {

		Preferences prefs = Preferences.systemNodeForPackage(this.getClass());
		JFileChooser chooser = new JFileChooser(new File(prefs.get(
				FileOpenAction.WORKING_DIR, "/")));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = chooser.showOpenDialog(parent);

		if (result == JFileChooser.CANCEL_OPTION)
			return;

		final File file = chooser.getSelectedFile();

		prefs.put(FileOpenAction.WORKING_DIR, file.getAbsolutePath());
		final ProgressDialog progressDialog = new ProgressDialog(parent);

		new SwingWorker() {
			public Object construct() {
				AveDocumentLength length = new AveDocumentLength();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						progressDialog.setVisible(true);
					}
				});
				double ave = length.getAveLength(file);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						progressDialog.setVisible(false);
					}
				});
				return ave;
			}

			public void finished() {
				SwingUtils
						.showInformationDialog(
								parent,
								MessageFormat
										.format(
												"Average document length in given index is:  {0,number,#.##}",
												new Object[] { this.get() }));
			}
		}.start();

	}
};