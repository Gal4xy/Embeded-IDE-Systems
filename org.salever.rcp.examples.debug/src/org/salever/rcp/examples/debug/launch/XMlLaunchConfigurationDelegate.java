/*******************************************************************************
 * Copyright (c) 2000, 2010 salever. All rights reserved. 
 *
 *******************************************************************************/
package org.salever.rcp.examples.debug.launch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;
import org.salever.rcp.examples.debug.DebugConstants;

/**
 * Using "Run"-->"Run Configurations"--> "New Configuration" -- > "Run" will lead here.
 * @author salever
 *
 */
public class XMlLaunchConfigurationDelegate implements
		ILaunchConfigurationDelegate {

	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.model.ILaunchConfigurationDelegate#launch(org.eclipse.debug.core.ILaunchConfiguration, java.lang.String, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		
		//Using configuration to build command line 
		List<String> cmdLine = new ArrayList<String>();
		cmdLine.add("C:\\windows\\NOTEPAD.EXE");//Application path should be stored in preference.
		String file = configuration.getAttribute(DebugConstants.XML_FILE, "");
		String filePath = ResourcesPlugin.getWorkspace().getRoot().findMember(file).getLocation().toOSString();
		cmdLine.add(filePath);//path is relative, so can not found it.
		String[] cmds = {};
		cmds = cmdLine.toArray(cmds);
		try {
			IProcess p = DebugPlugin.newProcess(
			        launch,
			        //Launch a process to debug.eg, launch NOTEPAD.EXE to show the XML file we choose.
			        Runtime.getRuntime().exec(cmds, null),
			        "Tomcat debug Process");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
