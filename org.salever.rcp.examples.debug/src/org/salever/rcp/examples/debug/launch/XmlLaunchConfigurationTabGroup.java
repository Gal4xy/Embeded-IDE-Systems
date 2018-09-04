/*******************************************************************************
 * Copyright (c) 2000, 2010 salever. All rights reserved. 
 *
 *******************************************************************************/
package org.salever.rcp.examples.debug.launch;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

/**
 * @author salever
 *
 */
public class XmlLaunchConfigurationTabGroup extends
		AbstractLaunchConfigurationTabGroup {

	/**
	 * 
	 */
	public XmlLaunchConfigurationTabGroup() {
	}
	
	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {
				new XMLMainTab(),
				new CommonTab()
		};
		setTabs(tabs);


	}
}
