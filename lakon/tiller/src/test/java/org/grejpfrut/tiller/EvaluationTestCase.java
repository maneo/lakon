package org.grejpfrut.tiller;

import java.util.List;

import org.grejpfrut.tiller.builders.ArticleBuilder;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Paragraph;
import org.grejpfrut.tiller.entities.Sentence;

/**
 * Test based on real newspapers texts.
 * 
 * @author Adam Dudczak
 */
public class EvaluationTestCase extends TillerTestCase {

	private ArticleBuilder factory;

	protected void setUp() throws Exception {

		super.setUp();
		super.config.setMinimalParagraphLength(8);
		super.config
				.setAbbreviationsList("itp,itd,np,in,tzw,tzn,cdn,godz,ul,ok,r,bm,m.in");
		this.factory = new ArticleBuilder(config);

	}

	public void testRebelions() {
		Article art = getAndValidateArticle("test.rebelion.title",
				"test.rebelion");

		List<Paragraph> paras = art.getParagraphs();
		Paragraph fpara = paras.get(0);
		Paragraph lpara = paras.get(paras.size() - 1);
		final String firstParagraph = this.testData.getProperty(
				"test.rebelion.paragraph.first").trim();
		final String lastParagraph = this.testData.getProperty(
				"test.rebelion.paragraph.last").trim();
		assertEquals(firstParagraph, fpara.getText());
		assertEquals(lastParagraph, lpara.getText());

		final String secondSentence = this.testData.getProperty(
				"test.rebelion.sentence.second").trim();
		assertEquals(secondSentence, fpara.getSentences().get(1).getText());
		assertEquals(secondSentence, art.getSentences().get(1).getText());

	}

	public void testGodspeed() {

		Article art = getAndValidateArticle("test.godspeed.title",
				"test.godspeed");

		final String firstParagraph = this.testData.getProperty(
				"test.godspeed.paragraph.first").trim();
		final String lastParagraph = this.testData.getProperty(
				"test.godspeed.paragraph.last").trim();
		final String thirdSentence = this.testData.getProperty(
				"test.godspeed.sentence.third").trim();

		List<Paragraph> paras = art.getParagraphs();
		Paragraph fpara = paras.get(0);
		Paragraph lpara = paras.get(paras.size() - 1);

		assertEquals(firstParagraph, fpara.getText());
		assertEquals(lastParagraph, lpara.getText());
		assertEquals(thirdSentence, art.getSentences().get(2).getText());

	}

	public void testMeat() {

		Article art = getAndValidateArticle("test.meat.title", "test.meat");

		final String thirdParagraph = this.testData.getProperty(
				"test.meat.paragraph.third").trim();
		final String prelastParagraph = this.testData.getProperty(
				"test.meat.paragraph.prelast").trim();
		final String lastSentence = this.testData.getProperty(
				"test.meat.sentence.last").trim();

		List<Paragraph> paras = art.getParagraphs();
		Paragraph para3 = paras.get(2);
		Paragraph preLastPara = paras.get(paras.size() - 2);


		assertEquals(thirdParagraph, para3.getText());
		assertEquals(prelastParagraph, preLastPara.getText());

		List<Sentence> sentences = art.getSentences();
		int last = sentences.size() - 1;

		assertEquals(lastSentence, sentences.get(last).getText());

	}

	public void testParasTitles() {

		Article art = getAndValidateArticle("test.marsz.title", "test.marsz");
		List<Paragraph> paras = art.getParagraphs();
		final String thirdTitle = super.testData
				.getProperty("test.marsz.title.third.para");
		assertEquals(thirdTitle, paras.get(2).getTitle());

		final String sentence = super.testData
				.getProperty("test.marsz.sentence");
		assertEquals(sentence, paras.get(1).getSentences().get(0).getText());

	}

	public void testAbbr() {

		Article art = getAndValidateArticle("test.monster.title",
				"test.monster");
		List<Paragraph> paras = art.getParagraphs();

		assertEquals(super.testData.getProperty("test.monster.sentence"),
				paras.get(2).getSentences().get(0).getText());

	}

	private Article getAndValidateArticle(String titleProperty,
			String articleProperty) {

		final String title = this.testData.getProperty(titleProperty).trim();
		final String article = this.testData.getProperty(articleProperty)
				.trim();
		Article art = this.factory.getArcticle(article, title);
		assertEquals(title, art.getTitle());
		assertEquals(article, art.getText());
		return art;
	}
	
	public void test197385(){
		
		final String article = this.testData.getProperty("test.monster.1973-85")
				.trim();
		Article art = this.factory.getArcticle(article);
		List<Paragraph> paras = art.getParagraphs();
		assertEquals(1, paras.size());
		List<Sentence> sentences = paras.get(0).getSentences();
		assertEquals(2, sentences.size());
		
	}
	
	
	public void testMin(){
		
		final String article = this.testData.getProperty("test.m.in").trim();
		Article art = this.factory.getArcticle(article);
		List<Paragraph> paras = art.getParagraphs();
		assertEquals(1, paras.size());
		List<Sentence> sentences = paras.get(0).getSentences();
		assertEquals(1, sentences.size());
		
	}
	
	public void testNumbers(){
		
		final String article = this.testData.getProperty("test.numbers").trim();
		Article art = this.factory.getArcticle(article);
		List<Paragraph> paras = art.getParagraphs();
		assertEquals(1, paras.size());
		List<Sentence> sentences = paras.get(0).getSentences();
		assertEquals(2, sentences.size());
		
	}
	
	
	
	

}
