package com.bjsasc.utils.select;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
/**
 * @author song 文件操作
 * 
 */
public class CFileFinder {
	List<IFile> clist = new ArrayList<IFile>();
	/**
	 * @author song 
	 * 功能： 筛选选中的.c或.h文件
	 * 参数：  resource为选中的资源
	 * 返回： 选中.c或.h文件的list
	 */
	public List<IFile> getCFile(IResource resource[]) {
		for (int i = 0; i < resource.length; i++) {
			if (resource[i] != null && resource[i] instanceof IFile) {
				if (resource[i].getName().endsWith(".c")
						|| resource[i].getName().endsWith(".C"))
					;
				clist.add((IFile) resource[i]);
			}
			if (resource[i] != null && resource[i] instanceof IFolder)
				CFileFinder_Folder((IFolder) resource[i]);
			if (resource[i] != null && resource[i] instanceof IProject)
				CFileFinder_Folder((IProject) resource[i]);

		}
		return clist;
	}
	/**
	 * @author song 
	 * 功能： 筛选选中的.c或.h文件
	 * 参数：  folder为文件夹
	 */
	private void CFileFinder_Folder(IFolder folder) {
		try {
			IResource res[] = folder.members();
			for (int i = 0; i < res.length; i++) {
				if (res[i] != null && res[i] instanceof IFile) {
					if (res[i].getName().endsWith(".c")
							|| res[i].getName().endsWith(".C"))
						clist.add((IFile) res[i]);

				} else if (res[i] != null && res[i] instanceof IFolder) {
					CFileFinder_Folder((IFolder) res[i]);
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @author song 
	 * 功能： 筛选选中的.c或.h文件
	 * 参数：  folder为工程
	 */
	private void CFileFinder_Folder(IProject folder) {
		try {
			IResource res[] = folder.members();
			for (int i = 0; i < res.length; i++) {
				if (res[i] != null && res[i] instanceof IFile) {
					if (res[i].getName().endsWith(".c")
							|| res[i].getName().endsWith(".C"))
						clist.add((IFile) res[i]);

				} else if (res[i] != null && res[i] instanceof IFolder) {
					CFileFinder_Folder((IFolder) res[i]);
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
}
