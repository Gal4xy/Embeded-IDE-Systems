package com.bjsasc.utils.misc;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public class PropertySourceUtil {

	public static boolean isValidPropertyDescriptorId(IPropertySource src, Object id) {
		for(IPropertyDescriptor pd : src.getPropertyDescriptors()) {
			if(id.equals(pd.getId())) {
				return true;
			}
		}
		
		return false;
	}
	
}
