package com.bjsasc.utils.dom;

import java.lang.reflect.Constructor;

import org.eclipse.core.internal.registry.osgi.OSGIUtils;
import org.osgi.framework.Bundle;
import org.w3c.dom.Element;

public class DefaultElementFactory implements IElementFactory {

	private String fPluginId;
	private String fPackageName;
	
	public DefaultElementFactory(String pluginId, String packageName) {
		fPluginId = pluginId;
		fPackageName = packageName;
	}
/*	
	@Override
	public ElementNode createElement(Manager manager, ElementNode parent, Element element) {
		// TODO Auto-generated method stub
		String className = fPackageName + "." + element.getNodeName();
		try {
			Bundle bundle = OSGIUtils.getDefault().getBundle(fPluginId);
			ElementNode node = (ElementNode) bundle.loadClass(className).newInstance();
			node.load(manager, parent, element);
			if(parent != null) {
				parent.addChild(node);
			}
			
			return node;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
*/
	@Override
	public ElementNode createElement(Manager manager, ElementNode parent, Element element) {
		// TODO Auto-generated method stub
		String className = fPackageName + "." + element.getNodeName();
		try {
			Bundle bundle = OSGIUtils.getDefault().getBundle(fPluginId);
			Class<?> theClass = bundle.loadClass(className);
			Constructor<?> c = theClass.getConstructor(
					new Class<?>[] { 
							Manager.class, 
							ElementNode.class, 
							Element.class 
					}
			);
			
			if (c == null) {
				return null;
			}

			return (ElementNode) c.newInstance(manager, parent, element);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
