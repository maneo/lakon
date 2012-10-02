package org.grejpfrut.tiller.builders;

import java.util.List;

import junit.framework.TestCase;

import org.grejpfrut.tiller.entities.Sentence;
import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.utils.TillerConfiguration;

public class SentenceBuilderTest extends TestCase {

	private final static String ABBRS_LIST = "itd,itp,np,etc,r";

	private final static String TEST_ONE = "Wi\u0119c nigdy nie wiadomo \u017ce to itp. nie jest. Jest to do\u015b\u0107 proste np. we\u017a itd. b\u0119dzie git.";

	private final static String TEST_STOPWORDS = "Wi\u0119c nigdy nie wiadomo \u017ce to itp. nie jest. Jest to do\u015b\u0107 proste np. we\u017a itd.";

	private final static String TEST_SEMICOLON = "Wi\u0119c nigdy nie wiadomo \u017ce to itp. nie jest;";

	private final static String TEST_SENTENCE_ENDINGS = "Wi\u0119c nigdy nie wiadomo \u017ce to itp. nie jest... Fabryka zdan to jest to? Zdania mog\u0105 si\u0119 r\u00f3\u017cnie ko\u0144czy\u0107!";

	private final static String TEST_COLON = "Demokracja zosta\u0142a przywr\u00f3cona w 1985 r., ale zbrodni nie \u015bcigano. Amnesti\u0119 dla wojskowych og\u0142osi\u0142 pierwszy po 1985 r. demokratyczny prezydent Julio Maria Sanguinetti, a nar\u00f3d popar\u0142 j\u0105 w referendum.";

	private final static String TEST_NUMBERS_AND_ONE_LETTER = "Wszystko to wina 18. brygady kawalerii dowodzonej przez Adama D. - znanego obiboka. Nikt nie wie jak to naprawde jest.";

	private final static String TEST_SPECIAL_WITH_ABBRS = "Wszystko to (np. bezrobocie) itp., wina  spowodowane 18. brygady kawalerii dowodzonej przez Adama D. - znanego obiboka. Nikt nie wie jak to naprawde jest.";

	private final static String TEST_INCORRECT_PUNCTATION = "Wszystko \"to\" (np. bezrobocie) itp. , wina ,  spowodowane 18. brygady kawalerii dowodzonej przez Adama D. - znanego obiboka . Nikt nie wie jak to naprawde jest .";

	private TillerConfiguration config = new TillerConfiguration(null);

	protected void setUp() throws Exception {
		config.setAbbreviationsList(ABBRS_LIST);
	}

	public void testSentences() {

		SentenceBuilder factory = new SentenceBuilder(config);
		List<Sentence> sents = factory.getSentences(TEST_ONE);

		assertEquals(2, sents.size());
		assertEquals("Wi\u0119c nigdy nie wiadomo \u017ce to itp. nie jest.",
				sents.get(0).getText());
		assertEquals("wi\u0119c nigdy nie wiadomo \u017ce to itp nie jest",
				sents.get(0).getStemmedText());
	}

	public void testWithStopWords() {

		config.setStopWords("nie,\u017ce,to");
		SentenceBuilder factory = new SentenceBuilder(config);
		List<Sentence> sents = factory.getSentences(TEST_STOPWORDS);

		assertEquals(2, sents.size());
		assertEquals("Wi\u0119c nigdy nie wiadomo \u017ce to itp. nie jest.",
				sents.get(0).getText());
		assertEquals("wi\u0119c nigdy wiadomo itp jest", sents.get(0)
				.getStemmedText());

		List<Token> tokens = sents.get(0).getTokens();
		assertTrue(tokens.get(2).isStopWord());
		assertTrue(tokens.get(4).isStopWord());
		assertTrue(tokens.get(5).isStopWord());

		assertFalse(tokens.get(1).isStopWord());
	}

	/**
	 * Tests sentences ended with semicolon.
	 */
	public void testSemicolon() {
		SentenceBuilder factory = new SentenceBuilder(config);
		List<Sentence> sents = factory.getSentences(TEST_SEMICOLON);

		assertEquals(1, sents.size());
		assertEquals("Wi\u0119c nigdy nie wiadomo \u017ce to itp. nie jest;",
				sents.get(0).getText());
	}

	/**
	 * Test sentences ended with ldots.
	 */
	public void testEndings() {

		SentenceBuilder factory = new SentenceBuilder(config);
		List<Sentence> sents = factory.getSentences(TEST_SENTENCE_ENDINGS);

		assertEquals(3, sents.size());
		assertEquals("Wi\u0119c nigdy nie wiadomo \u017ce to itp. nie jest...",
				sents.get(0).getText());
		assertEquals("Fabryka zdan to jest to?", sents.get(1).getText());
		assertEquals(
				"Zdania mog\u0105 si\u0119 r\u00f3\u017cnie ko\u0144czy\u0107!",
				sents.get(2).getText());

		List<Token> tokens = sents.get(0).getTokens();
		assertEquals("jest", tokens.get(8).getText());

		tokens = sents.get(1).getTokens();
		assertEquals("to", tokens.get(tokens.size() - 1).getText());

		tokens = sents.get(2).getTokens();
		assertEquals("ko\u0144czy\u0107", tokens.get(tokens.size() - 1)
				.getText());

	}

	public void testColon() {
		SentenceBuilder factory = new SentenceBuilder(config);
		List<Sentence> sents = factory.getSentences(TEST_COLON);

		assertEquals(2, sents.size());
	}

	public void testOneLetterAndNumbers() {
		SentenceBuilder factory = new SentenceBuilder(config);
		List<Sentence> sents = factory
				.getSentences(TEST_NUMBERS_AND_ONE_LETTER);
		assertEquals(2, sents.size());
		assertEquals(
				"Wszystko to wina 18. brygady kawalerii dowodzonej przez Adama D. - znanego obiboka.",
				sents.get(0).getText());
	}
	
	public void testSpecialsAndAbbrs() {
		
		SentenceBuilder factory = new SentenceBuilder(config);
		List<Sentence> sents = factory
				.getSentences(TEST_SPECIAL_WITH_ABBRS);
		assertEquals(2, sents.size());
		assertEquals(
				"Wszystko to (np. bezrobocie) itp., wina spowodowane 18. brygady kawalerii dowodzonej przez Adama D. - znanego obiboka.",
				sents.get(0).getText());
	}
	
	public void testIncorrectPunctation() {
		
		SentenceBuilder factory = new SentenceBuilder(config);
		List<Sentence> sents = factory
				.getSentences(TEST_INCORRECT_PUNCTATION);
		assertEquals(2, sents.size());
		assertEquals(
				"Wszystko \"to\" (np. bezrobocie) itp., wina, spowodowane 18. brygady kawalerii dowodzonej przez Adama D. - znanego obiboka.",
				sents.get(0).getText());
		assertEquals(
					"Nikt nie wie jak to naprawde jest.",
					sents.get(1).getText());
	}

}
