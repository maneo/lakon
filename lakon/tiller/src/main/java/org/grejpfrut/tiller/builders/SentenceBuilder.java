package org.grejpfrut.tiller.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.grejpfrut.tiller.entities.Paragraph;
import org.grejpfrut.tiller.entities.Sentence;
import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.entities.impl.SentenceImpl;
import org.grejpfrut.tiller.utils.StringTools;
import org.grejpfrut.tiller.utils.TillerConfiguration;

/**
 * This class should generate {@link Sentence} objects. After passing
 * {@link String} with text of the paragraph, this should produce an array of
 * {@link Paragraph}.
 * 
 * //TODO support abbreviations at the end of sentence. (question is how to
 * support...?)
 * 
 * @author Adam Dudczak
 * 
 */
public class SentenceBuilder {

	/**
	 * Sentences are build up from tokens.
	 */
	private final TokenBuilder tokenBuilder;

	/**
	 * Set of most common abbreviations. Can be empty but not null.
	 */
	private Set<String> abbrs;

	private static Pattern endOfSentence = Pattern
			.compile("((\\.+)|\\?|\\!|;)$");

	/**
	 * Initializes all fields for this class including {@link TokenBuilder} and
	 * {@link SentenceBuilder#abbrs}.
	 * 
	 * @param conf -
	 *            {@link TillerConfiguration} instance.
	 */
	public SentenceBuilder(TillerConfiguration conf) {

		this.tokenBuilder = new TokenBuilder(conf);
		this.abbrs = conf.getAbbreviationsList();

	}

	/**
	 * Gets {@link Sentence}s out of given paragraph text. It first splits
	 * input text into tokens, sentence can be ended with dot or semicolon.
	 * 
	 * @param paragraph -
	 *            text of given paragraph.
	 * @return an array of {@link Sentence}.
	 */
	public List<Sentence> getSentences(String paragraph) {

		List<Sentence> sentences = new ArrayList<Sentence>();

		// TODO: before splitting we should check for compound tokens.
		String[] splittedSentences = StringTools.cleanSentence(paragraph);
		List<String> sentenceBuffer = new ArrayList<String>();
		for (String word : splittedSentences) {
			final String endSign = endOfSentence(word);

			// TODO: condition with isAbbr is necessary only when endSign == '.'
			if ((endSign != null) && !isAbbr(word)) {
				sentenceBuffer.add(word.substring(0, word.length()
						- endSign.length()));
				List<Token> tokens = this.tokenBuilder
						.getTokens(sentenceBuffer);
				Sentence nsent = new SentenceImpl(tokens, endSign);
				sentenceBuffer.clear();
				sentences.add(nsent);
			} else {
				sentenceBuffer.add(word);
			}
		}
		if (sentenceBuffer.size() != 0) {
			List<Token> tokens = this.tokenBuilder.getTokens(sentenceBuffer);
			sentences.add(new SentenceImpl(tokens));
		}
		return sentences;

	}

	/**
	 * Checks for the end of sentence.
	 * 
	 * @param token -
	 *            curent token
	 * @return
	 */
	private String endOfSentence(String token) {
		final Matcher m = endOfSentence.matcher(token);
		if (!m.find())
			return null;
		return m.group();
	}

	/**
	 * Method which checks whether given word is an abbreviation. Numbers with
	 * dot and one letter words- are always treated as abbreviations.
	 * 
	 * @param word -
	 *            future token.
	 * @return true if input is an abbreviation, false otherwise.
	 */
	private boolean isAbbr(String word) {
		String wordWithoutDot = StringTools.getCleanAbbr(word);
		if ((wordWithoutDot.length() == 1)
				|| StringTools.isNumber(wordWithoutDot))
			return true;
		return this.abbrs.contains(wordWithoutDot);
	}

}