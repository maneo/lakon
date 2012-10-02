package org.grejpfrut.wiki.utils;

import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.dawidweiss.morfeusz.Analyzer;
import com.dawidweiss.morfeusz.InterpMorf;
import com.dawidweiss.morfeusz.Morfeusz;

/**
 * Morpheus stemmer wrapper.
 * 
 * @author ad
 * 
 */
public class MorfStemmer extends Stemmer {

	static Logger logger = Logger.getLogger(MorfStemmer.class);

	private Analyzer analyzer;

	public MorfStemmer() {
		try {
			analyzer = Morfeusz.getInstance().getAnalyzer();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public String getDocAsStems(String article) {

		String res = "";
		StringTokenizer tokenizer = new StringTokenizer(article, " ");
		try {
			while (tokenizer.hasMoreTokens()) {
				InterpMorf[] analysis;
				String token = tokenizer.nextToken();
				if (!containsInvalidChars(token)) {
					analysis = analyzer.analyze(token.getBytes("iso8859-2"));
					if (analysis == null)
						res = res + " ";
					else {
						for (int j = 0; j < analyzer.getTokensNumber(); j++) {
							String lemma = new String(analysis[j].getLemma(),
									0, analysis[j].getLemmaLength(),
									"iso8859-2");

							if (lemma != null) {
								if (isTokenAllowed(lemma))
									res = res + " " + lemma + " ";

							}
						}
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.fatal(e.getMessage());
		}

		return res;
	}

	/**
	 * 
	 */
	public String doStem(String article) {
		return this.getDocAsStems(article).trim();
	}

}
