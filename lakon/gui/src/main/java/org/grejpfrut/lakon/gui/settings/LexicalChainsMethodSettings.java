package org.grejpfrut.lakon.gui.settings;

import org.grejpfrut.lakon.gui.utils.ThresholdHelper;
import org.grejpfrut.lakon.summarizer.settings.LexicalChainsSettings;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.lakon.summarizer.settings.LexicalChainsSettings.Representatives;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

/**
 * 
 * @author Adam Dudczak
 */
public class LexicalChainsMethodSettings extends MethodSettings {

	private boolean buildOnly = false;

	public LexicalChainsMethodSettings(Settings settings, boolean buildOnly) {
		super(settings);
	}
	
	public LexicalChainsMethodSettings(Settings settings) {
		super(settings);
	}


	@Override
	public ProcessSettings createClone() {
		synchronized (this) {
			return new LexicalChainsMethodSettings(params.createClone(),
					buildOnly);
		}
	}

	@Override
	protected DefaultFormBuilder buildForm() {

		DefaultFormBuilder builder = buildOnly ? new DefaultFormBuilder(
				new FormLayout("pref", "")) : super.buildForm();

		builder.append("Lexical chains");

		builder.append(ThresholdHelper.createIntegerThreshold(this,
				LexicalChainsSettings.P_MAX_NUMBER_OF_INTERPRETATIONS,
				"Number of interpretations", 2, 100, 1, 10));

		builder.appendSeparator("Representatives");

		String[] reprs = { Representatives.ON.toString(),
				Representatives.OFF.toString() };

		builder.append(ThresholdHelper.createStringCombo(reprs,
				LexicalChainsSettings.P_REPRESENTATIVE_MODE, this));

		builder.appendSeparator("Only nouns");

		String[] nouns = { Boolean.TRUE.toString(), Boolean.FALSE.toString() };

		builder.append(ThresholdHelper.createStringCombo(nouns,
				LexicalChainsSettings.P_ONLY_NOUNS, this));

		return builder;
	}

}
