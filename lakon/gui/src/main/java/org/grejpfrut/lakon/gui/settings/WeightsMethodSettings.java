package org.grejpfrut.lakon.gui.settings;

import org.grejpfrut.lakon.gui.utils.ThresholdHelper;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings.SignatureWords;
import org.grejpfrut.lakon.summarizer.settings.WeightSettings.WeightType;

import com.jgoodies.forms.builder.DefaultFormBuilder;

/**
 * 
 * @author Adam Dudczak
 */
public class WeightsMethodSettings extends MethodSettings {

	public WeightsMethodSettings(Settings settings) {
		super(settings);

	}

	@Override
	protected DefaultFormBuilder buildForm() {
		DefaultFormBuilder builder = super.buildForm();

		builder.appendSeparator("");

		String[] weightsTypes = { WeightType.BM_25.toString(),
				WeightType.TFIDF.toString() };

		builder.appendSeparator("Weight type");
		
		builder.append(ThresholdHelper.createStringCombo(weightsTypes, WeightSettings.P_WEIGHT_TYPE, this));

		builder.appendSeparator("Signature words");
		
		String[] signatureWords = { SignatureWords.NOUNS.toString(),
				SignatureWords.ALL.toString() };
		
		builder.append(ThresholdHelper.createStringCombo(signatureWords, 
				WeightSettings.P_SIGNATURE_WORDS_TYPE, this));

		builder.appendSeparator("Weights");

		builder.append(ThresholdHelper.createIntegerThreshold(this,
				WeightSettings.P_NO_OF_KEYWORDS, "Number of keywords", -1,
				50, 1, 10));
		
		builder.append(ThresholdHelper.createDoubleThreshold(this,
				WeightSettings.P_TFIDF_TRESHOLD, "Minimal tfidf value:", 0.0,
				0.6, 0.05, 0.1));
		
		builder.append(ThresholdHelper.createDoubleThreshold(this,
				WeightSettings.P_BM25_TRESHOLD, "Minimal bm25 value:", 0.0,
				0.6, 0.05, 0.1));
		
//		builder.append(ThresholdHelper.createDoubleThreshold(this,
//				WeightSettings.P_AVE_DOC_LENGTH, "Ave document length:", 0.0,
//				120.0, 10.0, 20.0));

		
		builder.append(ThresholdHelper.createDoubleThreshold(this,
				WeightSettings.P_AVE_DOC_LENGTH, "Ave document length:", 0,
				100.0, 10.0, 20.0));

		
		return builder;
	}



	@Override
	public ProcessSettings createClone() {
		synchronized (this) {
			return new WeightsMethodSettings(params.createClone());
		}
	}

}
