package org.grejpfrut.tiller.entities.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.grejpfrut.tiller.entities.Article;
import org.grejpfrut.tiller.entities.Paragraph;
import org.grejpfrut.tiller.entities.Sentence;
import org.grejpfrut.tiller.entities.Token;

/**
 * Simple implementation of {@link Article} interface. It handles all features
 * defined in implemented interface.
 * 
 * @author Adam Dudczak
 * 
 */
public class ArticleImpl implements Article {

	/**
	 * Name of {@link Document} field which stores stemmed text of article.
	 */
	public static final String STEMS_FIELD_NAME = "stems";

	/**
	 * Name of {@link Document} field which stores normal text.
	 */
	public static final String TEXT_FIELD_NAME = "text";

	/**
	 * Name of {@link Document} field which stores article title.
	 */
	public static final String TITLE_FIELD_NAME = "title";

	/**
	 * An array with {@link Paragraph}s of this {@link Article}
	 */
	private final List<Paragraph> paragraphs;

	/**
	 * An array with {@link Sentence}s of this {@link Article}
	 */
	private List<Sentence> sentences = null;

	/**
	 * An array with {@link Token}s of this {@link Article}
	 */
	private List<Token> tokens = null;

	/**
	 * Optional title of this article.
	 */
	private String title;

	/**
	 * @param paragraphs -
	 *            paragraphs.
	 */
	public ArticleImpl(List<Paragraph> paragraphs) {
		this.paragraphs = paragraphs;
	}

	/**
	 * @param paragraphs -
	 *            paragraphs.
	 * @param title -
	 *            title of this article.
	 */
	public ArticleImpl(List<Paragraph> paragraphs, String title) {
		this.paragraphs = paragraphs;
		this.title = title;
	}

	/**
	 * Creates {@link Document} from this article. Created document has got
	 * three fields: title, text, stems.
	 */
	public Document getDocument() {

		final Document doc = new Document();
		doc.add(new Field(TITLE_FIELD_NAME, this.getTitle(), Field.Store.YES,
				Field.Index.UN_TOKENIZED, Field.TermVector.NO));
		doc.add(new Field(TEXT_FIELD_NAME, this.getText(), Field.Store.YES,
				Field.Index.UN_TOKENIZED,
				Field.TermVector.WITH_POSITIONS_OFFSETS));
		doc.add(new Field(STEMS_FIELD_NAME, this.getStemmedText(),
				Field.Store.NO, Field.Index.TOKENIZED,
				Field.TermVector.WITH_POSITIONS_OFFSETS));
		return doc;

	}

	/**
	 * Gets an array of {@link Paragraph}.
	 */
	public List<Paragraph> getParagraphs() {
		return this.paragraphs;
	}

	/**
	 * Gets an array of {@link Sentence}. If {@link ArticleImpl#sentences} is
	 * null method invokes {@link ArticleImpl#setSentences()} to create
	 * sentences array.
	 */
	public List<Sentence> getSentences() {
		if (this.sentences == null)
			setSentences();
		return this.sentences;
	}

	/**
	 * Gets the number of occurance of given {@link Token}.
	 */
	public int getNumberOfOccur(Token token) {
		int count = 0;
		for (Paragraph para : this.paragraphs) {
			count += para.getNumberOfOccur(token);
		}
		return count;
	}

	/**
	 * Gets stemmed text of the article (without stop words, all words in first
	 * base form).
	 */
	public String getStemmedText() {
		StringBuffer sb = new StringBuffer();
		for (Paragraph para : this.paragraphs) {
			sb.append(para.getStemmedText() + " ");
		}

		return sb.toString();
	}

	/**
	 * Gets human-readable text of this article.
	 */
	public String getText() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < paragraphs.size(); i++) {
			sb.append(paragraphs.get(i).getText());
			if (i < (paragraphs.size() - 1))
				sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * Gets an array of {@link Token}. If {@link ArticleImpl#tokens} is null
	 * method invokes {@link ArticleImpl#setTokens()} to create tokens array.
	 */
	public List<Token> getTokens() {
		if (this.tokens == null)
			setTokens();
		return this.tokens;
	}

	/**
	 * Gathers all {@link Sentence} from article's {@link Paragraph}s and sets
	 * {@link ArticleImpl#sentences} field.
	 * 
	 */
	private void setSentences() {
		List<Sentence> sts = new ArrayList<Sentence>();
		for (Paragraph paragraph : this.paragraphs) {
			sts.addAll(paragraph.getSentences());
		}
		this.sentences = sts;
	}

	/**
	 * Gathers all {@link Tokens} from article's {@link Sentence}s and sets
	 * {@link ArticleImpl#tokens} field. If {@link ArticleImpl#sentences} is
	 * null method invokes {@link ArticleImpl#setSentences()}.
	 */
	private void setTokens() {

		if (this.sentences == null)
			setSentences();

		List<Token> tks = new ArrayList<Token>();
		for (Sentence sentence : this.sentences) {
			tks.addAll(sentence.getTokens());
		}
		this.tokens = tks;

	}

	/**
	 * Gets article title.
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Sets article title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		return this.getText();
	}

}
