package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch;

import org.eclipse.cdt.launch.ui.CArgumentsTab;
import org.eclipse.cdt.launch.ui.CMainTab;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;

import com.bjsasc.gdbdebugger.eclipse.plugins.cdt.sparcelf.SparcCDebuggerTab;

public class LaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup{
	
	public void createTabs(ILaunchConfigurationDialog dialog, String mode)
	  {
	    ILaunchConfigurationTab[] tabs = { 
	      new CMainTab(2), 
	      new SzmonLaunchConfigurationTab(), 
	      new GdbLaunchConfigurationTab(false), 
	      new SparcCDebuggerTab(false),
	     // new CArgumentsTab(), 
	      //new EnvironmentTab(), 
	      
	      new SourceLookupTab()
	    };
	     // new CommonTab() 
	    //hidded by mbl at 20110812
	    setTabs(tabs);
	  }

}
