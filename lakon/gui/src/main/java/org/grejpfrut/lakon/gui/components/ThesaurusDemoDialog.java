package org.grejpfrut.lakon.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.util.List;
import java.util.Locale;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import org.grejpfrut.tiller.utils.thesaurus.SynSet;
import org.grejpfrut.tiller.utils.thesaurus.Thesaurus;
import org.grejpfrut.tiller.utils.thesaurus.ThesaurusFactory;

import com.jgoodies.uif_lite.panel.SimpleInternalFrame;

/**
 * Simple component to show features of Lakon thesauri.
 * @author Adam Dudczak
 */
public class ThesaurusDemoDialog extends JDialog {

	private Thesaurus thesauri;

	private Frame parent;

	public ThesaurusDemoDialog(JFrame parent, String path) {
		super(parent, false);

		this.parent = parent;

		this.thesauri = ThesaurusFactory.getThesaurus(path);

		final JTextPane results = new JTextPane();
		results.setContentType("text/html");
		results.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(results,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		//SimpleInternalFrame sif = new SimpleInternalFrame("Results");
		//sif.add(scrollPane);

		
		final JTextField input = new JTextField();
		input.setText("<type here>");
		input.addCaretListener(new CaretListener() {

			public void caretUpdate(CaretEvent e) {

				String inputText = input.getText();
				if ((inputText != null) && (!"".equals(inputText))) {

					String toLower = inputText.toLowerCase(new Locale("pl"));
					List<SynSet> synsets = thesauri.getSynSets(toLower);
					StringBuffer sb = new StringBuffer();
					sb.append("<ul>");
					for (SynSet synset : synsets) {
						sb.append("<li> <b>" + synset.getId() + "</b> <ul>");
						sb.append("<li> <b> synonyms :</b> "
								+ synset.getSyns().toString() + "</li>");
						if (synset.getHypernyms().size() > 0)
							sb.append("<li> <b> hypernyms :</b> "
									+ synset.getHypernyms().toString()
									+ "</li>");
						if (synset.getHyponyms().size() > 0)
							sb.append("<li> <b> hyponyms :</b> "
											+ synset.getHyponyms().toString()
											+ "</li>");
						sb.append("</ul>");
					}
					sb.append("</ul>");
					results.setText(sb.toString());
				}

			}

		});


		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(scrollPane, BorderLayout.CENTER);
		this.getContentPane().add(input, BorderLayout.NORTH);

		final Dimension position = Toolkit.getDefaultToolkit().getScreenSize();

		this.setPreferredSize(new Dimension(400, 400));

		this.setLocation((position.width - 400) / 2,
				(position.height - 400) / 2);

		this.pack();

	}

}
