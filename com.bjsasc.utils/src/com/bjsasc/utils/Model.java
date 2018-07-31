package com.bjsasc.utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * ÊôÐÔ¼àÌý½Ó¿Ú
 * 
 * @author Administrator
 * 
 */
public class Model {
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	public void addListener(PropertyChangeListener listener) {
		PropertyChangeListener[] oldListeners = listeners
				.getPropertyChangeListeners();
		for (PropertyChangeListener l : oldListeners) {
			if (l == listener)
				return;
		}
		listeners.addPropertyChangeListener(listener);
	}

	public void firePropertyChange(String property, Object oldValue,
			Object newValue) {
		if (oldValue == newValue)
			return;
		listeners.firePropertyChange(property, oldValue, newValue);
	}

	public void removeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

	public int listenerCount() {
		return listeners.getPropertyChangeListeners().length;
	}

}
