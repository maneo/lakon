package org.grejpfrut.lakon.summarizer.settings;

import java.util.Map;
import java.util.Properties;

import org.grejpfrut.lakon.summarizer.methods.LocationBasedSummarizer;

/**
 * This settings class groups all configuration 
 * required by {@link LocationBasedSummarizer}. 
 *  
 * @author Adam Dudczak
 */
public class LocationSettings extends Settings {

	public enum LocationMode {
		FIRST, FIRST_IN_PARAGRAPHS
	}
	
	public final static String P_LOCATION_MODE = "location.mode";
	
	
	public LocationSettings(Map<String, Object> map) {
		super(map);
	}
	
	
	public LocationSettings() {
		super();
	}
	
	public LocationSettings(Properties conf) {
		super(conf);
	}
	
	

	@Override
	public Settings createClone() {
		synchronized (this) {
			return new LocationSettings(this.settings);
		}
	}

}


