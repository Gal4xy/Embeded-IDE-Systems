package c8051.server;

/**
 * 
 * @author Galaxy
 * @date  2018/01/23
 */

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.RefreshTab;

import szmonproject.gdb.SparcCDebuggerTab;

public class ServerLaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup
{

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		// TODO Auto-generated method stub
		ILaunchConfigurationTab tabs[]= {
				new Options(),
				//new SzmonLaunchProgramTab(),
				//new SparcCDebuggerTab(false),
				new RefreshTab(),
				new EnvironmentTab(),
				new CommonTab()
		};
	
	       setTabs(tabs);
	}
	

	
}
