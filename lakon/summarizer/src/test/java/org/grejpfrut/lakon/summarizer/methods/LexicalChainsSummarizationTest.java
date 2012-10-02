package org.grejpfrut.lakon.summarizer.methods;

import java.util.List;

import org.grejpfrut.lakon.summarizer.Summarizer;
import org.grejpfrut.lakon.summarizer.methods.lc.AbstractThesauriTestCase;
import org.grejpfrut.lakon.summarizer.settings.LexicalChainsSettings;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.tiller.entities.Sentence;
import org.grejpfrut.tiller.utils.thesaurus.Thesaurus;

/**
 *
 * @author Adam Dudczak
 */
public class LexicalChainsSummarizationTest extends SummarizerTest {

	protected final static String INTERPRETATION_TESAURUS_KEY = "test.interpretation.thesaurus";

	protected Thesaurus thesauri;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.settings = new LexicalChainsSettings();
		
		String thes = super.testData.getProperty(INTERPRETATION_TESAURUS_KEY);
		this.thesauri = AbstractThesauriTestCase.getTestThesauri(thes);
		
	}
	@Override
	protected Summarizer getSummarizer() {
		return new LexicalChainsSummarizer(this.settings, this.thesauri); 
	}
		
	@Override
	public void test2Sentences() {
		
		settings.put(Settings.P_SUMMARY_LENGTH, 2);
		final String text = testData.getProperty("test.lexicalchain");
		Summarizer summarizer = this.getSummarizer();
		List<Sentence> sents = summarizer.summarize(text);
		assertEquals(2, sents.size());
		assertEquals(testData.getProperty("test.lexicalchain.result.1"), sents.get(0).getText());
		assertEquals(testData.getProperty("test.lexicalchain.result.2"), sents.get(1).getText());
		
	}
	
	public void test3Sentences() {
		
		settings.put(Settings.P_SUMMARY_LENGTH, 3);
		final String text = testData.getProperty("test.lexicalchain");
		Summarizer summarizer = this.getSummarizer();
		List<Sentence> sents = summarizer.summarize(text);
		assertEquals(3, sents.size());
		assertEquals(testData.getProperty("test.lexicalchain.result.1"), sents.get(0).getText());
		assertEquals(testData.getProperty("test.lexicalchain.result.2"), sents.get(1).getText());
		assertEquals(testData.getProperty("test.lexicalchain.result.3"), sents.get(2).getText());
		
	}
	


}
