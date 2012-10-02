package org.grejpfrut.tiller.builders;

import java.util.List;

import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Paragraph;
import org.grejpfrut.tiller.entities.impl.ArticleImpl;
import org.grejpfrut.tiller.utils.TillerConfiguration;

/**
 * Simple factory to produce an {@link Article} out of raw text.
 * 
 * @author Adam Dudczak
 * 
 */
public class ArticleBuilder {

	/**
	 * {@link Article} is build up of {@link Paragraph}s.
	 */
	private final ParagraphBuilder paragraphBuilder;

	/**
	 * Inits {@link ArticleBuilder#paragraphBuilder}.
	 * 
	 * @param conf -
	 *            {@link TillerConfiguration}
	 */
	public ArticleBuilder(TillerConfiguration conf) {
		this.paragraphBuilder = new ParagraphBuilder(conf);
	}

	/**
	 * Gets {@link Article} out of raw text.
	 * 
	 * @param text -
	 *            input text.
	 * @return An {@link Article} object.
	 */
	public Article getArcticle(String text) {

		List<Paragraph> paras = this.paragraphBuilder.getParagraphs(text);
		Article art = new ArticleImpl(paras);
		return art;

	}

	/**
	 * Gets {@link Article} out of raw text.
	 * 
	 * @param text -
	 *            input text.
	 * @param title -
	 *            title of the article.
	 * @return An {@link Article} object.
	 */
	public Article getArcticle(String text, String title) {

		List<Paragraph> paras = this.paragraphBuilder.getParagraphs(text);
		Article art = new ArticleImpl(paras, title);
		return art;

	}

}
