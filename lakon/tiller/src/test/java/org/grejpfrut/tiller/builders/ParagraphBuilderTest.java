package org.grejpfrut.tiller.builders;

import java.util.List;

import junit.framework.TestCase;

import org.grejpfrut.tiller.entities.Paragraph;
import org.grejpfrut.tiller.utils.TillerConfiguration;

/**
 * Tests {@link ParagraphBuilder} class.
 * 
 * @author Adam Dudczak
 * 
 */
public class ParagraphBuilderTest extends TestCase {

	private TillerConfiguration config = new TillerConfiguration(null);

	private final static String twoParagraphsNL = " To jest pierwszy akapit. \n To jest już drugi.";

	private final static String twoParagraphsNL2 = " To jest pierwszy akapit. \r\n To jest już drugi.";

	private final static String titledParagraph = " To jest tytul. \n To jest zawartosc pierwszego akapitu. "
			+ "Niewiadomo co z tego wyjdzie.\n Tu jest drugi akapit ktory nie ma tytulu.";

	private final static String lastParagraph = "To jest pierwszy akapit dlugi naprawde. \n "
			+ "To z kolei jest srodkowy akapit. \n To ostatni.";

	private final static String additionalNewLineParagraph = "To jest pierwszy akapit dlugi naprawde. \n\n "
		+ "To z kolei jest srodkowy akapit. \n\n To ostatni.";
	
	

	
	protected void setUp() throws Exception {

		this.config
				.setTokenizerClassName("org.grejpfrut.tiller.analysis.StempelatorTokenizer");
	}

	private ParagraphBuilder getParagraphFactory() {
		return new ParagraphBuilder(this.config);
	}

	public void testTwoParagraphs() {

		ParagraphBuilder paraFactory = getParagraphFactory();
		List<Paragraph> paragraphs = paraFactory.getParagraphs(twoParagraphsNL);
		checkParagraphs(paragraphs);

		paragraphs = paraFactory.getParagraphs(twoParagraphsNL2);
		checkParagraphs(paragraphs);

	}

	private void checkParagraphs(List<Paragraph> paragraphs) {
		assertEquals(2, paragraphs.size());

		assertEquals("to jest pierwszy akapit", paragraphs.get(0)
				.getStemmedText());
		assertEquals("to jest już drug", paragraphs.get(1).getStemmedText());
	}

	public void testTitledParagraphs() {

		this.config.setMinimalParagraphLength(4);
		ParagraphBuilder paraFactory = getParagraphFactory();
		List<Paragraph> paras = paraFactory.getParagraphs(titledParagraph);

		assertEquals(2, paras.size());

		assertEquals("To jest tytul.", paras.get(0).getTitle());
		assertEquals("Tu jest drugi akapit ktory nie ma tytulu.", paras.get(1)
				.getText());

	}

	public void testLastParagraph() {

		this.config.setMinimalParagraphLength(4);
		ParagraphBuilder paraFactory = getParagraphFactory();
		List<Paragraph> paragraphs = paraFactory.getParagraphs(lastParagraph);

		assertEquals(3, paragraphs.size());

		assertEquals(null, paragraphs.get(2).getTitle());
		assertEquals("To ostatni.", paragraphs.get(2).getText());

	}
	
	public void testAdditionalNewLineParagraph() {

		this.config.setMinimalParagraphLength(4);
		ParagraphBuilder paraFactory = getParagraphFactory();
		List<Paragraph> paragraphs = paraFactory.getParagraphs(additionalNewLineParagraph);

		assertEquals(3, paragraphs.size());

		assertEquals(null, paragraphs.get(2).getTitle());
		assertEquals("To ostatni.", paragraphs.get(2).getText());

	}

}
