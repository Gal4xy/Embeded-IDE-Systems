package com.bjsasc.gdbdebugger.eclipse.plugins;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class ColorManager {
	private static ColorManager gfColorManager;
	protected Map fColorTable = new HashMap(10);

	public static ColorManager getDefault() {
		if (gfColorManager == null) {
			gfColorManager = new ColorManager();
		}
		return gfColorManager;
	}

	public Color getColor(RGB rgb) {
		Color color = (Color) getColorTable().get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			getColorTable().put(rgb, color);
		}
		return color;
	}

	public void dispose() {
		Iterator e = getColorTable().values().iterator();
		while (e.hasNext())
			((Color) e.next()).dispose();
	}

	private Map getColorTable() {
		return this.fColorTable;
	}
}
