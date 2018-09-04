package runnotepad;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;

public class NpLaunchConfigurationDelegate implements ILaunchConfigurationDelegate{
      
	
	public void launch(ILaunchConfiguration config, String mode,ILaunch launch,
			IProgressMonitor monitor) throws CoreException {
		
		String locater="C:\\MinGW\\bin\\gdb.exe";
		System.out.println("Launch delegate is running");
		try {
		       IProcess p=DebugPlugin.newProcess(launch, 
		    		   Runtime.getRuntime().exec(locater), "Run an exe");	
		}
		
		catch(Exception e) {
			  e.printStackTrace();
		}
	}
     	
}
