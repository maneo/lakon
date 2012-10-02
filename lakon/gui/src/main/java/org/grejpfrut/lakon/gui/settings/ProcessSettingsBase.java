
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
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.JComponent;

import org.grejpfrut.lakon.summarizer.settings.Settings;


/**
 * Base class for process settings.
 *  
 * @author Stanislaw Osinski
 */
public abstract class ProcessSettingsBase implements ProcessSettings
{
    private boolean liveUpdate = true;

    protected Settings params;
    protected Vector listeners = new Vector();

    public boolean hasSettings() {
        return false;
    }

    public abstract JComponent getSettingsComponent(Frame owner);

    public abstract boolean isConfigured();

    public Settings getRequestParams() {
        synchronized (this) {
            return params;
        }
    }

    public abstract ProcessSettings createClone();

    protected void fireParamsUpdated() {
        for (Iterator i = listeners.iterator(); i.hasNext();)
        {
            ((ProcessSettingsListener) i.next()).settingsChanged(this.params);
        }
    }

    public void addListener(ProcessSettingsListener listener) {
        this.listeners.add(listener);
    }

    public void setRequestParams(Map<String,Object> params) {
        synchronized (this) {
            this.params.setMap(params);
            if (liveUpdate) {
                fireParamsUpdated();
            }
        }
    }

    public boolean isLiveUpdate() {
        return liveUpdate;
    }

    public void setLiveUpdate(boolean liveUpdate) {
        this.liveUpdate = liveUpdate;
        if (liveUpdate) {
            fireParamsUpdated();
        }
    }
    
	public void put(String key, Object value) {
		synchronized (this) {
			params.put(key, value);
			if (isLiveUpdate()) {
				fireParamsUpdated();
			}
		}
	}

	public Object get(String key) {
		synchronized (this) {
			return this.params.get(key);
		}
	}

    
    public void dispose()
    {
    }
}