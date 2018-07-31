package com.bjsasc.gdbdebugger.eclipse.plugins;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class PluginsPlugin extends AbstractUIPlugin{
	
	private static PluginsPlugin plugin;
	  private FontManager fFontManager = new FontManager();
	  private ColorManager fColorManager = new ColorManager();
	  public static final String PLUGIN_ID = "com.bjsasc.gdbdebugger.eclipse.plugins";
	  SyncList fDetectList = new SyncList();

	  public PluginsPlugin()
	  {
	    plugin = this;
	  }

	  public static String getUniqueIdentifier()
	  {
	    if (getDefault() == null) {
	      return "com.bjsasc.gdbdebugger.eclipse.plugins";
	    }
	    return getDefault().getBundle().getSymbolicName();
	  }

	  public void start(BundleContext context)
	    throws Exception
	  {
	    super.start(context);
	  }

	  public void stop(BundleContext context)
	    throws Exception
	  {
	    super.stop(context);
	    plugin = null;
	  }

	  public static PluginsPlugin getDefault()
	  {
	    return plugin;
	  }

	  public static IStatus newErrorStatus(String message, Throwable exception)
	  {
	    if (message == null) {
	      message = "";
	    }
	    return new Status(4, "com.bjsasc.gdbdebugger.eclipse.plugins", 0, message, exception);
	  }

	  public void log(String message, Throwable exception)
	  {
	    IStatus status = newErrorStatus(message, exception);
	    getLog().log(status);
	  }

	  public static ImageDescriptor getImageDescriptor(String path)
	  {
	    return AbstractUIPlugin.imageDescriptorFromPlugin("com.bjsasc.gdbdebugger", path);
	  }

	  public Font getFont(String face, int height) {
	    return this.fFontManager.getFont(face, height);
	  }
	  public Color getColor(RGB color) {
	    return this.fColorManager.getColor(color);
	  }

	  public void launchDetectStart()
	  {
	    this.fDetectList.clearItems();
	  }
	  public void launchDetected(Object e) {
	    this.fDetectList.addItem(e);
	  }
	  public Object launchDetect(int timeout) throws InterruptedException {
	    Object e = this.fDetectList.removeItem(timeout);
	    return e;
	  }

}
