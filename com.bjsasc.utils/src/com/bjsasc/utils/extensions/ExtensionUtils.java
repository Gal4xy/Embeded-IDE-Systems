package com.bjsasc.utils.extensions;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;


/**
 * @author maobaolong
 * ��ȡ��չ�㣬��ȡ��FlashWriter��������
 *
 */
public class ExtensionUtils {
	/**
	 * ����չ����ȡ������Ԫ��
	 * @return ����Ԫ��
	 */
	public static ConfigurationElement getElementFromExtension(String pluginId,String extensionId){
		ConfigurationElement flashwriterConfElementRoot = new ConfigurationElement(extensionId, null, null);
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry
				.getConfigurationElementsFor(
						pluginId, extensionId);
		if (elements == null || elements.length <= 0) {
			return null;
		}
		
		ConfigurationElement.buildConfElementTree(flashwriterConfElementRoot, elements);
		
		return flashwriterConfElementRoot;
	}
}
