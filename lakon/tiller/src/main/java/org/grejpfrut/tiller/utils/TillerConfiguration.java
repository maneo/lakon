package org.grejpfrut.tiller.utils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import org.grejpfrut.tiller.builders.TokenBuilder;

/**
 * This class allows for simple configuration of Tiller factories. It introduces
 * getters and setters for all configuration properties. Properties to Object
 * mapping :)
 * 
 * @author Adam Dudczak
 * 
 * @TODO maybe we should change configuration file format to xml properties.
 */
public class TillerConfiguration {

	/**
	 * Path to default properties file.
	 */
	public static final String DEFAULT_CONF_PROPERTIES = "/tiller-conf.xml";

	/**
	 * Property key which stores a list of stop words.
	 */
	public static final String STOP_WORDS_PROPERTY = "stop.words";

	/**
	 * Property key with path to text file with thesaurus.
	 */
	public final static String PATH_TO_THESAURUS_PROPERTY = "path.to.thesaurus";

	/**
	 * Property key with information of minimum paragraph length in words.
	 */
	public final static String MIN_PARA_LENGTH_PROPERTY = "min.paragraph.length";

	/**
	 * Property key under which the list of most common abbreviations is passed.
	 */
	public final static String ABBREVIATIONS_LIST_PROPERTY = "abbr.list";

	/**
	 * Property key with class of tokenizer used to create tokens and init
	 * {@link TokenBuilder#tokenizer}.
	 */
	public static final String TOKENIZER_CLASS_NAME_PROPERTY = "tokenizer.class.name";

	/**
	 * Properties for storing configuration.
	 */
	private Properties conf;

	/**
	 * Set with tokenized stopwords.
	 */
	private Set<String> stopWords;

	/**
	 * Set with tokenized abbreviations list.
	 */
	private Set<String> abbrList;

	/**
	 * Default configuration can be overridden by passing custom configuration
	 * properties.
	 * 
	 * !! If conf param equals null {@link TillerConfiguration} won't load the
	 * defaults.
	 * 
	 * @param conf -
	 *            custom configuration keys.
	 */
	public TillerConfiguration(Properties conf) {
		if (conf != null)
			this.init(conf);
		else
			this.conf = new Properties();
	}

	/**
	 * Constructs default {@link TillerConfiguration} representing default
	 * configuration (stored in jar).
	 */
	public TillerConfiguration() {
		this.init(null);
	}

	/**
	 * If specified this method will override default configuration properties
	 * with those passed through constructor.
	 * 
	 * @param addProperties -
	 *            custom configuration.
	 */
	private void init(Properties addProperties) {

		try {
			this.conf = new Properties();
			this.conf.loadFromXML(TillerConfiguration.class
					.getResourceAsStream(DEFAULT_CONF_PROPERTIES));
		} catch (IOException e) {
			throw new RuntimeException("Cannot find given configuration file",
					e);
		}
		if (addProperties != null)
			this.conf.putAll(addProperties);
	}
	
	public String get(String key){
		return (String) this.conf.get(key);
	}
	

	public void setTokenizerClassName(String tokenizerClassName) {
		this.conf.put(TOKENIZER_CLASS_NAME_PROPERTY, tokenizerClassName);
	}

	public void setAbbreviationsList(String abbreviationsList) {
		this.conf.put(ABBREVIATIONS_LIST_PROPERTY, abbreviationsList);
		this.abbrList = tokenize(abbreviationsList);
	}

	public void setMinimalParagraphLength(int minParaLength) {
		this.conf.put(MIN_PARA_LENGTH_PROPERTY, String.valueOf(minParaLength));
	}

	public void setPathToThesaurus(String pathToThesaurus) {
		this.conf.put(PATH_TO_THESAURUS_PROPERTY, pathToThesaurus);
	}

	public void setStopWords(String stopWords) {
		this.conf.put(STOP_WORDS_PROPERTY, stopWords);
		this.stopWords = tokenize(stopWords);
	}

	public Set<String> getStopWords() {
		if (this.stopWords == null)
			this.stopWords = tokenize(conf.getProperty(STOP_WORDS_PROPERTY));
		return new HashSet<String>(this.stopWords);
	}

	public Set<String> getAbbreviationsList() {
		if (this.abbrList == null)
			this.abbrList = tokenize(conf
					.getProperty(ABBREVIATIONS_LIST_PROPERTY));
		return new HashSet<String>(this.abbrList);
	}

	public String getPathToThesaurus() {
		return (String) this.conf.get(PATH_TO_THESAURUS_PROPERTY);
	}

	public String getTokenizerClassName() {
		return (String) this.conf.get(TOKENIZER_CLASS_NAME_PROPERTY);
	}

	public int getMinimalParagraphLength() {
		String minp = (String) this.conf.get(MIN_PARA_LENGTH_PROPERTY);
		if (minp == null)
			return 0;
		return new Integer(minp);
	}

	/**
	 * This method is used to produce {@link Set<String>} from comma separeted
	 * list of tokens.
	 * 
	 * @param list
	 *            String with comma separeted list of tokens.
	 */
	private Set<String> tokenize(String list) {
		Set<String> tokens = new HashSet<String>();
		if (list == null)
			return tokens;
		String[] sw = list.split(",");
		for (String w : sw) {
			tokens.add(w.toLowerCase(new Locale("pl")));
		}
		return tokens;
	}

}
