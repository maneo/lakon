package org.grejpfrut.lakon.gui.actions;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JSplitPane;

import com.jgoodies.uif_lite.panel.SimpleInternalFrame;

/**
 *
 * @author Adam Dudczak
 */
public class HideSettingsAction extends AbstractAction {
	
	private Component component;
	private JSplitPane split;

	public HideSettingsAction(SimpleInternalFrame settingsPanel, JSplitPane split) {
		this.component = settingsPanel;
		this.split = split;
		
	}

	public void setComponent(Component component){
		this.component = component;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		boolean isVisible = this.component.isVisible();
		this.component.setVisible(!isVisible);
		int location = (int) Math.round(Toolkit.getDefaultToolkit()
				.getScreenSize().getHeight() / 4);
		this.split.setDividerLocation(location); 
		
	}

}
