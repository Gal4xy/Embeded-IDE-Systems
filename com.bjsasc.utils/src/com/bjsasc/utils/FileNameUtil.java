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
		//��������IPath�������еķ�������ȡ�ļ�����
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
	 *            :Դ··�����Ը�·��Ϊ����Ѱ��Ŀ��·��targetPath
	 * @param targetPath
	 *            :Ŀ��·������Դ·��sourcePathΪ����Ѱ�ұ�·�� sourcePath��targetPath�����ǺϷ�·��
	 * @return :Ŀ��·�������Դ·�����ַ�����·��ֵ,����·����ȫ��ͬʱ����""������·���Ƿ����߲��������·����ϵ�򷵻�null
	 * @author lichengfei 2012-07-04
	 * */
	public static String findRelativePath(String sourcePath, String targetPath) {
		// ·������Ϊnull
		if (sourcePath == null || targetPath == null)
			return null;
		// ȫ�����Сд��ĸ
		sourcePath = sourcePath.trim().toLowerCase();
		targetPath = targetPath.trim().toLowerCase();
		// ·�����ݲ���Ϊ��
		if ("".equals(sourcePath) || "".equals(targetPath))
			return null;

		if (!(sourcePath.contains(":") && targetPath.contains(":")))
			return null;
		// �ֱ��ȡ����·�����̷�����ͬ������Ƚϣ����򷵻�null
		String[] sourceDiskSegs = sourcePath.split(":");
		String[] targetDiskSegs = targetPath.split(":");
		if (sourceDiskSegs!=null&&targetDiskSegs!=null&&sourceDiskSegs[0].equals(targetDiskSegs[0])
				&& sourceDiskSegs[0].matches("[a-z]"))// ���׸�֤�̷���ͬ
		{
			// б�ܷ��ͳһ��\

			sourcePath = sourcePath.replace("/", "\\");
			targetPath = targetPath.replace("/", "\\");
			// �����б���滻��һ��б��
			while (sourcePath.contains("\\\\"))
				sourcePath = sourcePath.replace("\\\\", "\\");
			while (targetPath.contains("\\\\"))
				targetPath = targetPath.replace("\\\\", "\\");
			// ���·����ͬ������""
			if (sourcePath.equals(targetPath)) {
				return "";
			}
			// ȥ��·����󲿷ֵķ�б��
			if (sourcePath.endsWith("\\")) {
				int index = sourcePath.lastIndexOf("\\");
				sourcePath = sourcePath.substring(0, index);
			}
			if (targetPath.endsWith("\\")) {
				int index = targetPath.lastIndexOf("\\");
				targetPath = targetPath.substring(0, index);
			}
			// ��б�ָܷ�·���ַ���
			String[] sourcePathSplit = sourcePath.split("\\\\");
			String[] targetPathSplit = targetPath.split("\\\\");
			int same = 0;// ��¼��ͬ���ݵ��յ�
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
			 * ���ݵ�targetPath��ڴ� { resultPath = resultPath + "..\\"; } for (int k
			 * = 0; k < targetPathSplit.length - same; k++)// ����targetPathĿ¼ {
			 * if (k < targetPathSplit.length - same - 1) resultPath =
			 * resultPath + targetPathSplit[same + k] + "\\"; else if (k ==
			 * targetPathSplit.length - same - 1) resultPath = resultPath +
			 * targetPathSplit[same + k]; }
			 */
			// Begin:modified by lichengfei 2012-08-21 ʹ���Դ��ĺ����������·��
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
	 *            :Դ··�����Ը�·��Ϊ����Ѱ��Ŀ��·��targetPath
	 * @param targetPath
	 *            :Ŀ��·������Դ·��sourcePathΪ����Ѱ�ұ�·�� sourcePath��targetPath�����ǺϷ�·��
	 * @return :Ŀ��·�������Դ·�����ַ�����·��ֵ,����·����ȫ��ͬʱ����""������·���Ƿ����߲��������·����ϵ�򷵻�null
	 * @author lichengfei 2012-07-04
	 * */
	public static String findRelativePathEasy(String sourcePath,
			String targetPath) {
		// ·������Ϊnull
		if (sourcePath == null || targetPath == null)
			return null;
		// ȫ�����Сд��ĸ 
		sourcePath = sourcePath.trim().toLowerCase();
		targetPath = targetPath.trim().toLowerCase();
		Path sourcePathFile = new Path(sourcePath);
		Path targetPathFile = new Path(targetPath);
		if (sourcePathFile.getDevice()!=null&&sourcePathFile.getDevice().equals(targetPathFile.getDevice())&& targetPathFile.getDevice().matches("[a-z]:"))
		// ���׸�֤�̷���ͬ
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
