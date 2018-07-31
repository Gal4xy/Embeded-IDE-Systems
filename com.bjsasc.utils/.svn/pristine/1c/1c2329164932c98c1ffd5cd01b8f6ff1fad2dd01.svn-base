package com.bjsasc.utils.extensions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;

public class ConfigurationElement {

	private IConfigurationElement fConfElement;
	private String fName;
	private Map<String,String> attributes = new HashMap<String,String>();
	private ConfigurationElement parent;
	private List<ConfigurationElement> children = new ArrayList<ConfigurationElement>();

	public ConfigurationElement(String name, IConfigurationElement ce, ConfigurationElement parent) {
		fName = name;
		this.fConfElement = ce;
		this.parent = parent;
	}

	public IConfigurationElement getConfElement() {
		return fConfElement;
	}
	
	public String getName() {
		return fName;
	}
	
	public String getAttribute(String key) {
		return attributes.get(key);
	}
	
	public void setAttribute(String key, String value) {
		attributes.put(key, value);
	}

	public ConfigurationElement getParent() {
		return parent;
	}

	public ConfigurationElement[] getChildren() {
		return children.toArray(new ConfigurationElement[children.size()]);
	}

	public void addChild(ConfigurationElement child) {
		children.add(child);
		child.setParent(this);
	}

	public void addChild(int index, ConfigurationElement child) {
		children.add(index, child);
		child.setParent(this);
	}

	public void addChildren(ConfigurationElement[] children) {
		for (ConfigurationElement ce : children) {
			this.children.add(ce);
			ce.setParent(this);
		}
	}
	
	public void removeChildren() {
		children.clear();
	}
	
	public ConfigurationElement clone(ConfigurationElement parent) {
		ConfigurationElement ce = new ConfigurationElement(fName, fConfElement, parent);
		for(String key : attributes.keySet()) {
			ce.setAttribute(key, attributes.get(key));
		}
		
		for(ConfigurationElement child : children) {
			ce.addChild(child.clone(ce));
		}

		return ce;
	}
	
	public void setParent(ConfigurationElement parent) {
		this.parent = parent;
	}
	
	public static void buildConfElementTree(ConfigurationElement confElement,
			IConfigurationElement[] elements) {
		if (elements == null || elements.length <= 0) {
			return;
		}

		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement ce = elements[i];
			ConfigurationElement child = new ConfigurationElement(ce.getName(), ce, confElement);
			String[] names = ce.getAttributeNames();
			for (String key : names) {
				child.setAttribute(key, ce.getAttribute(key));
			}

			confElement.addChild(child);
			IConfigurationElement[] ces = ce.getChildren();
			buildConfElementTree(child, ces);
		}
	}
}
