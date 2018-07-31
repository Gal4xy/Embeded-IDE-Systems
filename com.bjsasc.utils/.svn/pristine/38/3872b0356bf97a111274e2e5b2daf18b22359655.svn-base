package com.bjsasc.utils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public  class View implements PropertyChangeListener {
	private Model model;
	public void setModel(Model model) {
		this.model  = model;
		//added by maobaolong at 2012-3-30
		model.addListener(this);
	}
	public Model getModel() {
		return this.model;
	}
	public void remove() {
		if(model!=null)
			model.removeListener(this);
	}
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
