package org.grejpfrut.lakon.gui.actions;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.carrot2.demo.swing.SwingUtils;
import org.grejpfrut.lakon.gui.components.ProgressDialog;
import org.grejpfrut.lakon.gui.components.ThesaurusBuildingDialog;
import org.grejpfrut.lakon.gui.utils.SwingWorker;
import org.grejpfrut.tiller.utils.thesaurus.SynSet;
import org.grejpfrut.tiller.utils.thesaurus.Thesaurus;
import org.grejpfrut.tiller.utils.thesaurus.ThesaurusFactory;
import org.grejpfrut.tiller.utils.thesaurus.ThesaurusParser;

/**
 * 
 * @author Adam Dudczak
 */
public class BuildThesauriAction extends AbstractAction {

	private Frame parent;

	private ThesaurusBuildingDialog dialog;

	public BuildThesauriAction(JFrame frame) {
		this.parent = frame;
		this.dialog = new ThesaurusBuildingDialog(this.parent, this);

	}

	public void actionPerformed(ActionEvent e) {

		dialog.setVisible(true);

	}

	public void buildThesauri(final String inputPath, final String outputPath) {

		if (!validatePaths(inputPath, outputPath)) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					dialog.setVisible(true);
				}
			});
			return;
		}

		final ProgressDialog progressDialog = new ProgressDialog(parent);

		new SwingWorker() {
			public Object construct() {
				ProgressDialog.showDialog(progressDialog);
				try {
					ThesaurusParser tparser = new ThesaurusParser(inputPath);
					List<SynSet> synsets;
					synsets = tparser.parse();
					Thesaurus thesauri = ThesaurusFactory.getThesaurus(synsets,
							tparser.getIndex());
					ThesaurusFactory.saveThesaurus(thesauri, outputPath);
				} catch (Exception e) {
					SwingUtils.showExceptionDialog(parent,
							"Exception while creating thesauri", e);
					return Boolean.FALSE;
				} finally {
					ProgressDialog.hideDialog(progressDialog);
				}
				return Boolean.TRUE;
			}

			public void finished() {
				if ((Boolean) this.get())
					SwingUtils.showInformationDialog(parent,
							"Thesaurus succesfully created");
			}
		}.start();

	}

	private boolean validatePaths(String inputPath, String outputPath) {

		if ((inputPath == null) || inputPath.equals("") || outputPath == null
				|| outputPath.equals("")) {
			SwingUtils.showExceptionDialog(parent, "Incorrect data", null);
			return false;
		}
		return true;

	}

}
