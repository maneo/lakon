package org.grejpfrut.lakon.summarizer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.tiller.builders.ArticleBuilder;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Sentence;
import org.grejpfrut.tiller.utils.TillerConfiguration;

/**
 * Basic abstract class for all summarizers which defines summarizer functions.
 * 
 * @author Adam Dudczak
 */
public abstract class SummarizerBase implements Summarizer {
	
	protected final static Comparator<Double> DESC_COMPARATOR = new Comparator<Double>(){

		public int compare(Double o1, Double o2) {
			return -o1.compareTo(o2);
		}
		
	};

	protected Settings settings;

	protected int length = 0;

	protected Article art;

	/**
	 * @param settings
	 */
	public SummarizerBase(Settings settings) {
		this.settings = settings.createClone();

		this.length = (Integer) this.settings.get(Settings.P_SUMMARY_LENGTH);
	}

	/**
	 * This base method checks preconditions for all summary methods. Summary
	 * cannot be longer then original text, and must be longer then 0.
	 * @param text - text for summarization.
	 * @return summary as a list of {@link Sentence}s object.
	 */
	public List<Sentence> summarize(String text) {
		this.art = getArticle(text);
		final List<Sentence> sentences = art.getSentences();
		if (sentences.size() <= this.length)
			return sentences;
		else if (length <= 0)
			return new ArrayList<Sentence>();
		return prepareSummary(art);
	}
	
	/**
	 * See {@link SummarizerBase#summarize(String)}.
	 */
	public List<Sentence> summarize(Article art){
		final List<Sentence> sentences = art.getSentences();
		if (sentences.size() <= this.length)
			return sentences;
		else if (length <= 0)
			return new ArrayList<Sentence>();
		return prepareSummary(art);
	}

	/**
	 * Prepares summary from given article.
	 * 
	 * @param article
	 * @return summary.
	 */
	protected abstract List<Sentence> prepareSummary(Article article);

	/**
	 * Create {@link ArticleBuilder} and gets {@link Article}.
	 * 
	 * @param text -
	 *            text.
	 * @return {@link Article}.
	 */
	protected Article getArticle(String text) {
		ArticleBuilder ab = new ArticleBuilder(new TillerConfiguration());
		return ab.getArcticle(text);
	}
	
	public String name(){
		return this.getClass().getSimpleName();
	}

}
