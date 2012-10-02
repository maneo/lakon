package org.grejpfrut.lakon.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
/**
 * Simple compnent displaying infinite progres bar.
 * @author Adam Dudczak
 */
public class ProgressDialog extends JDialog {

	private final JProgressBar progress = new JProgressBar();
	
	private final Frame parent;

	public ProgressDialog(Frame parent) {
		super(parent, true);
		
		this.parent = parent;
		
		JPanel panel = new JPanel();
		this.progress.setIndeterminate(true);
		panel.add(progress);
		this.setTitle("Please wait...");
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panel, BorderLayout.CENTER);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		final Dimension position = Toolkit.getDefaultToolkit().getScreenSize();

		this.setLocation((position.width - 200) / 2,
                (position.height - 50) / 2);
		
		this.setPreferredSize(new Dimension(200,50));
		
		this.pack();
		
	}
	
	
	public static void hideDialog(final ProgressDialog dialog){
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				dialog.setVisible(false);
			}
		});
		
	}
	
	public static void showDialog(final ProgressDialog dialog){
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				dialog.setVisible(true);
			}
		});
		
	}

	
	
}
