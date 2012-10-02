package org.grejpfrut.tiller.analysis;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.entities.Token.PartOfSpeech;
import org.grejpfrut.tiller.entities.impl.TokenImpl;
import org.grejpfrut.tiller.utils.StringTools;
import org.grejpfrut.tiller.utils.TillerConfiguration;

import com.dawidweiss.morfeusz.Analyzer;
import com.dawidweiss.morfeusz.InterpMorf;
import com.dawidweiss.morfeusz.Morfeusz;

/**
 * This {@link TillerTokenizer} extends {@link StopWordsTokenizer} it creates
 * {@link Token}s with all base forms and input words information it also
 * checks whether given word is a stop word or not.
 * 
 * @author Adam Dudczak
 * 
 */
public class MorpheusTokenizer extends StopWordsTokenizer {

	/**
	 * Encoding used by Morpheus stemmer.
	 */
	private static final String MORPHEUS_ENCODING = "iso8859-2";

	/**
	 * {@link Analyzer} instance.
	 */
	private final Analyzer analyzer;

	/**
	 * Tags indicating that given token is noun.
	 */
	private final static String[] NOUN_TAGS = new String[] { "subst", "depr" };

	/**
	 * Tags indicating that given token is verb.
	 */
	private final static String[] VERB_TAGS = new String[] { "fin", "bedzie",
			"aglt", "praet", "impt", "imps", "inf", "pcon", "pant", "ger",
			"pact", "ppas" };

	/**
	 * Tags indicating that given token is unknow.
	 */
	private final static String[] UNKNOWN_TAGS = new String[] { "xxx", "xxs" };

	/**
	 * Tags indicating that given token is adjective (adj, adja, adjp).
	 */
	private final static String[] ADJECTIVE_TAGS = new String[] { "adj",
			"adja", "adjp" };

	/**
	 * Map used to determine which part of speech token is.
	 */
	private Map<String, PartOfSpeech> tags = new HashMap<String, PartOfSpeech>();

	/**
	 * Passes configuration to super class and creates
	 * {@link MorpheusTokenizer#analyzer} instance.
	 * 
	 * @param conf -
	 *            configuration.
	 */
	public MorpheusTokenizer(TillerConfiguration conf) {
		super(conf);

		try {
			this.analyzer = Morfeusz.getInstance().getAnalyzer();
		} catch (Exception e) {
			throw new RuntimeException(
					"Exception while initializing Morpheus analyser instance",
					e);
		}
		addTag(UNKNOWN_TAGS, PartOfSpeech.UNKNOWN);
		addTag(NOUN_TAGS, PartOfSpeech.NOUN);
		addTag(ADJECTIVE_TAGS, PartOfSpeech.ADJECTIVE);
		addTag(VERB_TAGS, PartOfSpeech.VERB);
	}

	/**
	 * Additional method, helps create mapping of Morpheus tags to
	 * {@link PartOfSpeech} enum.
	 * 
	 * @param morphtags
	 * @param tillerTag
	 */
	private void addTag(String[] morphtags, PartOfSpeech tillerTag) {
		for (String tag : morphtags) {
			this.tags.put(tag, tillerTag);
		}
	}

	/**
	 * Gets token out of given word. It sets all base forms, and morphological
	 * information about given word.
	 */
	public Token getToken(String word) {

		try {
			InterpMorf[] analysis;
			String cleanOne = StringTools.cleanToken(word);
			if (super.isStopWord(cleanOne)) {
				List<String> baseForm = new ArrayList<String>();
				baseForm.add(cleanOne);
				Token tk = new TokenImpl(word, baseForm, null);
				tk.setStopWord(true);
				return tk;
			}
			analysis = this.analyzer.analyze(cleanOne
					.getBytes(MORPHEUS_ENCODING));
//			final int numberOfTokens = analyzer.getTokensNumber();
			Token tk = null;
			List<String> lemmas = new ArrayList<String>();
			List<PartOfSpeech> infos = new ArrayList<PartOfSpeech>();

			for (int j = 0; j < analyzer.getTokensNumber(); j++) {
				lemmas.add(new String(analysis[j].getLemma(), 0, analysis[j]
						.getLemmaLength(), MORPHEUS_ENCODING));
				final String mTag = new String(analysis[j].getTag(), 0,
						analysis[j].getTagLength(), MORPHEUS_ENCODING);
				infos.add(convertTag(mTag));
			}
			if ((lemmas.size() != 0) && (!lemmas.get(0).equals("")))
				tk = new TokenImpl(word, lemmas, infos);
			else {
				List<String> baseForm = new ArrayList<String>();
				baseForm.add(cleanOne);
				tk = new TokenImpl(word, baseForm, null);

			}
			tk.setStopWord(super.isStopWord(cleanOne));
			return tk;

		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * This method does the conversion between Morpheus part of speech tags to
	 * {@link PartOfSpeech}.
	 * 
	 * @param tag -
	 *            Morpehus tag.
	 * @return {@link PartOfSpeech}.
	 */
	private PartOfSpeech convertTag(String tag) {
		if ("".equals(tag) || (tag == null) || (tag.indexOf(":") == -1))
			return PartOfSpeech.UNKNOWN;
		PartOfSpeech ttag = this.tags.get(tag.substring(0, tag.indexOf(":")));
		if (ttag == null)
			return PartOfSpeech.UNKNOWN;
		return ttag;

	}
}
