package org.grejpfrut.lakon.gui.methods;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.JTextComponent;

/**
 * 
 * @author Adam Dudczak
 */
public abstract class MethodBase {


	protected final JTextArea inputTextArea;

	protected final JTextPane resultsTextPane = new JTextPane();
	
	protected Frame parent;
	
	public MethodBase(JTextArea input, Frame parent) {
		this.resultsTextPane.setContentType("text/html");
		this.inputTextArea = input;
		this.parent = parent;
	}

	public abstract void execute();

	public abstract JComponent getSettingsPanel();

	
	public Component getResultPanel() {
		return this.getPanel(this.resultsTextPane);
	}
	
	
	protected JPanel getPanel(JTextComponent textarea) {

		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(textarea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scrollPane);
		return panel;

	}

	public void setInput(String input) {
		synchronized (this) {
			this.inputTextArea.setText(input);
		}
	}

	protected String getInput() {
		return this.inputTextArea.getText();
	}

	protected void setResult(String result) {
		this.resultsTextPane.setText(result);
	}
	
	public void clearResults(){
		this.resultsTextPane.setText("");
		
	}

}
