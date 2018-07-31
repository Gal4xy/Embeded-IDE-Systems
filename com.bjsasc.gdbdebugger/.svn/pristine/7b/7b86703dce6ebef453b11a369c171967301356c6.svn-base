package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.eclipse.cdt.debug.core.CDebugCorePlugin;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.core.ICDebugConfiguration;
import org.eclipse.cdt.debug.mi.core.IGDBServerMILaunchConfigurationConstants;
import org.eclipse.cdt.debug.mi.core.IMILaunchConfigurationConstants;
import org.eclipse.cdt.launch.ui.CDebuggerTab;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;

import com.bjsasc.gdbdebugger.eclipse.plugins.cdt.sparcelf.SparcElfConstants;
import com.bjsasc.gdbdebugger.eclipse.plugins.cdt.sparcelf.SparcElfGDBDebugger;

public class GdbLaunchConfigurationTab extends CDebuggerTab {

	public GdbLaunchConfigurationTab(boolean attachMode) {
		super(attachMode);
	}

	protected void loadDebuggerComboBox(ILaunchConfiguration config,
			String selection) {
		ICDebugConfiguration[] debugConfigs = CDebugCorePlugin.getDefault()
				.getDebugConfigurations();
		Arrays.sort(debugConfigs, new Comparator() {
			public int compare(Object o1, Object o2) {
				ICDebugConfiguration ic1 = (ICDebugConfiguration) o1;
				ICDebugConfiguration ic2 = (ICDebugConfiguration) o2;
				return ic1.getName().compareTo(ic2.getName());
			}
		});

		List list = new ArrayList();
		String mode;
		if (this.fAttachMode)
			mode = "attach";
		else {
			mode = "run";
		}
		System.out.println("loadTab");
		String defaultSelection = SparcElfGDBDebugger.DEBUGGER_ID;
		for (int i = 0; i < debugConfigs.length; ++i) {
			if ((!debugConfigs[i].supportsMode(mode))
					|| (!validatePlatform(config, debugConfigs[i])))
				continue;
			String id = debugConfigs[i].getID();

			if (id.contains(SparcElfGDBDebugger.DEBUGGER_ID))
				list.add(debugConfigs[i]);
				
			

			// if (defaultSelection.equals("")) {
			// defaultSelection =
			// "com.bjsasc.gdbdebugger.eclipse.plugins.ctd.sparcelf.SparcElfGDBDebugger";
			// }

		}

		setInitializeDefault(selection.equals(""));
		loadDebuggerCombo((ICDebugConfiguration[]) list
				.toArray(new ICDebugConfiguration[list.size()]),
				selection);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
		super.setDefaults(config);
		config
				.setAttribute(
						ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ID,
						SparcElfGDBDebugger.DEBUGGER_ID);
		// if (fAttachMode) {
		// config.setAttribute(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_START_MODE,
		// ICDTLaunchConfigurationConstants.DEBUGGER_MODE_ATTACH);
		// } else {
		// config.setAttribute(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_START_MODE,
		// ICDTLaunchConfigurationConstants.DEBUGGER_MODE_RUN);
		// config.setAttribute(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN,
		// ICDTLaunchConfigurationConstants.DEBUGGER_STOP_AT_MAIN_DEFAULT);
		// }
		 config.setAttribute(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ENABLE_VARIABLE_BOOKKEEPING,
		 false);
		 config.setAttribute(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_ENABLE_REGISTER_BOOKKEEPING,
		 false);
		
		 String symbol = null;
		 String gdbCommand = SparcElfGDBDebugger.DEBUGGER_CMD;
				
		 config.setAttribute(ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN_SYMBOL,
		 symbol);
		 config.setAttribute(IMILaunchConfigurationConstants.ATTR_DEBUG_NAME,
		 gdbCommand);
		 config.setAttribute(SparcElfConstants.ATTR_DEBUG_NAME, gdbCommand);
		 config.setAttribute(IMILaunchConfigurationConstants.ATTR_GDB_INIT,
		 "");
		 config.setAttribute(IGDBServerMILaunchConfigurationConstants.ATTR_REMOTE_TCP,
		 true);
		 config.setAttribute(IGDBServerMILaunchConfigurationConstants.ATTR_HOST,
		 "localhost");
		 config.setAttribute(IGDBServerMILaunchConfigurationConstants.ATTR_PORT,
		 "2222");
	}

}
