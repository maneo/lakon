package org.grejpfrut.evaluation.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * This bean is used to get xml properties from disk. When you inject this bean
 * you can get access to content stored in xml files eg. project-info page text.
 * 
 * @author Adam Dudczak
 */
public class ContentBean {

	private Properties content = new Properties();


	/**
	 * Gets content properties from xml file.
	 */
	public ContentBean() {
		init();
	}

	private void init() {
		InputStream fis = this.getClass().getResourceAsStream("/content-pages.xml");
		try {
			this.content.loadFromXML(fis);
		} catch (Exception e) {
			throw new RuntimeException("Cannot find file content-pages.xml ",e);
		}
	}

	/**
	 * @param key -
	 *            desired key.
	 * @return Gets desired key.
	 */
	public String get(String key) {
		return content.getProperty(key);
	}


}
