package org.grejpfrut.lakon.gui.settings;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.grejpfrut.lakon.gui.utils.ThresholdHelper;
import org.grejpfrut.lakon.summarizer.settings.Settings;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

/**
 *  
 * @author Adam Dudczak
 */
public class MethodSettings extends ProcessSettingsBase {

	public MethodSettings(Settings settings) {
		params = settings;
		setLiveUpdate(true);
	}

	@Override
	public ProcessSettings createClone() {
		synchronized (this) {
			return new MethodSettings(params.createClone());
		}
	}

	@Override
	public JComponent getSettingsComponent(Frame owner) {

		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());

		final DefaultFormBuilder builder = buildForm();
		
		panel.add(builder.getPanel(), BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane(panel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		return scrollPane;

	}

	protected DefaultFormBuilder buildForm() {
		
		final DefaultFormBuilder builder = new DefaultFormBuilder(
				new FormLayout("pref", ""));

		builder.appendSeparator("Basic");

		builder.append(ThresholdHelper.createIntegerThreshold(this,
				Settings.P_SUMMARY_LENGTH, "Summary length", 0, 30, 1, 10));
		
		return builder;
		
	}

	@Override
	public boolean isConfigured() {
		return true;
	}


}
