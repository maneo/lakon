package org.grejpfrut.lakon.gui.settings;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

import org.grejpfrut.lakon.gui.utils.ThresholdHelper;
import org.grejpfrut.lakon.summarizer.settings.LocationSettings;
import org.grejpfrut.lakon.summarizer.settings.Settings;
import org.grejpfrut.lakon.summarizer.settings.LocationSettings.LocationMode;

import com.jgoodies.forms.builder.DefaultFormBuilder;

/**
 * 
 * @author Adam Dudczak
 */
public class LocationMethodSettings extends MethodSettings {

	public LocationMethodSettings(Settings settings) {
		super(settings);
	}

	@Override
	public ProcessSettings createClone() {
		synchronized (this) {
			return new LocationMethodSettings(params.createClone());
		}
	}

	@Override
	protected DefaultFormBuilder buildForm() {
		DefaultFormBuilder builder = super.buildForm();

		builder.appendSeparator("Location");

		String[] locationMode = { LocationMode.FIRST.toString(),
				LocationMode.FIRST_IN_PARAGRAPHS.toString() };

		builder.append(ThresholdHelper.createStringCombo(locationMode,
				LocationSettings.P_LOCATION_MODE, this));

		return builder;
	}

}
