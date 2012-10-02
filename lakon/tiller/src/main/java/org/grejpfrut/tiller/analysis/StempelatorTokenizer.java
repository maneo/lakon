package org.grejpfrut.tiller.analysis;

import java.io.IOException;
import java.util.Arrays;

import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.entities.impl.TokenImpl;
import org.grejpfrut.tiller.utils.StringTools;
import org.grejpfrut.tiller.utils.TillerConfiguration;

import com.dawidweiss.stemmers.Stempelator;

/**
 * This tokenizer extends {@link StopWordsTokenizer} it adds base forms taken
 * from {@link Stempelator}.
 * 
 * @author ad
 * 
 */
public class StempelatorTokenizer extends StopWordsTokenizer {

	/**
	 * {@link Stempelator} instance.
	 */
	private final Stempelator analyzer;

	/**
	 * Passes configuration to super class and creates
	 * {@link StempelatorTokenizer#analyzer}.
	 * 
	 * @param conf -
	 *            configuration.
	 */
	public StempelatorTokenizer(TillerConfiguration conf) {
		super(conf);

		try {
			analyzer = new Stempelator();
		} catch (IOException e) {
			throw new RuntimeException(
					"Exception while initializing Stemplator", e);
		}

	}

	/**
	 * Creates {@link Token} instance. Sets base forms, and checks for
	 * stopwordnes of input word.
	 */
	public Token getToken(String word) {
		String cleanOne = StringTools.cleanToken(word);
		String[] res = this.analyzer.stem(cleanOne);
		Token tk = new TokenImpl(word, Arrays.asList(res), null);
		tk.setStopWord(super.isStopWord(cleanOne));
		return tk;
	}

}
