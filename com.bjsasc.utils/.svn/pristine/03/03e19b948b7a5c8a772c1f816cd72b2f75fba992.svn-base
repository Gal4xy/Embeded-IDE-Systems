package com.bjsasc.utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * ÊôÐÔ¼àÌý½Ó¿Ú
 * @author Administrator
 *
 */
public abstract class AbstractModel {
	private PropertyChangeSupport listeners = new PropertyChangeSupport(
			this);

	public void addView(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	public void firePropertyChange(String property, Object oldValue, Object newValue) {
		listeners.firePropertyChange(property, oldValue, newValue);
	}

	public void removeView(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}
	public int viewCount()
	{
		return listeners.getPropertyChangeListeners().length;
	}


}
