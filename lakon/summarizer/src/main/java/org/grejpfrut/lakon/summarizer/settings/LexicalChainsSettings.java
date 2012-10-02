package org.grejpfrut.lakon.summarizer.settings;

import java.util.Map;
import java.util.Properties;

/**
 * 
 * @author Adam Dudczak
 */
public class LexicalChainsSettings extends Settings {

	public final static String P_PATH_TO_THESAURI = "path.to.thesaurus";

	public final static String P_MAX_NUMBER_OF_INTERPRETATIONS = "max.interpretation";

	public final static String P_REPRESENTATIVE_MODE = "representative.mode";
	
	public final static String P_ONLY_NOUNS = "only.nouns";
	
	public enum Representatives {
		ON, OFF
	}

	public LexicalChainsSettings(Map<String, Object> map) {
		super(map);
	}

	public LexicalChainsSettings() {
		super();
	}

	public LexicalChainsSettings(Properties conf){
		super(conf);
	}
	
	@Override
	public Settings createClone() {
		synchronized (this) {
			return new LexicalChainsSettings(this.settings);
		}
	}

}
