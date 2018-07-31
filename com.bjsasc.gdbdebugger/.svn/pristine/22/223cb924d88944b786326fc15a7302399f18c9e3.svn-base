package com.bjsasc.gdbdebugger.eclipse.plugins;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

public class FontManager {
	protected Map fFontTable = new HashMap(10);

	public Font getFont(String face, int height) {
		String search = face + height;
		Font f = (Font) this.fFontTable.get(search);
		if (f == null) {
			FontData fontData = new FontData(face, height, 0);

			Display display = Display.getCurrent();
			if (display == null) {
				display = Display.getDefault();
			}

			f = new Font(display, fontData);
		}
		return f;
	}
}
