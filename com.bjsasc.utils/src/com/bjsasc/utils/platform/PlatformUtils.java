package com.bjsasc.utils.platform;

import org.eclipse.ui.IFileEditorMapping;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.registry.EditorRegistry;
import org.eclipse.ui.internal.registry.FileEditorMapping;

/**
 * @author maobaolong
 * 平台、工作台、相关的函数
 *
 */
public class PlatformUtils {
	/**
	 * @param extension 后缀
	 * 把指定后缀加入到工作台的编辑器注册表中
	 */
	public static void addExtensionToEditorRegistryMapping(String extension){
		EditorRegistry er=	(EditorRegistry) PlatformUI.getWorkbench().getEditorRegistry();
		IFileEditorMapping[] oldFeMappings = er.getFileEditorMappings();
		FileEditorMapping[] newFeMappings = new FileEditorMapping[oldFeMappings.length+1];
		for(int i=0;i<oldFeMappings.length;i++){
			if(oldFeMappings[i].getExtension().equals(extension)){
				return;
			}
			newFeMappings[i] =(FileEditorMapping) oldFeMappings[i];
		}
		FileEditorMapping feMapping = new FileEditorMapping("*",extension);
		newFeMappings[oldFeMappings.length]=feMapping;
		er.setFileEditorMappings(newFeMappings);
	}
}
