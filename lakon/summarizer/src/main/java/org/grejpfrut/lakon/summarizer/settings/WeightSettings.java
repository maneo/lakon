package org.grejpfrut.lakon.summarizer.settings;

import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Adam Dudczak
 */
public class WeightSettings extends Settings implements Cloneable{
	
	public final static String P_TFIDF_TRESHOLD = "min.tfidf";

	public final static String P_BM25_TRESHOLD = "min.bm25";
	
	public final static String P_NO_OF_KEYWORDS = "no.of.keywords";
	
	public final static String P_REFERENCE_INDEX_DIR = "ref.index.dir";
	
	public final static String P_SIGNATURE_WORDS_TYPE = "signature.words.type";
	
	public final static String P_WEIGHT_TYPE = "weight.type";
	
	public final static String P_AVE_DOC_LENGTH = "ave.doc.length";

	
	public enum SignatureWords {
		NOUNS, ALL
	}

	public enum WeightType {
		BM_25, TFIDF
	}


	/**
	 * Default minimum value of TFIDF
	 */
	public static final double DEFAULT_MIN_BM25 = 0.025;

	
	public WeightSettings(Map<String, Object> map) {
		super(map);
	}
	
	public WeightSettings() {
		super();
	}
	
	public WeightSettings(Properties conf) {
		super(conf);
	}


	@Override
	public Settings createClone() {
		synchronized (this) {
			return new WeightSettings(this.settings);
		}
	}

	
	
	
	
	

}

