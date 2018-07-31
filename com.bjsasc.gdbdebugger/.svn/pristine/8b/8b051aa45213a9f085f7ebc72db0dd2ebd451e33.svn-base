package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.sparcelf;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class SparcElfMessage
{
  private static final String BUNDLE_NAME = "com.bjsasc.gdbdebugger.eclipse.plugins.cdt.sparcelf.SparcElfMessage";
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("com.bjsasc.gdbdebugger.eclipse.plugins.cdt.sparcelf.SparcELFMessage");

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
