package org.grejpfrut.wiki.searcher.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

/**
 * Use this icon to give JTabbedPanel tabs ability to close.
 * @author Tim Ryan, Adam Dudczak
 *
 */
public class CloseTabIcon extends ImageIcon {

	private final ImageIcon icon;

	private JTabbedPane tabbedPane = null;

	private Rectangle position = null;

	/**
	 * Creates a new instance of CloseTabIcon.
	 */
	public CloseTabIcon() {
		this.icon = new ImageIcon(CloseTabIcon.class
				.getResource("/images/close.gif"));
	}

	/**
	 * when painting, remember last position painted so we can see if the user
	 * clicked on the icon.
	 */
	public void paintIcon(Component component, Graphics g, int x, int y) {

		if (tabbedPane == null) {
			tabbedPane = (JTabbedPane) component;

			tabbedPane.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
					if (!e.isConsumed()
							&& position.contains(e.getX(), e.getY())) {
						
						tabbedPane.remove(tabbedPane.getSelectedComponent());
						tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
						e.consume();
					}
				}
			});
		}

		position = new Rectangle(x, y, getIconWidth(), getIconHeight());
		icon.paintIcon(component, g, x, y);
	}

	public int getIconWidth() {
		return icon.getIconWidth();
	}

	public int getIconHeight() {
		return icon.getIconHeight();
	}

}
