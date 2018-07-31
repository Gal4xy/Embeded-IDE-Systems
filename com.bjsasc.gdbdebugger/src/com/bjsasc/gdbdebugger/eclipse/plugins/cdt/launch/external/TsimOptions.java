package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.external;

import com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.external.gui.TsimLaunchOptions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class TsimOptions extends Options
{
  private TsimLaunchOptions tsim;

  public TsimOptions()
  {
    super("com.gaisler.eclipse.plugins.cdt.singlelaunch.PROGRAM_LOCATION_TSIM", "tsim-leon3");
  }

  public String getName()
  {
    return "TSIM options";
  }

  protected void createArgumentComponent(Composite parent)
  {
    this.tsim = new TsimLaunchOptions(parent, 0);
    this.tsim.addUpdateListener(new Listener(){
    	  public void handleEvent(Event event)
    	  {
    		  System.out.println("something wrong with access$0");
//    	    TsimOptions.access$0(this.this$0, true);
//    	    TsimOptions.access$1(this.this$0);
    	  }
    });

    GridData gridData = new GridData(4, 128, true, false);
    this.tsim.setLayoutData(gridData);
  }

  public void initializeFrom(ILaunchConfiguration configuration)
  {
    super.initializeFrom(configuration);
    try
    {
      String arguments = configuration.getAttribute(this.fArgname, "");
      this.tsim.parseCommandLine(arguments);
    }
    catch (CoreException localCoreException)
    {
    }
  }

  public void performApply(ILaunchConfigurationWorkingCopy configuration) {
    String temp = null;
	  super.performApply(configuration);
    String arguments = this.tsim.getCommandLineOptions();
    if (arguments.length() == 0)
      configuration.setAttribute(this.fArgname, temp);
    else
      configuration.setAttribute(this.fArgname, arguments);
  }

  public void setDefaults(ILaunchConfigurationWorkingCopy configuration)
  {
    super.setDefaults(configuration);
  }
}