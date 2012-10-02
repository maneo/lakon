package org.grejpfrut.wiki.searcher.gui.actions;

import java.awt.Component;

/**
 * Used to change parent component in Actions.
 * @author Adam Dudczak
 *
 */
public interface ChangeParentAction {
	
	/**
	 * Sets new parent.
	 * @param parent
	 */
	void setParent(Component parent);

}
