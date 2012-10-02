package org.grejpfrut.lakon.summarizer.methods;

import org.grejpfrut.lakon.summarizer.Summarizer;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings.SignatureWords;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings.WeightType;

/**
 * {@link WeightsBasedSummarizer} test in {@link WeightSettings}.BM_25 mode.
 * @author Adam Dudczak
 */
public class BM25BasedSummarizationTest extends SummarizerTest {


	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.settings = new WeightSettings();
	}
	
	@Override
	protected Summarizer getSummarizer() {
		this.settings.put(WeightSettings.P_SIGNATURE_WORDS_TYPE, SignatureWords.ALL.toString());
		this.settings.put(WeightSettings.P_WEIGHT_TYPE, WeightType.BM_25.toString());
		return new WeightsBasedSummarizer(this.settings);
	}
	
	public void test3Sentence(){
		testLength(3, super.testData.getProperty(TEST_SUMMARY_LENGTH));
	}

}
