package org.grejpfrut.lakon.summarizer.methods;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import org.grejpfrut.lakon.summarizer.Summarizer;
import org.grejpfrut.lakon.summarizer.SummarizerBase;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Sentence;

/**
 * This implementation of {@link Summarizer} can be used as a reference when
 * comparing effieciency with other implementation.
 * 
 * @author Adam Dudczak
 */
public class RandomSummarizer extends SummarizerBase {

	public RandomSummarizer(Settings conf) {
		super(conf);
	}

	@Override
	protected List<Sentence> prepareSummary(Article article) {

		final Random rand = new Random();
		List<Sentence> sents = article.getSentences();
		SortedSet<Integer> result = new TreeSet<Integer>();
		int slength = this.length;
		while (slength > 0) {
			int sentIndex = rand.nextInt(sents.size());
            if ( !result.contains(sentIndex) ) {
            	result.add(sentIndex);
            	slength--;
            }
		}
		final List<Sentence> resSentences = new ArrayList<Sentence>();
		for ( Integer sIndex : result ){
			resSentences.add(sents.get(sIndex));
		}
		return resSentences;
	}

}
