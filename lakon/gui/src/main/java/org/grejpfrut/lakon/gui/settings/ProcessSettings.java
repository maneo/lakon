
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

package org.grejpfrut.lakon.gui.settings;

import java.awt.Frame;

import javax.swing.JComponent;

import org.grejpfrut.lakon.summarizer.settings.Settings;

/**
 * Classes implementing this interface manage settings for a given process
 * (identified by a process identifier).
 * 
 * @author Dawid Weiss
 * @author Adam Dudczak - lakon adaptation.
 */
public interface ProcessSettings {
    
    /**
     * Should return <code>true</code> if this components has
     * any settings and visual space should be provided for its
     * settings dialog etc.
     */
    public boolean hasSettings();

    /**
     * Returns a GUI component which displays process settings
     * and lets the user play with them interactively.
     * @param owner TODO
     */
    public JComponent getSettingsComponent(Frame owner);

    /**
     * Should return <code>true</code> if this component is configured
     * to handle a query request. Some processes might require some
     * configuration prior to executing a query (i.e. pointing at an
     * index location and such).
     */
    public boolean isConfigured();

    /** Returns current request parameters. */
    public Settings getRequestParams();

    /** 
     * Create a clone of yourself (and default settings) for use in a query 
     * and for subsequent local changes in the query tab. 
     */
    public ProcessSettings createClone();

    /**
     * Adds a change listener to this process settings.
     */
    public void addListener(ProcessSettingsListener listener);
    
    public void setLiveUpdate(boolean liveUpdate);
    
    public boolean isLiveUpdate();
    
    public void dispose();
}
