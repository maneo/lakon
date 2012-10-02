package org.grejpfrut.lakon.gui.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Properties;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import org.grejpfrut.lakon.gui.components.ThesaurusDemoDialog;
import org.grejpfrut.tiller.utils.TillerConfiguration;
import org.grejpfrut.tiller.utils.thesaurus.ThesaurusFactory;

/**
 * 
 * @author Adam Dudczak
 */
public class RunThesauriDemo extends AbstractAction {

	private JFrame parent;

	private TillerConfiguration conf;

	private final static FileFilter thesauriFileFilter = new FileFilter() {

		@Override
		public boolean accept(File f) {
			return f.toString().endsWith(ThesaurusFactory.THESAURI_FILE_NAME);
		}

		@Override
		public String getDescription() {
			return "Thesauri in file called "
					+ ThesaurusFactory.THESAURI_FILE_NAME;
		}

	};

	public RunThesauriDemo(JFrame frame, Properties conf) {

		this.parent = frame;
		this.conf = new TillerConfiguration(conf);

	}

	public void actionPerformed(ActionEvent e) {

		if (this.conf.getPathToThesaurus() == null) {

			Preferences prefs = Preferences.systemNodeForPackage(this
					.getClass());
			JFileChooser chooser = new JFileChooser(new File(prefs.get(
					FileOpenAction.WORKING_DIR, "/")));

			chooser.setFileFilter(thesauriFileFilter);
			int result = chooser.showOpenDialog(parent);

			if (result == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				this.conf.setPathToThesaurus(file.toString());
			}
		}

		final ThesaurusDemoDialog demo = new ThesaurusDemoDialog(parent,
				this.conf.getPathToThesaurus());
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				demo.setVisible(true);
			}
		});

	}

}
