package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages
{
  private static final String BUNDLE_NAME = "com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.Messages";
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.Messages");

  public static String getString(String key)
  {
    try
    {
      return RESOURCE_BUNDLE.getString(key);
    } catch (MissingResourceException localMissingResourceException) {
    }
    return '!' + key + '!';
  }
}