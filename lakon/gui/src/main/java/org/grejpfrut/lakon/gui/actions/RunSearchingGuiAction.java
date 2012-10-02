package org.grejpfrut.lakon.gui.actions;

import java.awt.event.ActionEvent;
import java.util.Properties;

import javax.swing.AbstractAction;

import org.grejpfrut.wiki.searcher.gui.SearchingGui;

/**
 * Runs SearchingDemo from wiki subproject.
 * @author Adam Dudczak
 */
public class RunSearchingGuiAction extends AbstractAction {

	private Properties conf;

	public RunSearchingGuiAction(Properties conf) {
		this.conf = conf;
	}

	public void actionPerformed(ActionEvent e) {

		SearchingGui demo = new SearchingGui(prepareProperties(), true);
		demo.display();

	}

	private Properties prepareProperties() {

		Properties copy = new Properties();
		copy.putAll(conf);

		String index = copy.getProperty("ref.index.dir");
		copy.put("lucene.index.dir", index);
		return copy;

	}

}
