package org.grejpfrut.lakon.summarizer.methods;

import java.util.List;

import org.grejpfrut.lakon.summarizer.Summarizer;
import org.grejpfrut.lakon.summarizer.settings.LocationSettings;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.lakon.summarizer.settings.LocationSettings.LocationMode;
import org.grejpfrut.tiller.entities.Sentence;

/**
 * Test {@link LocationBasedSummarizer} in
 * {@link LocationMode#FIRST_IN_PARAGRAPHS} mode.
 * 
 * @author Adam Dudczak
 */
public class LocationBasedSummarizerTest extends SummarizerTest {


	private static final String TEST_LOCATION_RESULT1 = "test.location.result.1";

	private static final String TEST_LOCATION_RESULT2 = "test.location.result.2";

	private static final String TEST_LOCATION_RESULT3 = "test.location.result.3";

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.settings = new LocationSettings();
	}

	
	@Override
	protected Summarizer getSummarizer() {

		settings.put(LocationSettings.P_LOCATION_MODE,
				LocationMode.FIRST_IN_PARAGRAPHS.toString());
		return new LocationBasedSummarizer(settings);

	}

	public void test1Sentences() {

		int length = 1;
		String result = super.testData.getProperty(TEST_LOCATION_RESULT1); 
		checkContent(length, result);
	}
	
	public void test2Sentences() {

		int length = 2;
		String result = super.testData.getProperty(TEST_LOCATION_RESULT2); 
		checkContent(length, result);
	}

	public void test3Sentences() {

		int length = 3;
		String result = super.testData.getProperty(TEST_LOCATION_RESULT3); 
		checkContent(length, result);
	}

	
	private void checkContent(int length, String result) {
		
		super.settings.put(Settings.P_SUMMARY_LENGTH, new Integer(length));
		super.settings.put(LocationSettings.P_LOCATION_MODE,
				LocationMode.FIRST_IN_PARAGRAPHS.toString());
		Summarizer summ = new LocationBasedSummarizer(super.settings);
		List<Sentence> ss = summ.summarize(result);
		assertEquals(length, ss.size());
		String summary = getSummaryString(ss);
		assertEquals(result, summary);
		
	}

	private String getSummaryString(List<Sentence> ss) {
		StringBuffer sb = new StringBuffer();
		for(Sentence s : ss){
			sb.append(" "+s.getText());
		}
		return sb.toString().substring(1);
	}

}
