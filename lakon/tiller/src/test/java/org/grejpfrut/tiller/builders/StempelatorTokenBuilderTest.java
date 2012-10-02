package org.grejpfrut.tiller.builders;

import java.util.List;

import org.grejpfrut.tiller.analysis.StempelatorTokenizer;
import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.entities.Token.PartOfSpeech;

/**
 * This is a test of {@link StempelatorTokenizer}.
 * 
 * @author Adam Dudczak
 * 
 */
public class StempelatorTokenBuilderTest extends StopWordTokenBuilderTest {

	private final static String testStemmmer = "Człowiek znikąd, wystawia w Wiedniu zdumiewające "
			+ "spektakle, które wzbudzają sensację i plotki o jego nadprzyrodzonych zdolnościach.";

	private final static String testStemmmer2 = "Jednocześnie konwencja \"zaleca dalszy aktywny udział "
			+ "Samoobrony w koalicji rządowej i rekomenduje przewodniczącemu podejmowanie decyzji, zgodnie z "
			+ "jego oceną sytuacji, nie wykluczając najbardziej radykalnych politycznych decyzji\".";

	protected void setUp() throws Exception {
		super.setUp();
		super.config.setTokenizerClassName("org.grejpfrut.tiller.analysis.StempelatorTokenizer");
	}

	public void testStemmer() {

		TokenBuilder factory = new TokenBuilder(super.config);
		List<Token> tokens = factory.getTokens(testStemmmer);

		assertEquals(16, tokens.size());

		assertEquals("Człowiek", tokens.get(0).getText());
		assertEquals("człowiek", tokens.get(0).getBaseForms().get(0));
		assertFalse(tokens.get(0).isStopWord());
		assertEquals(PartOfSpeech.UNKNOWN, tokens.get(0).getInfo().get(0));

		assertEquals("znikąd,", tokens.get(1).getText());
		assertEquals("znikąd", tokens.get(1).getBaseForms().get(0));
		assertFalse(tokens.get(1).isStopWord());

		assertEquals("wystawia", tokens.get(2).getText());
		assertEquals("wystawiać", tokens.get(2).getBaseForms().get(0));

		assertEquals("Wiedniu", tokens.get(4).getText());
		assertEquals("wiedniu", tokens.get(4).getBaseForms().get(0));

	}

	public void testStemmer2() {

		TokenBuilder factory = new TokenBuilder(super.config);
		List<Token> tokens = factory.getTokens(testStemmmer2);

		assertEquals(26, tokens.size());

		assertEquals("konwencja", tokens.get(1).getText());
		assertEquals("konwencja", tokens.get(1).getBaseForms().get(0));

		assertEquals("\"zaleca", tokens.get(2).getText());
		assertEquals("zalecać", tokens.get(2).getBaseForms().get(0));

		assertEquals("decyzji\".", tokens.get(tokens.size() - 1).getText());
		assertEquals("decyzja", tokens.get(tokens.size() - 1).getBaseForms().get(0));

		assertEquals("Jednocześnie", tokens.get(0).getText());
		assertEquals("jednoczesny", tokens.get(0).getBaseForms().get(0));

	}

}
