package org.grejpfrut.tiller.builders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.grejpfrut.tiller.analysis.StopWordsTokenizer;
import org.grejpfrut.tiller.analysis.TillerTokenizer;
import org.grejpfrut.tiller.entities.Token;
import org.grejpfrut.tiller.utils.ClassTool;
import org.grejpfrut.tiller.utils.TillerConfiguration;

/**
 * Produces {@link Token}.
 * 
 * @author Adam Dudczak
 * 
 */
public class TokenBuilder {

	/**
	 * Tokenizer class instance.
	 */
	public TillerTokenizer tokenizer = null;

	/**
	 * This one configures {@link TokenBuilder#tokenizer}. If there is no
	 * {@link TillerConfiguration#TOKENIZER_CLASS_NAME_PROPERTY} given in configuration
	 * it creates {@link StopWordsTokenizer} by default.
	 * 
	 * @param conf -
	 *            configuration.
	 */
	public TokenBuilder(TillerConfiguration conf) {

		final String tokenizerClass = conf.getTokenizerClassName();
		if ((tokenizerClass != null) && (!"".equals(tokenizerClass))) {
			this.tokenizer = (TillerTokenizer) ClassTool.getInstance(
					tokenizerClass, new Object[] { conf });
		} else {
			this.tokenizer = new StopWordsTokenizer(conf);
		}

	}

	/**
	 * Gets a token, when there is no analyser it will return the token with
	 * given word as a base form.
	 * 
	 * @param
	 * @return
	 */
	public Token getToken(String word) {
		return this.tokenizer.getToken(word);
	}

	/**
	 * Gets list of tokens out of given sentence.
	 * @param sentence - raw text of sentence.
	 * @return an array of tokens.
	 */
	public List<Token> getTokens(String sentence) {
		String[] tokens = sentence.split(" ");
		return getTokens(Arrays.asList(tokens));
	}
	
	/**
	 * Gets list of tokens out of list of 
	 * tokens in given sentence.
	 * @param sentence 
	 * @return 
	 */
	public List<Token> getTokens(List<String> sentence) {
		
		List<Token> result = new ArrayList<Token>();
		for (int i = 0; i < sentence.size(); i++) {
			result.add(this.getToken(sentence.get(i)));
		}
		return result;

		
	}

}
