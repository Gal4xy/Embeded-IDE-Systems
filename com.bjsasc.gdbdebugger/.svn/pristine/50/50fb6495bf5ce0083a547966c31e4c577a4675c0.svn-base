package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.external.gui;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public abstract class LaunchOptions extends Composite
{
  public static final int TSIM = 0;
  public static final int SZMON = 1;
  protected Set updateListeners;
  protected LaunchOptions.UpdateEventSender listener;

  class UpdateEventSender
  implements ModifyListener, SelectionListener, Listener
{
  UpdateEventSender(LaunchOptions paramLaunchOptions)
  {
  }

  public void modifyText(ModifyEvent e)
  {
    sendUpdateEvent(new Event());
  }

  public void widgetDefaultSelected(SelectionEvent e)
  {
  }

  public void widgetSelected(SelectionEvent e)
  {
    sendUpdateEvent(new Event());
  }

  public void handleEvent(Event event)
  {
    sendUpdateEvent(new Event());
  }
}
  
  LaunchOptions(Composite parent, int style)
  {
    super(parent, style);
    this.updateListeners = new HashSet();
    this.listener = new LaunchOptions.UpdateEventSender(this);
  }

  public abstract String getCommandLineOptions();

  public abstract String parseCommandLine(String paramString);

  public abstract void save(IDialogSettings paramIDialogSettings);

  public abstract void restore(IDialogSettings paramIDialogSettings);

  public abstract void setDefaults();

  public int targetType()
  {
    return -1;
  }

  public void addUpdateListener(Listener listener) {
    checkWidget();
    if (listener == null) SWT.error(4);
    this.updateListeners.add(listener);
  }

  public void removeUpdateListener(Listener listener) {
    checkWidget();
    if (listener == null) SWT.error(4);
    if (this.updateListeners == null) return;
    this.updateListeners.remove(listener);
  }

  public void sendUpdateEvent(Event e)
  {
    Iterator iter = this.updateListeners.iterator();
    while (iter.hasNext())
      ((Listener)iter.next()).handleEvent(e);
  }
}
