package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.external;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.RefreshTab;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.externaltools.internal.launchConfigurations.ExternalToolsLaunchConfigurationMessages;
import org.eclipse.ui.externaltools.internal.launchConfigurations.ExternalToolsUtil;
import org.eclipse.ui.externaltools.internal.program.launchConfigurations.ExternalToolsProgramMessages;

public class SzmonLaunchConfigurationDelegate implements
		ILaunchConfigurationDelegate {
	private  IWindowListener windowListener;//modified by lichengfei 2012-08-30,delete static
	String fAttrProg = "";
	String fAttrWorkspace = "";
	String fAttrArguments = "";

	class ProgramLaunchWindowListener implements IWindowListener {
		private ProgramLaunchWindowListener(
				SzmonLaunchConfigurationDelegate paramSzmonLaunchConfigurationDelegate) {
		}

		@Override
		public void windowActivated(IWorkbenchWindow window) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosed(IWorkbenchWindow window) {
			// TODO Auto-generated method stub
			IWorkbenchWindow[] windows = PlatformUI.getWorkbench()
					.getWorkbenchWindows();
			if (windows.length > 1) {
				return;
			}
			ILaunchManager manager = DebugPlugin.getDefault()
					.getLaunchManager();
			ILaunchConfigurationType programType = manager
					.getLaunchConfigurationType("com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.external.LaunchConfigurationType");
			if (programType == null) {
				return;
			}
			ILaunch[] launches = manager.getLaunches();

			for (int i = 0; i < launches.length; ++i) {
				ILaunchConfigurationType configType = null;
				try {
					ILaunchConfiguration config = launches[i]
							.getLaunchConfiguration();
					if (config == null) {
						break;
					}
					configType = config.getType();
				} catch (CoreException localCoreException) {
					break;
				}
				ILaunchConfiguration config;
				if ((!configType.equals(programType))
						|| (launches[i].isTerminated()))
					continue;
				MessageDialog
						.openWarning(
								window.getShell(),
								ExternalToolsProgramMessages.ProgramLaunchDelegate_Workbench_Closing_1,
								ExternalToolsProgramMessages.ProgramLaunchDelegate_The_workbench_is_exiting);

				try {
					launches[i].terminate();
				} catch (DebugException localDebugException) {
				}
			}
		}

		@Override
		public void windowDeactivated(IWorkbenchWindow window) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowOpened(IWorkbenchWindow window) {
			// TODO Auto-generated method stub

		}
	}

	Process createProcess(String[] cmdLine, File workingDirectory,
			String[] envp, IProgressMonitor monitor) throws IOException,
			CoreException {
		String cmd = cmdLine[0];
		for (int i = 1; i < cmdLine.length; ++i) {
			cmd = cmd + " " + cmdLine[i];
		}

		Process p2 = null;
		if (Platform.getOS().compareTo("win32") == 0) {
			p2 = SzmonLaunchProcessFactory.getFactory().exec(cmdLine, envp,
					workingDirectory);
		} else
			p2 = DebugPlugin.exec(cmdLine, workingDirectory, envp);

		return p2;
	}

	public static boolean isRunning(String name) {
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType programType = manager
				.getLaunchConfigurationType("com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.external.LaunchConfigurationType");
		if (programType == null) {
			return false;
		}
		ILaunch[] launches = manager.getLaunches();

		for (int i = 0; i < launches.length; ++i) {
			String configName = null;
			try {
				ILaunchConfiguration config = launches[i]
						.getLaunchConfiguration();
				if (config == null) {
					break;
				}
				ILaunchConfigurationType configType = config.getType();
				configName = config.getName();
			} catch (CoreException localCoreException) {
				break;
			}
			ILaunchConfiguration config;
			ILaunchConfigurationType configType = null;
			if (configType!=null//added by lichengfei 2012-08-27  configType!=null
					&&(configType.equals(programType))
					&& (configName.compareTo(name) == 0)
					&& (!launches[i].isTerminated())) {
				return true;
			}
		}

		return false;
	}

	public static int getPort(String name) throws CoreException {
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType programType = manager
				.getLaunchConfigurationType("com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.external.LaunchConfigurationType");
		if (programType == null) {
			return -1;
		}

		ILaunchConfiguration[] launch = manager
				.getLaunchConfigurations(programType);
		for (int i = 0; i < launch.length; ++i) {
			ILaunchConfiguration l = launch[i];
			String n = l.getName();
			if (n.compareTo(name) == 0) {
				String type = l
						.getAttribute(
								"com.bjsasc.gdbdebugger.eclipse.plugins.cdt.singlelaunch.PROGRAM_TYPE",
								"");

				String[] args = new String[0];
				int port = 1234;
				if (type.compareTo("SZMON") == 0) {
					args = getArguments(l,
							"com.bjsasc.gdbdebugger.eclipse.plugins.cdt.singlelaunch.PROGRAM_LOCATION_SZMON.arguments");
					port = 2222;
				} else if (type.compareTo("TSIM2") == 0) {
					args = getArguments(l,
							"com.bjsasc.gdbdebugger.eclipse.plugins.cdt.singlelaunch.PROGRAM_LOCATION_TSIM.arguments");
					port = 1234;
				}
				if (args != null) {
					for (int j = 0; j < args.length - 1; ++j) {
						if (args[j].trim().toLowerCase().compareTo("-port") == 0) {
							port = Integer.valueOf(args[(j + 1)].trim())
									.intValue();
							break;
						}
					}
				}
				return port;
			}
		}
		return -1;
	}

	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		String[] extraOptions = (String[]) null;

		if (monitor.isCanceled()) {
			return;
		}

		String type = configuration
				.getAttribute(
						"com.bjsasc.gdbdebugger.eclipse.plugins.cdt.singlelaunch.PROGRAM_TYPE",
						"");
		String base = "";
		if (type.compareTo("SZMON") == 0) {
			base = "com.bjsasc.gdbdebugger.eclipse.plugins.cdt.singlelaunch.PROGRAM_LOCATION_SZMON";
			extraOptions = new String[] { "-eclipse", "-gdb", "-noreadline" };
		} else if (type.compareTo("TSIM2") == 0) {
			base = "com.bjsasc.gdbdebugger.eclipse.plugins.cdt.singlelaunch.PROGRAM_LOCATION_TSIM";
			extraOptions = new String[] { "-gdb", "-noreadline" };
		}

		this.fAttrProg = (base + ".name");
		this.fAttrWorkspace = (base + ".workspace");
		this.fAttrArguments = (base + ".arguments");

		IPath location = getLocation(configuration, this.fAttrProg);

		if (monitor.isCanceled()) {
			return;
		}

		IPath workingDirectory = getWorkingDirectory(configuration,
				this.fAttrWorkspace);

		if (monitor.isCanceled()) {
			return;
		}

		String[] arguments = getArguments(configuration, this.fAttrArguments);

		if (monitor.isCanceled()) {
			return;
		}

		int cmdLineLength = 1 + ((arguments != null) ? arguments.length : 0)
				+ ((extraOptions != null) ? extraOptions.length : 0);
		String[] cmdLine = new String[cmdLineLength];
		cmdLine[0] = location.toOSString();
		if (arguments != null) {
			System.arraycopy(arguments, 0, cmdLine, 1, arguments.length);
		}
		if (extraOptions != null) {
			int destPos = 1 + ((arguments != null) ? arguments.length : 0);
			System.arraycopy(extraOptions, 0, cmdLine, destPos,
					extraOptions.length);
		}

		File workingDir = null;
		if (workingDirectory != null) {
			workingDir = workingDirectory.toFile();
		}

		if (monitor.isCanceled()) {
			return;
		}

		String[] envp = DebugPlugin.getDefault().getLaunchManager()
				.getEnvironment(configuration);

		if (monitor.isCanceled()) {
			return;
		}

		if (windowListener == null) {
			windowListener = new SzmonLaunchConfigurationDelegate.ProgramLaunchWindowListener(
					this);
			PlatformUI.getWorkbench().addWindowListener(windowListener);
		}

		Process p = null;
		try {
			p = createProcess(cmdLine, workingDir, envp, monitor);
		} catch (IOException localIOException) {
		}

		IProcess process = null;

		Map processAttributes = new HashMap();
		String programName = location.lastSegment();
		String extension = location.getFileExtension();
		if (extension != null) {
			programName = programName.substring(0, programName.length()
					- (extension.length() + 1));
		}
		programName = programName.toLowerCase();
		processAttributes.put(IProcess.ATTR_PROCESS_TYPE, programName);
		processAttributes.put(IProcess.ATTR_PROCESS_TYPE, type);

		if (p != null) {
			monitor.beginTask("begin", -1);
			process = DebugPlugin.newProcess(launch, p, location.toOSString(),
					processAttributes);
			if (process == null) {
				p.destroy();
				throw new CoreException(new Status(4,
						"org.eclipse.ui.externaltools", 150,
						"err",
						null));
			}
		}

		//Begin: added by lichengfei 2012-08-27
		if(process!=null)
		//End: added by lichengfei 2012-08-27
			process.setAttribute(IProcess.ATTR_CMDLINE,
					generateCommandLine(cmdLine));

		if (CommonTab.isLaunchInBackground(configuration)) {
			if (RefreshTab.getRefreshScope(configuration) != null) {
				/*BackgroundResourceRefresher refresher = new BackgroundResourceRefresher(
						configuration, process);
				refresher.startBackgroundRefresh();*/
			}
		} else {
			do
				try {
					if (monitor.isCanceled()) {
						process.terminate();
						break;
					}
					Thread.sleep(50L);
				} catch (InterruptedException localInterruptedException) {
				}
			while (!process.isTerminated());

			RefreshTab.refreshResources(configuration, monitor);
		}
	}

	private String generateCommandLine(String[] commandLine) {
		if (commandLine.length < 1)
			return "";
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < commandLine.length; ++i) {
			buf.append(' ');
			char[] characters = commandLine[i].toCharArray();
			StringBuffer command = new StringBuffer();
			boolean containsSpace = false;
			for (int j = 0; j < characters.length; ++j) {
				char character = characters[j];
				if (character == '"')
					command.append('\\');
				else if (character == ' ') {
					containsSpace = true;
				}
				command.append(character);
			}
			if (containsSpace) {
				buf.append('"');
				buf.append(command);
				buf.append('"');
			} else {
				buf.append(command);
			}
		}
		return buf.toString();
	}

	public static IPath getLocation(ILaunchConfiguration configuration,
			String fAttrProg) throws CoreException {
		String temp = null;
		String location = configuration.getAttribute(fAttrProg, temp);
		if (location == null) {
			abort(
					MessageFormat
							.format(
									ExternalToolsLaunchConfigurationMessages.ExternalToolsUtil_Location_not_specified_by__0__1,
									new String[] { configuration.getName() }),
					null, 0);
		} else {
			String expandedLocation = getStringVariableManager()
					.performStringSubstitution(location);
			return new Path(expandedLocation);
		}

		return null;
	}

	public static IPath getWorkingDirectory(ILaunchConfiguration configuration,
			String fAttrWorkspace) throws CoreException {
		String temp = null;
		String location = configuration.getAttribute(fAttrWorkspace, temp);
		if (location != null) {
			String expandedLocation = getStringVariableManager()
					.performStringSubstitution(location);
			if (expandedLocation.length() > 0) {
				File path = new File(expandedLocation);
				if (path.isDirectory()) {
					return new Path(expandedLocation);
				}
				String msg = MessageFormat
						.format(
								ExternalToolsLaunchConfigurationMessages.ExternalToolsUtil_invalidDirectory__0_,
								new Object[] { expandedLocation,
										configuration.getName() });
				abort(msg, null, 0);
			}
		}
		return null;
	}

	public static String[] getArguments(ILaunchConfiguration configuration,
			String fAttrArguments) throws CoreException {
		String args = configuration.getAttribute(fAttrArguments, "");
		if (args.trim().length() > 0) {
			String expanded = getStringVariableManager()
					.performStringSubstitution(args);
			return ExternalToolsUtil.parseStringIntoList(expanded);
		}
		return null;
	}

	protected static void abort(String message, Throwable exception, int code)
			throws CoreException {
		throw new CoreException(new Status(4, "org.eclipse.ui.externaltools",
				code, message, exception));
	}

	public static IStringVariableManager getStringVariableManager() {
		return VariablesPlugin.getDefault().getStringVariableManager();
	}
}
