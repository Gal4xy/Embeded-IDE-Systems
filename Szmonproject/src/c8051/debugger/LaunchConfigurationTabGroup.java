package c8051.debugger;

import org.eclipse.cdt.launch.ui.CArgumentsTab;
import org.eclipse.cdt.launch.ui.CMainTab;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;
import c8051.debugger.GdbLaunchConfigurationTab;;


public class LaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup{
	
	public void createTabs(ILaunchConfigurationDialog dialog, String mode)
	  {
	    ILaunchConfigurationTab[] tabs = { 
	      new CMainTab(2), 
	      //new SzmonLaunchConfigurationTab(), 
	      new GdbLaunchConfigurationTab(false), 
	     // new SparcCDebuggerTab(false),
	      new CArgumentsTab(), 
	      new EnvironmentTab(), 
	      new SourceLookupTab(),
	      new CommonTab() 
	    };
	     
	    setTabs(tabs);
	  }

}

