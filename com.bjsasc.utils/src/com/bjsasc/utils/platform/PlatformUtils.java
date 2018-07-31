package com.bjsasc.utils.platform;

import org.eclipse.ui.IFileEditorMapping;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.registry.EditorRegistry;
import org.eclipse.ui.internal.registry.FileEditorMapping;

/**
 * @author maobaolong
 * ƽ̨������̨����صĺ���
 *
 */
public class PlatformUtils {
	/**
	 * @param extension ��׺
	 * ��ָ����׺���뵽����̨�ı༭��ע�����
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
