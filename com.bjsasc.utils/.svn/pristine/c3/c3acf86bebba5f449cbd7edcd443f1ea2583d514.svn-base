package com.bjsasc.utils;

import java.io.File;
import java.net.MalformedURLException;

import org.eclipse.jface.resource.ImageDescriptor;

public class ImageUtil {

	public static ImageDescriptor getImageDescriptor(String path) {
		try {
			return ImageDescriptor.createFromURL(new File(path).toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
