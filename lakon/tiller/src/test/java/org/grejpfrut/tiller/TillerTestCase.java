package org.grejpfrut.tiller;

import java.util.Properties;

import junit.framework.TestCase;

import org.grejpfrut.tiller.utils.TillerConfiguration;

/**
 * Basic test case for all Tiller functional tests.
 * 
 * @author Adam Dudczak
 */
public abstract class TillerTestCase extends TestCase {

	protected Properties testData = new Properties();

	private final static String PATH_TO_DEFAULT_TEST_DATA = "/texts.xml";

	protected TillerConfiguration config = new TillerConfiguration(null);

	protected void setUp() throws Exception {
		this.setUp(null);
	}

	protected void setUp(String pathToTestData) throws Exception {
		
		if (pathToTestData == null)
			testData.loadFromXML(this.getClass().getResourceAsStream(
					PATH_TO_DEFAULT_TEST_DATA));
		else
			testData.loadFromXML(this.getClass().getResourceAsStream(
					pathToTestData));
	}

}
