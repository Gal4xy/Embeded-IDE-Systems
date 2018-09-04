/*******************************************************************************
 * Copyright (c) 2000, 2010 salever. All rights reserved. 
 *
 *******************************************************************************/
package org.salever.rcp.examples.debug.launch;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.salever.rcp.examples.debug.DebugConstants;

/**
 * Using "Run As" --> "Launch XML debug" will lead here.
 * @author salever
 *
 */
public class XmlLaunchShortcut implements ILaunchShortcut {

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchShortcut#launch(org.eclipse.jface.viewers.ISelection, java.lang.String)
	 */
	@Override
	public void launch(ISelection selection, String mode) {
		Object selectObj = ((IStructuredSelection) selection).getFirstElement();
		if (selectObj instanceof IFile) {
			launchFile((IFile) selectObj, mode);
		}
		else if(selectObj instanceof IProject){
			MessageDialog.openWarning(null, "Warning", "Not implemeneted yet!");
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.debug.ui.ILaunchShortcut#launch(org.eclipse.ui.IEditorPart, java.lang.String)
	 */
	@Override
	public void launch(IEditorPart editor, String mode) {
		//empty
	}
	
	/**
	 * Launch an XML file,using the file information, which means using default launch configurations.
	 * 
	 * @param file
	 * @param mode
	 */
	private void launchFile(IFile file, String mode) {
		
		// check for an existing launch config for the js file
		String path = file.getFullPath().toString();
		ILaunchManager launchManager = DebugPlugin.getDefault()
				.getLaunchManager();
		ILaunchConfigurationType type = launchManager
				.getLaunchConfigurationType(DebugConstants.LAUNCH_CONFIGURATION_TYPE);
	
		try {
			ILaunchConfiguration configuration = createLaunchConfiguration(
					type, path, file);
			DebugUITools.launch(configuration, mode);
		} catch (CoreException e1) {
		}
	}

	/**
	 * Create a new configuration and set useful data.
	 * 
	 * @param type
	 * @param path
	 * @param file
	 * @return
	 * @throws CoreException
	 */
	private ILaunchConfiguration createLaunchConfiguration(
			ILaunchConfigurationType type, String path, IFile file)
			throws CoreException {
		// create a new configuration for the js file
		ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(null,
				file.getName());
		workingCopy.setAttribute(DebugConstants.XML_FILE, path);
		workingCopy.setAttribute(DebugConstants._TYPE,
				"");
		workingCopy.setMappedResources(new IResource[] { file });
		return workingCopy.doSave();
	}
}
