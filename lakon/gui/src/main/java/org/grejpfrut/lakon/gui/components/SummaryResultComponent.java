package org.grejpfrut.lakon.gui.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.grejpfrut.tiller.builders.ArticleBuilder;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Paragraph;
import org.grejpfrut.tiller.entities.Sentence;
import org.grejpfrut.tiller.utils.TillerConfiguration;

/**
 * 
 * @author Adam Dudczak
 */
public class SummaryResultComponent extends JPanel {

	private JCheckBox onlySummary;

	private JTextPane textPane;

	private Article art;

	private List<Sentence> sentences;

	private static ArticleBuilder artBuilder = new ArticleBuilder(
			new TillerConfiguration());

	public SummaryResultComponent() {

		buildGui();

	}

	private void buildGui() {

		setLayout(new BorderLayout());
		textPane = new JTextPane();
		textPane.setContentType("text/html");
		textPane.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textPane,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.add(scrollPane, BorderLayout.CENTER);

		onlySummary = new JCheckBox("show only summary");
		this.add(onlySummary, BorderLayout.SOUTH);

		onlySummary.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				updateResultPane(sentences);
			}

		});
	}

	public void setArticle(String text) {
		this.art = artBuilder.getArcticle(text);
	}

	public void setSummary(String text) {

		if (this.art != null) {
			this.sentences = artBuilder.getArcticle(text).getSentences();
			updateResultPane(this.sentences);
		}

	}

	private void updateResultPane(List<Sentence> sents) {

		if ( sents == null ) return;
		StringBuffer sb = new StringBuffer();
		if (onlySummary.isSelected()) {

			for (Sentence sent : sentences) {
				sb.append(sent.getText() + " ");
			}
			textPane.setText(sb.toString());
		} else {

			for (Paragraph para : art.getParagraphs()) {
				sb.append("<p>");
				for (Sentence sentence : para.getSentences()) {
					boolean isIn = sentences.contains(sentence);
					if (isIn)
						sb.append("<b>");
					sb.append(sentence.getText()+" ");
					if (isIn)
						sb.append("</b>");
				}
				sb.append("</p>");
			}
			textPane.setText(sb.toString());
		}

	}

}
