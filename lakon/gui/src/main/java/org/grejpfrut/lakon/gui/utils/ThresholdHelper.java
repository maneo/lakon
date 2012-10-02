/*
 * Carrot2 project.
 *
 * Copyright (C) 2002-2006, Dawid Weiss, Stanisław Osiński.
 * Portions (C) Contributors listed in "carrot2.CONTRIBUTORS" file.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the repository checkout or at:
 * http://www.carrot2.org/carrot2.LICENSE
 */

package org.grejpfrut.lakon.gui.utils;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.carrot2.demo.swing.util.JIntThreshold;
import org.carrot2.demo.swing.util.JThreshold;
import org.grejpfrut.lakon.gui.settings.LexicalChainsMethodSettings;
import org.grejpfrut.lakon.gui.settings.MethodSettings;

/**
 * A small helper class for creating threshold components.
 * 
 * @author Dawid Weiss
 * @author Adam Dudczak - lakon adaptation.
 */
public class ThresholdHelper {

	public static JIntThreshold createIntegerThreshold(
			final MethodSettings settings, final String key, String label,
			int min, int max, int minorTick, int majorTick) {
		final JIntThreshold comp = new JIntThreshold(label, min, max,
				minorTick, majorTick);

		final Integer paramValue = (Integer) settings.get(key);
		if (paramValue != null) {
			comp.setValue(paramValue);
		} else
			comp.setValue(min);

		comp.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				settings.put(key, comp.getValue());
			}
		});
		comp.setAlignmentX(Component.LEFT_ALIGNMENT);
		return comp;
	}

	public static JComponent createDoubleThreshold(
			final MethodSettings settings, final String key, String label,
			double min, double max, double minorTick, double majorTick) {
		final double value = (Double) settings.get(key);
		final JThreshold comp = new JThreshold(label, min, max, minorTick,
				majorTick);
		comp.setValue(value);
		comp.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				settings.put(key, comp.getValue());
			}
		});
		comp.setAlignmentX(Component.LEFT_ALIGNMENT);
		return comp;
	}

	public static JComboBox createStringCombo(String[] elements,
			final String key, final MethodSettings settings) {

		final JComboBox combo = new JComboBox(elements);
		String defaultValue = (String) settings.get(key);
		combo.setSelectedItem(defaultValue);

		combo.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				settings.put(key, combo.getSelectedItem());
			}

		});
		return combo;

	}

	public static JCheckBox createCheckBox(final String key, String label,
			final LexicalChainsMethodSettings settings) {

		final JCheckBox check = new JCheckBox(label);
		check.setSelected(Boolean.valueOf((String)settings.get(key)));
		
		check.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				settings.put(key, String.valueOf(check.isSelected()));
			}
			
		});
		
		return check;
	}

}
