package org.grejpfrut.tiller.utils;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Useful hacks for operation with Strings.
 * 
 * @author Adam Dudczak
 */
public class StringTools {

	/**
	 * Pattern used to recognise numbers in text.
	 */
	private static Pattern NUMBER = Pattern.compile("\\d+");

	/**
	 * When testing for abbreviations, we should remove only first and last dot.
	 */
	private static final BasicCleanerImpl cleanAbbr = new BasicCleanerImpl(
			"^\\p{Punct}|\\p{Punct}$") {

		@Override
		protected String processTokens(String token) {
			return "";
		}

	};

	/**
	 * This
	 * 
	 * @{link {@link BasicCleanerImpl} is used to clean words from all
	 *        unnecessary chars.
	 */
	private static final BasicCleanerImpl cleanAll = new BasicCleanerImpl(
			"^\\p{Punct}+|\\p{Punct}+$") {

		@Override
		protected String processTokens(String token) {
			return "";
		}

	};

	/**
	 * Removes all non-letter chars from the begining of token.
	 */
	private static final BasicCleanerImpl cleanFromStart = new BasicCleanerImpl(
			"^\\p{Punct}+") {

		@Override
		protected String processTokens(String token) {
			return "";
		}

	};

	/**
	 * Removes all whitespaces before interpunction. Helps to deal with: "words
	 * in this sentence . Another sentence." -> "words in this sentence. Another
	 * sentence." This won't affect on "-" and "(" used in sentences.
	 */
	private static final BasicCleanerImpl cleanWhiteSpaces = new BasicCleanerImpl(
			"\\s+\\p{Punct}\\s*") {

		@Override
		protected String processTokens(String token) {
			String trimmed = token.trim();
			if (trimmed.equals("-") || trimmed.equals("(")
					|| trimmed.equals("\""))
				return token;
			return trimmed + " ";
		}

	};

	/**
	 * See {@link StringTools#cleanWhiteSpaces} comment.
	 * 
	 * @param sentence
	 * @return
	 */
	public static String[] cleanSentence(String sentence) {
		String cleanOne = cleanWhiteSpaces.processMatchingMarkup(sentence)
				.trim();
		return cleanOne.split("\\s+");
	}

	/**
	 * Removes all unnecessary (non-letter) chars, from given token.
	 * 
	 * @param token
	 * @return clean token.
	 */
	public static String removeSpecials(String token) {
		return cleanAll.processMatchingMarkup(token);
	}

	/**
	 * Removes all unnecessary (non-letter) chars, from beggining of given
	 * token.
	 * 
	 * @param token
	 * @return
	 */
	public static String removeSpecialsFromStart(String token) {
		return cleanFromStart.processMatchingMarkup(token);
	}

	/**
	 * @param token -
	 *            potential number.
	 * @return gets information is given word a number.
	 */
	public static boolean isNumber(String token) {
		return NUMBER.matcher(token).matches();
	}

	/**
	 * Removes special at most one character from start, and atmost one special
	 * character from the end of given token.
	 * 
	 * @param token
	 * @return
	 */
	public static String getCleanAbbr(String token) {
		return cleanAbbr.processMatchingMarkup(token);
	}

	/**
	 * Cleans token from special signs, and lower its case.
	 * 
	 * @param token
	 * @return
	 */
	public static String cleanToken(String token) {
		final String cl = token.toLowerCase(new Locale("pl"));
		return StringTools.removeSpecials(cl);
	}

}
