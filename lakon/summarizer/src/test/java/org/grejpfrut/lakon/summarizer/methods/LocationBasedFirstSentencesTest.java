package org.grejpfrut.lakon.summarizer.methods;

import java.util.List;

import org.grejpfrut.lakon.summarizer.Summarizer;
import org.grejpfrut.lakon.summarizer.settings.LocationSettings;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.lakon.summarizer.settings.LocationSettings.LocationMode;
import org.grejpfrut.tiller.entities.Sentence;

/**
 * Test {@link LocationBasedSummarizer} in {@link LocationMode#FIRST} mode.
 * 
 * @author Adam Dudczak
 */
public class LocationBasedFirstSentencesTest extends SummarizerTest {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.settings = new LocationSettings();
	}


	@Override
	protected Summarizer getSummarizer() {

		settings.put(LocationSettings.P_LOCATION_MODE,
				LocationMode.FIRST.toString());
		return new LocationBasedSummarizer(settings);

	}

	public void testFirst3Sentences() {

		final String text = super.testData.getProperty(TEST_SUMMARY_LENGTH);
		this.settings.put(LocationSettings.P_LOCATION_MODE,
				LocationMode.FIRST.toString());
		this.settings.put(Settings.P_SUMMARY_LENGTH, new Integer(3));
		Summarizer summ = new LocationBasedSummarizer(settings);
		List<Sentence> result = summ.summarize(text);
		String resultString = result.get(0).getText() + " "
				+ result.get(1).getText() + " " + result.get(2).getText();
		assertEquals(super.testData.getProperty("test.location.first.3"),
				resultString);

	}

}
