package org.grejpfrut.lakon.summarizer;

import java.util.Properties;

import junit.framework.TestCase;

/**
 * Abstract test case which loads test data from standard location or from given
 * one.
 * 
 * @author Adam Dudczak
 */
public abstract class AbstractDataTestCase extends TestCase {

	protected Properties testData = new Properties();

	private final static String PATH_TO_DEFAULT_TEST_DATA = "/texts.xml";

	protected void setUp() throws Exception {
		this.setUp(null);
	}

	/**
	 * By using this setup method from subclass you can pass path to custom file
	 * with data. Which will be loaded into
	 * {@link AbstractDataTestCase#testData} field. For access to default file
	 * use {@link AbstractDataTestCase#setUp()} method.
	 * 
	 * @param pathToTestData
	 * @throws Exception
	 */
	protected void setUp(String pathToTestData) throws Exception {

		if (pathToTestData == null)
			testData.loadFromXML(this.getClass().getResourceAsStream(
					PATH_TO_DEFAULT_TEST_DATA));
		else
			testData.loadFromXML(this.getClass().getResourceAsStream(
					pathToTestData));
	}

}
