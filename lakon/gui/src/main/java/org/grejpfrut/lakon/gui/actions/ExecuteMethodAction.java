package org.grejpfrut.lakon.gui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.grejpfrut.lakon.gui.methods.MethodBase;

/**
 * 
 * @author Adam Dudczak
 */
public class ExecuteMethodAction extends AbstractAction {

	private MethodBase method;

	public ExecuteMethodAction(MethodBase method) {
		this.method = method;
	}

	public void actionPerformed(ActionEvent e) {

		method.execute();

	}



}
