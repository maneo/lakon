package org.grejpfrut.tiller.builders;

import java.util.List;

import junit.framework.TestCase;

import org.grejpfrut.tiller.analysis.StopWordsTokenizer;
import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.entities.Token.PartOfSpeech;
import org.grejpfrut.tiller.utils.TillerConfiguration;

/**
 * This is a test of {@link TokenBuilder} with default
 * {@link StopWordsTokenizer}.
 * 
 * @author ad
 * 
 */
public class StopWordTokenBuilderTest extends TestCase {

	private static final String STOP_WORDS_LIST = "to,o,z,bez,albo,na,lub,i,a";

	private static final String test1 = "Ten tekst z kilkoma na, lub, i pod za to bez albo i o";

	protected TillerConfiguration config = new TillerConfiguration(null);

	protected void setUp() throws Exception {
		config.setStopWords(STOP_WORDS_LIST);
	}

	public void testStopWords() {

		TokenBuilder factory = new TokenBuilder(this.config);
		List<Token> tokens = factory.getTokens(test1);

		assertEquals("ten", tokens.get(0).getBaseForms().get(0));
		assertEquals("Ten", tokens.get(0).getText());
		assertFalse(tokens.get(0).isStopWord());

		assertEquals("z", tokens.get(2).getBaseForms().get(0));
		assertTrue(tokens.get(2).isStopWord());

		assertEquals("o", tokens.get(tokens.size() - 1).getBaseForms().get(0));

		assertEquals(14, tokens.size());

		assertEquals(PartOfSpeech.UNKNOWN, tokens.get(0).getInfo().get(0));

	}

}
