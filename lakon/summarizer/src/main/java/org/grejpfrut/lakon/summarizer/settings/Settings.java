package org.grejpfrut.lakon.summarizer.settings;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * Class which holds settings.
 * 
 * @author Adam Dudczak
 */
public class Settings {

	protected static final int DEFAULT_SUMMARY_LENGTH = 4;

	protected Map<String, Object> settings;

	protected final static String CONF_FILE = "/summarizer-conf.properties";

	private final static Logger logger = Logger.getLogger(Settings.class);

	/**
	 * Property key which holds number of sentences in result.
	 */
	public static final String P_SUMMARY_LENGTH = "length";

	/**
	 * Creates instance of {@link Settings} class, loads defaults and overides
	 * defaults with given values. Map is not parsed, values stored in given map
	 * as String won't be parsed.
	 * 
	 * @param map -
	 *            custom values.
	 */
	public Settings(Map<String, Object> map) {
		this.settings = new HashMap<String, Object>();
		loadDefaults();
		this.settings.putAll(map);
	}

	/**
	 * Creates instance of configuration, and loads defaults.
	 */
	public Settings() {
		this.settings = new HashMap<String, Object>();
		loadDefaults();
	}

	/**
	 * Creates instance, loads default configuration and covers defaults with
	 * given configuration.
	 * 
	 * @param conf
	 */
	public Settings(Properties conf) {
		this.settings = new HashMap<String, Object>();
		loadDefaults();
		parseProperties(conf);
	}

	/**
	 * Loads default values from {@link Settings#CONF_FILE}.
	 */
	private void loadDefaults() {
		Properties props = new Properties();
		try {
			props.load(this.getClass().getResourceAsStream(CONF_FILE));
			parseProperties(props);
		} catch (IOException e) {
			logger.warn("Can't load default configuration for summarizer");
		}
	}

	/**
	 * Parses given set of properties, and where it is possible creates instance
	 * of appropriate object.
	 * 
	 * @param props
	 */
	private void parseProperties(Properties props) {

		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			if (value != null) {
				// try to get real type of property value
				try {
					if (value.contains(".")) {
						this.settings.put(keyStr, Double.parseDouble(value));
					} else {
						this.settings.put(keyStr, Integer.parseInt(value));
					}
				} catch (NumberFormatException e) {
					this.settings.put(keyStr, value);
				}
			}
		}

	}

	/**
	 * Gets value for given key.
	 * 
	 * @param key -
	 *            String
	 * @return value as Object or null.
	 */
	public Object get(String key) {
		return settings.get(key);
	}

	/**
	 * Puts given value under given key.
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value) {
		this.settings.put(key, value);
	}

	/**
	 * Gets all keys in this {@link Settings} instance.
	 * 
	 * @return keyset.
	 */
	public Set<String> keySet() {
		return this.settings.keySet();
	}

	/**
	 * @return Gets map with all keys and values.
	 */
	public Map<String, Object> getMap() {
		return settings;
	}

	/**
	 * Creates clone of this {@link Settings} class.
	 * 
	 * @return Cloned {@link Settings}.
	 */
	public Settings createClone() {
		synchronized (this) {
			return new Settings(this.settings);
		}
	}

	public void setMap(Map<String, Object> map) {
		synchronized (this) {
			this.settings = map;
		}

	}

}
