package com.bjsasc.utils.extensions;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;


/**
 * @author maobaolong
 * 获取扩展点，并取得FlashWriter对象链表
 *
 */
public class ExtensionUtils {
	/**
	 * 从扩展点中取得配置元素
	 * @return 配置元素
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
