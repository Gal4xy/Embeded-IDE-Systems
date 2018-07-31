package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.sparcelf;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.mi.core.IGDBServerMILaunchConfigurationConstants;
import org.eclipse.cdt.debug.mi.core.IMILaunchConfigurationConstants;
import org.eclipse.cdt.launch.ui.CDebuggerTab;
import org.eclipse.cdt.managedbuilder.core.IManagedBuildInfo;
import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.widgets.Composite;



public class SparcCDebuggerTab extends CDebuggerTab {

	public SparcCDebuggerTab(boolean attachMode) {
		super(attachMode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
		super.setDefaults(config);
		
		if (fAttachMode) {
			config.setAttribute(
					ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_START_MODE,
					ICDTLaunchConfigurationConstants.DEBUGGER_MODE_ATTACH);
		} else {
			config.setAttribute(
					ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_START_MODE,
					ICDTLaunchConfigurationConstants.DEBUGGER_MODE_RUN);
			config
					.setAttribute(
							ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN,
							LaunchConstants.BREAK_AT_STARTUP_DEFAULT);
		}
		config
				.setAttribute(
						ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ENABLE_VARIABLE_BOOKKEEPING,
						false);
		config
				.setAttribute(
						ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ENABLE_REGISTER_BOOKKEEPING,
						false);

		String symbol = null;
		String gdbCommand = null;
		symbol = "main";
		gdbCommand = "sparc-elf-gdb";

		config
				.setAttribute(
						ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN_SYMBOL,
						symbol);
		config.setAttribute(IMILaunchConfigurationConstants.ATTR_DEBUG_NAME,
				gdbCommand);
		config.setAttribute(IMILaunchConfigurationConstants.ATTR_GDB_INIT, "");
		config.setAttribute(
				IGDBServerMILaunchConfigurationConstants.ATTR_REMOTE_TCP, true);
		config.setAttribute(IGDBServerMILaunchConfigurationConstants.ATTR_HOST,
				"localhost");
		config.setAttribute(IGDBServerMILaunchConfigurationConstants.ATTR_PORT,
				"2222");
		config.setAttribute(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ID,
				"com.bjsasc.gdbebugger.sparcelf");
		config.setAttribute(LaunchConstants.ATTR_SZMON_PROGRAM_ARGUMENTS_DEVICE, "com1");
		config.setAttribute(LaunchConstants.ATTR_SZMON_PROGRAM_ARGUMENTS_BAUDRATE, "115200");
		config.setAttribute(LaunchConstants.ATTR_SZMON_PROGRAM_ARGUMENTS_PORTID, "2222");
	}

	public static String getConfigId(ILaunchConfiguration config) {
		String configId = null;
		try {
			String projectName = config.getAttribute(
					ICDTLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
			if (projectName.length() > 0) {
				IProject project = ResourcesPlugin.getWorkspace().getRoot()
						.getProject(projectName);
				IManagedBuildInfo info = ManagedBuildManager
						.getBuildInfo(project);
				configId = info.getDefaultConfiguration().getId();
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return configId;
	}

}
