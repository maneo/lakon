package org.grejpfrut.lakon.summarizer.methods;

import java.util.List;

import org.grejpfrut.lakon.summarizer.AbstractDataTestCase;
import org.grejpfrut.lakon.summarizer.Summarizer;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.tiller.builders.ArticleBuilder;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Sentence;
import org.grejpfrut.tiller.utils.TillerConfiguration;

/**
 * Scafold for testing {@link Summarizer} implementation.
 * 
 * @author Adam Dudczak
 */
public abstract class SummarizerTest extends AbstractDataTestCase {

	protected static final String TEST_SUMMARY_LENGTH = "test.random.summarizer";

	protected ArticleBuilder articleBuilder;

	protected Summarizer summarizer;

	protected Settings settings;

	protected void setUp() throws Exception {
		super.setUp();
		this.settings = new Settings();
	}

	protected abstract Summarizer getSummarizer();

	public void test2Sentences() {
		testLength(2, super.testData.getProperty(TEST_SUMMARY_LENGTH));
	}

	public void test0Sentences() {
		testLength(0, super.testData.getProperty(TEST_SUMMARY_LENGTH));
	}

	public void testMaxSentences() {
		final String text = super.testData.getProperty(TEST_SUMMARY_LENGTH);
		testLength(this.getSize(text), text);
	}

	public void testMoreSentences() {
		final String text = super.testData.getProperty(TEST_SUMMARY_LENGTH);
		final int length = this.getSize(text);
		settings.put(Settings.P_SUMMARY_LENGTH, length + 1);
		this.summarizer = getSummarizer();
		List<Sentence> result = this.summarizer.summarize(text);
		assertEquals(length, result.size());
	}

	protected void testLength(int length, String text) {
		settings.put(Settings.P_SUMMARY_LENGTH, length);
		this.summarizer = getSummarizer();
		List<Sentence> result = this.summarizer.summarize(text);
		assertEquals(length, result.size());
	}

	protected int getSize(String text) {
		ArticleBuilder articleBuilder = new ArticleBuilder(
				new TillerConfiguration());
		Article art = articleBuilder.getArcticle(text);
		return art.getSentences().size();
	}

}
