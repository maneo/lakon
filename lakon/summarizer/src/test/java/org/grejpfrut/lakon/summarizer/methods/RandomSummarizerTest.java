package org.grejpfrut.lakon.summarizer.methods;

import org.grejpfrut.lakon.summarizer.Summarizer;
import org.grejpfrut.tiller.entities.Article;

/**
 * Basic {@link RandomSummarizer} test.
 * 
 * @author Adam Dudczak
 */
public class RandomSummarizerTest extends SummarizerTest {

	private static final String TEST_SUMMARY_LENGTH = "test.random.summarizer";

	protected Article getArticle() {

		final String text = super.testData.getProperty(TEST_SUMMARY_LENGTH);
		return super.articleBuilder.getArcticle(text);

	}

	protected Summarizer getSummarizer() {
		return new RandomSummarizer(this.settings);
	}
}
