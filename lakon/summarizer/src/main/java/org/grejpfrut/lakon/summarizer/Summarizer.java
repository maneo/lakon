package org.grejpfrut.lakon.summarizer;

import java.util.List;

import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Sentence;

/**
 * Basic interface for all summarizers.
 * @author Adam Dudczak
 */
public interface Summarizer {

	/**
	 * @param text -
	 *            text for summarization.
	 * @return summary as a list of {@link Sentence}s object.
	 */
	public List<Sentence> summarize(String text);
	
	/**
	 * 
	 * @param article
	 * @return
	 */
	public List<Sentence> summarize(Article article);
	
	/**
	 * @return Gets unique indentifier of this method
	 */
	public String name();

}
