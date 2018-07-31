package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.external;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.external.gui.SzmonLaunchOptions;

public class SzmonOptions extends Options
{
  private SzmonLaunchOptions szmon;

  public SzmonOptions()
  {
    super("com.gaisler.eclipse.plugins.cdt.singlelaunch.PROGRAM_LOCATION_SZMON", "szmon-eval");
  }

  protected void createArgumentComponent(Composite parent)
  {
    this.szmon = new SzmonLaunchOptions(parent, 0);
    this.szmon.setDefaults();
    this.szmon.addUpdateListener(new Listener(){
    	public void handleEvent(Event event)
    	  {
    		szmon.update();
//    	    SzmonOptions.access$0(this.this$0, true);
//    	    SzmonOptions.access$1(this.this$0);
    	  }
    });

    GridData gridData = new GridData(4, 128, true, false);
    this.szmon.setLayoutData(gridData);
  }

  public String getName()
  {
    return "SZMON options";
  }

  public void initializeFrom(ILaunchConfiguration configuration)
  {
    super.initializeFrom(configuration);
    try
    {
      String arguments = configuration.getAttribute(this.fArgname, "");
      this.szmon.parseCommandLine(arguments);
    }
    catch (CoreException localCoreException)
    {
    }
  }

  public void performApply(ILaunchConfigurationWorkingCopy configuration)
  {
	  String temp = null;
    super.performApply(configuration);
    String arguments = this.szmon.getCommandLineOptions();
    if (arguments.length() == 0)
      configuration.setAttribute(this.fArgname, temp);
    else
      configuration.setAttribute(this.fArgname, arguments);
  }
}
