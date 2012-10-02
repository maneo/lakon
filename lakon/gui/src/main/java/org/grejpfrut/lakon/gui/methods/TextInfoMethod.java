package org.grejpfrut.lakon.gui.methods;

import java.awt.Frame;

import javax.swing.JTextArea;

import org.grejpfrut.tiller.demo.SentenceLength;
import org.grejpfrut.tiller.utils.TillerConfiguration;

/**
 *
 * @author Adam Dudczak
 */
public class TextInfoMethod extends TillerDemoMethod {

	public TextInfoMethod(TillerConfiguration conf, JTextArea area, Frame parent) {
		super(conf, area, parent);
	}

	public void execute() {
		
		SentenceLength info = new SentenceLength(conf);
		String input = super.getInput();
		if ( input != null ){
			String result = info.getSentenceLengthInfo(input,true);
			super.setResult(result);
		}
	}

}
