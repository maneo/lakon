package org.grejpfrut.lakon.gui.methods;

import java.awt.Frame;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.grejpfrut.tiller.demo.TillerDemo;
import org.grejpfrut.tiller.utils.TillerConfiguration;

/**
 *
 * @author Adam Dudczak
 */
public class TillerDemoMethod extends MethodBase {

	protected TillerConfiguration conf;
	
	public TillerDemoMethod(TillerConfiguration conf, JTextArea area, Frame parent) {
		super(area, parent);
		this.conf = conf; 
	}

	public void execute() {

		TillerDemo demo = new TillerDemo(conf);
		String input = super.getInput();
		if ( input != null ){
			String result = demo.demo(input, true);
			super.setResult(result);
		}
		
	}

	@Override
	public JComponent getSettingsPanel() {
		JPanel panel = new JPanel();
		return panel;
	}



}
