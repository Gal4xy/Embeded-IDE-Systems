package com.bjsasc.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * 
 *
 */
public class FileNameUtil {
	public static String getPathName(String fileName) {
		fileName = fileName.replace("\\", "/");
		/*
		 * int n = fileName.lastIndexOf("\\"); if(n < 0) {
		 */
		int n = fileName.lastIndexOf("/");
		/* } */

		if (n < 0) {
			return null;
		}

		return fileName.substring(0, n);
	}

	public static String getFileName(String fileName) {
		//可以利用IPath里面已有的方法来获取文件名称
		fileName = fileName.replace("\\", "/");
		int n = fileName.lastIndexOf("\\");
		if (n < 0) {
			n = fileName.lastIndexOf("/");
		}

		if (n < 0) {
			return null;
		}

		return fileName.substring(n + 1);
	}

	public static String getFileNameWithoutExt(String fileName) {
		String fileNameWithExt = getFileName(fileName);
		String fileNameWithoutExt = fileNameWithExt;
		if (fileNameWithExt != null && fileNameWithExt.contains(".")) {
			fileNameWithoutExt = fileNameWithExt.substring(0,
					fileNameWithExt.lastIndexOf("."));
		}
		return fileNameWithoutExt;
	}

	/**
	 * @param sourcePath
	 *            :源路路径，以该路径为基础寻找目标路径targetPath
	 * @param targetPath
	 *            :目标路径，以源路径sourcePath为基础寻找本路径 sourcePath和targetPath必须是合法路径
	 * @return :目标路径相对与源路径的字符串型路径值,两个路径完全相同时返回""，输入路径非法或者不存在相对路径关系则返回null
	 * @author lichengfei 2012-07-04
	 * */
	public static String findRelativePath(String sourcePath, String targetPath) {
		// 路径不能为null
		if (sourcePath == null || targetPath == null)
			return null;
		// 全部变成小写字母
		sourcePath = sourcePath.trim().toLowerCase();
		targetPath = targetPath.trim().toLowerCase();
		// 路径内容不能为空
		if ("".equals(sourcePath) || "".equals(targetPath))
			return null;

		if (!(sourcePath.contains(":") && targetPath.contains(":")))
			return null;
		// 分别获取两个路径的盘符，相同则继续比较，否则返回null
		String[] sourceDiskSegs = sourcePath.split(":");
		String[] targetDiskSegs = targetPath.split(":");
		if (sourceDiskSegs!=null&&targetDiskSegs!=null&&sourceDiskSegs[0].equals(targetDiskSegs[0])
				&& sourceDiskSegs[0].matches("[a-z]"))// 保首个证盘符相同
		{
			// 斜杠风格统一成\

			sourcePath = sourcePath.replace("/", "\\");
			targetPath = targetPath.replace("/", "\\");
			// 将多个斜杠替换成一个斜杠
			while (sourcePath.contains("\\\\"))
				sourcePath = sourcePath.replace("\\\\", "\\");
			while (targetPath.contains("\\\\"))
				targetPath = targetPath.replace("\\\\", "\\");
			// 如果路径相同，返回""
			if (sourcePath.equals(targetPath)) {
				return "";
			}
			// 去掉路径最后部分的反斜杠
			if (sourcePath.endsWith("\\")) {
				int index = sourcePath.lastIndexOf("\\");
				sourcePath = sourcePath.substring(0, index);
			}
			if (targetPath.endsWith("\\")) {
				int index = targetPath.lastIndexOf("\\");
				targetPath = targetPath.substring(0, index);
			}
			// 以斜杠分割路径字符串
			String[] sourcePathSplit = sourcePath.split("\\\\");
			String[] targetPathSplit = targetPath.split("\\\\");
			int same = 0;// 记录相同内容的终点
			int minLength = sourcePathSplit.length > targetPathSplit.length ? targetPathSplit.length
					: sourcePathSplit.length;
			for (int i = 0; i < minLength; i++) {
				if (sourcePathSplit[i].equals(targetPathSplit[i])) {
					same++;
				} else
					break;
			}
			// Begin:delete by lichengfei at 2012-08-22
			/*
			 * if (same == 0) return null;
			 */
			// End:delete by lichengfei at 2012-08-22
			String resultPath = "";
			/*
			 * for (int j = 0; j < sourcePathSplit.length - same; j++)//
			 * 回溯到targetPath入口处 { resultPath = resultPath + "..\\"; } for (int k
			 * = 0; k < targetPathSplit.length - same; k++)// 进入targetPath目录 {
			 * if (k < targetPathSplit.length - same - 1) resultPath =
			 * resultPath + targetPathSplit[same + k] + "\\"; else if (k ==
			 * targetPathSplit.length - same - 1) resultPath = resultPath +
			 * targetPathSplit[same + k]; }
			 */
			// Begin:modified by lichengfei 2012-08-21 使用自带的函数计算相对路径
			Path sourcePathFile = new Path(sourcePath);
			Path targetPathFile = new Path(targetPath);
			IPath c = targetPathFile.makeRelativeTo(sourcePathFile);
			resultPath = c.toString();
			// End:modified by lichengfei 2012-08-21
			return resultPath;

		} else
			return null;
	}

	/**
	 * @param sourcePath
	 *            :源路路径，以该路径为基础寻找目标路径targetPath
	 * @param targetPath
	 *            :目标路径，以源路径sourcePath为基础寻找本路径 sourcePath和targetPath必须是合法路径
	 * @return :目标路径相对与源路径的字符串型路径值,两个路径完全相同时返回""，输入路径非法或者不存在相对路径关系则返回null
	 * @author lichengfei 2012-07-04
	 * */
	public static String findRelativePathEasy(String sourcePath,
			String targetPath) {
		// 路径不能为null
		if (sourcePath == null || targetPath == null)
			return null;
		// 全部变成小写字母 
		sourcePath = sourcePath.trim().toLowerCase();
		targetPath = targetPath.trim().toLowerCase();
		Path sourcePathFile = new Path(sourcePath);
		Path targetPathFile = new Path(targetPath);
		if (sourcePathFile.getDevice()!=null&&sourcePathFile.getDevice().equals(targetPathFile.getDevice())&& targetPathFile.getDevice().matches("[a-z]:"))
		// 保首个证盘符相同
		{
			String resultPath = "";
			IPath relativePath = targetPathFile.makeRelativeTo(sourcePathFile);
			resultPath = relativePath.toString();
			return resultPath;
		} else
			return null;
	}

	public static IFile getIFileFromPath(String filePath) {
		String sourceFileFullPath = null;
		IFile file = null;

		if (!filePath.startsWith("file:")) {
			filePath = "file:" + filePath;
		}
		java.net.URL url = null;
		try {
			url = new URL(filePath);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return file;
		}
		if (url.getProtocol().equals("file")) {
			sourceFileFullPath = url.getFile();
			if (sourceFileFullPath != null) {
				file = ResourcesPlugin
						.getWorkspace()
						.getRoot()
						.getFileForLocation(
								new org.eclipse.core.runtime.Path(
										sourceFileFullPath));
			}
		}

		return file;
	}

	public static void main(String[] args) {
		System.out.println(findRelativePath("e:\\54545\\4545",
				"e:\\54545\\4545\\43 434"));
	}
}
