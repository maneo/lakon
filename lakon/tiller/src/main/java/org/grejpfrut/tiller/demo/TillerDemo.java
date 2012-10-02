package org.grejpfrut.tiller.demo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.grejpfrut.tiller.builders.ArticleBuilder;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Paragraph;
import org.grejpfrut.tiller.entities.Sentence;
import org.grejpfrut.tiller.utils.TillerConfiguration;

/**
 * 
 * @author Adam Dudczak
 */
public class TillerDemo {

	private final TillerConfiguration conf;

	public static void main(String args[]) {

		if (args.length != 1) {
			System.out.println("you must specify the path to file with text");
			return;
		}
		TillerDemo td = new TillerDemo(new TillerConfiguration());
		StringBuffer sb = new StringBuffer();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(args[0]), "UTF8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (Exception e) {
			System.out.println("Exception while getting content of file "
					+ args[0]);
		}
		System.out.println(td.demo(sb.toString(), false));

	}

	public TillerDemo(TillerConfiguration conf) {
		this.conf = conf;
	}

	public String demo(String text, boolean isHtml) {

		if (isHtml ) return demoHtml(text);
		
		String newline = System.getProperty("line.separator");
		ArticleBuilder ab = new ArticleBuilder(this.conf);
		Article art = ab.getArcticle(text);
		StringBuffer sb = new StringBuffer();
		sb.append("<article>" + newline);
		for (Paragraph p : art.getParagraphs()) {
			sb.append(" <paragraph>" + newline);
			for (Sentence s : p.getSentences()) {
				sb.append("  <sentence>" + s.getText() + "</sentence>"
						+ newline);
			}
			sb.append(" </paragraph>" + newline);
		}
		sb.append("</article>" + newline);
		return sb.toString();

	}

	private String demoHtml(String text) {
		
		String newline = System.getProperty("line.separator");
		ArticleBuilder ab = new ArticleBuilder(this.conf);
		Article art = ab.getArcticle(text);
		StringBuffer sb = new StringBuffer();
		int counter = 0;
		sb.append("<ol>" + newline);
		for (Paragraph p : art.getParagraphs()) {
			String title  = p.getTitle() != null ? p.getTitle() : "--";
			sb.append(" <li>"+  title  + "<ol>" + newline);
			for (Sentence s : p.getSentences()) {
				sb.append("  <li>" + s.getText() + "</li>" + newline);
			}
			sb.append(" </ol></li>" + newline);
		}
		sb.append("</ol>" + newline);
		return sb.toString();
	}

}
