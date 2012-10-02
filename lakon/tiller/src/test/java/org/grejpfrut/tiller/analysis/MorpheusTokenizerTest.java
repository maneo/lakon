package org.grejpfrut.tiller.analysis;

import junit.framework.TestCase;

import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.entities.Token.PartOfSpeech;
import org.grejpfrut.tiller.utils.TillerConfiguration;

/**
 * Morpheus toknizer test.
 * 
 * @author Adam Dudczak
 * 
 */
public class MorpheusTokenizerTest extends TestCase {

	private static final String STOP_WORDS_LIST = "to,o,z,bez,albo,na,lub,i,a";

	MorpheusTokenizer tokenizer;

	protected void setUp() throws Exception {

		TillerConfiguration config = new TillerConfiguration(null);
		config.setStopWords(STOP_WORDS_LIST);
		this.tokenizer = new MorpheusTokenizer(config);

	}

	public void testStemming() {

		Token token = this.tokenizer.getToken("Tymczasem");
		assertEquals("Tymczasem", token.getText());
		assertEquals("tymczasem", token.getBaseForms().get(0));
		assertEquals(PartOfSpeech.UNKNOWN, token.getInfo().get(0));

		token = this.tokenizer.getToken(":przesłaniający");
		assertEquals(":przesłaniający", token.getText());
		assertEquals("przesłaniać", token.getBaseForms().get(0));
		assertEquals(PartOfSpeech.VERB, token.getInfo().get(0));

		token = this.tokenizer.getToken("Dudczak");
		assertEquals("Dudczak", token.getText());
		assertEquals("dudczak", token.getBaseForms().get(0));
		assertEquals(PartOfSpeech.UNKNOWN, token.getInfo().get(0));

		token = this.tokenizer.getToken("turystyczną");
		assertEquals("turystyczną", token.getText());
		assertEquals("turystyczny", token.getBaseForms().get(0));
		assertEquals(PartOfSpeech.ADJECTIVE, token.getInfo().get(0));

		token = this.tokenizer.getToken("60 minut,");
		assertEquals("60 minut,", token.getText());

		token = this.tokenizer.getToken("to.");
		assertEquals("to.", token.getText());
		assertEquals("to", token.getBaseForms().get(0));
		assertTrue(token.isStopWord());

		token = this.tokenizer.getToken("rzeka");
		assertEquals("rzeka", token.getText());
		assertEquals("rzeka", token.getBaseForms().get(0));
		assertFalse(token.isStopWord());
		assertEquals(PartOfSpeech.NOUN, token.getInfo().get(0));

		token = this.tokenizer.getToken("dom");
		assertEquals("dom", token.getText());
		assertEquals("dom", token.getBaseForms().get(0));
		assertFalse(token.isStopWord());
		assertEquals(PartOfSpeech.NOUN, token.getInfo().get(0));

	}

}
