package org.grejpfrut.lakon.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.carrot2.demo.swing.SwingUtils;
import org.grejpfrut.lakon.gui.actions.BuildThesauriAction;
import org.grejpfrut.lakon.gui.actions.FileOpenAction;
import org.grejpfrut.lakon.gui.utils.ThresholdHelper;
import org.grejpfrut.wiki.process.SearchingSettings;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * 
 * @author Adam Dudczak
 */
public class ThesaurusBuildingDialog extends JDialog {

	private final Frame parent;

	private final BuildThesauriAction buildAction;

	private final JTextField pathToThesauri = new JTextField();

	private final JTextField outputPath = new JTextField();

	private final JButton okButton = new JButton();

	private final JButton cancelButton = new JButton();

	public ThesaurusBuildingDialog(Frame parent, BuildThesauriAction action) {
		super(parent, true);
		this.parent = parent;
		this.buildAction = action;

		this.setTitle("Thesaurus dialog");

		ActionListener hider = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				hideDialog();
				buildAction.buildThesauri(pathToThesauri.getText(), outputPath
						.getText());
			}

		};

		okButton.setText("Ok");
		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				hideDialog();
				buildAction.buildThesauri(pathToThesauri.getText(), outputPath
						.getText());
			}

		});

		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				hideDialog();
			}

		});

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(getPanel(), BorderLayout.CENTER);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		final Dimension position = Toolkit.getDefaultToolkit().getScreenSize();

		this.setPreferredSize(new Dimension(400, 150));

		this.setLocation((position.width - 400) / 2,
				(position.height - 150) / 2);

		this.pack();

	}

	private JPanel getPanel() {

		JButton inputChooser = new JButton("Choose..");
		inputChooser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				getFile(pathToThesauri, false);
			}
		});

		JButton outputChooser = new JButton("Choose..");
		outputChooser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				getFile(outputPath, true);
			}

		});

		FormLayout layout = new FormLayout(
				"pref, 4dlu, 35dlu, 2dlu, 35dlu, 2dlu, 35dlu, 2dlu, 35dlu",
				"p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p");

		layout.setRowGroups(new int[][] { { 1, 3, 5, 7, 9, 11, 13, 15, 17 } });

		JPanel panel = new JPanel(layout);
		panel.setBorder(Borders.DIALOG_BORDER);
		CellConstraints cc = new CellConstraints();

		panel.add(new JLabel("Path to thesauri:"), cc.xy(1, 1));
		panel.add(pathToThesauri, cc.xyw(3, 1, 5));
		panel.add(inputChooser, cc.xy(9, 1));

		panel.add(new JLabel("Output path:"), cc.xy(1, 3));
		panel.add(outputPath, cc.xyw(3, 3, 5));
		panel.add(outputChooser, cc.xy(9, 3));

		panel.add(okButton, cc.xyw(3, 5, 5));
		panel.add(cancelButton, cc.xy(9, 5));
		return panel;

	}

	public void hideDialog() {
		this.setVisible(false);
	}

	public String getThesauriPath() {
		return this.pathToThesauri.getText();
	}

	public String getOutputPath() {
		return this.outputPath.getText();
	}

	private void getFile(JTextField target, boolean onlyDirs) {

		Preferences prefs = Preferences.systemNodeForPackage(this.getClass());
		JFileChooser chooser = new JFileChooser(new File(prefs.get(
				FileOpenAction.WORKING_DIR, "/")));
		if (onlyDirs)
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int result = chooser.showOpenDialog(parent);

		if (result == JFileChooser.CANCEL_OPTION)
			return;

		final File file = chooser.getSelectedFile();
		prefs.put(FileOpenAction.WORKING_DIR, file.getAbsolutePath());
		target.setText(file.toString());

	}

}
