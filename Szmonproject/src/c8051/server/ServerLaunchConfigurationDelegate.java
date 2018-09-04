package c8051.server;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.RefreshTab;
import org.eclipse.ui.externaltools.internal.launchConfigurations.ExternalToolsLaunchConfigurationMessages;
import org.eclipse.ui.externaltools.internal.launchConfigurations.ExternalToolsUtil;

public class ServerLaunchConfigurationDelegate implements ILaunchConfigurationDelegate{

	String fAttrProg = "";
	String fAttrWorkspace = "";
	String fAttrArguments = "";
	
	
	
/**
 * 	
 * @param cmdLine    IPath+Argument+ExtraOption
 * @param workingDirectory
 * @param envp     LaunchManager.getEnvironment(configuration)
 * @param monitor    
 * @return Process  return a process with the specified command line 
 * @throws IOException
 * @throws CoreException
 */
	Process createProcess(String[] cmdLine, File workingDirectory,
			String[] envp, IProgressMonitor monitor) throws IOException,
			CoreException {
		String cmd = cmdLine[0];
		for (int i = 1; i < cmdLine.length; ++i) {
			cmd = cmd + " " + cmdLine[i];
		}

		Process p2= DebugPlugin.exec(cmdLine, workingDirectory, envp);

		return p2;
	}
/**
 * 	
 * @param configuration  
 * @param fAttrArguments  Argument's name in the launch constants 
 * @return String array   expanded argument which was expanded   
 * @throws CoreException
 */
	
	@SuppressWarnings("restriction")
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
	
/**
 * 	
 * @param configuration
 * @param fAttrWorkspace   workspace's name in launch constants
 * @return IPath working directory's path 
 * @throws CoreException
 */
	
public static IPath getWorkingDirectory(ILaunchConfiguration configuration,String fAttrWorkspace) throws CoreException {
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
	
/**
 * 	
 * @param configuration String fAttrProg(Location's name in launch constants)
 * @param fAttrProg
 * @return IPath Expand Location 
 * @throws CoreException
 */
	
	
 public static IPath getLocation(ILaunchConfiguration configuration,String fAttrProg) throws CoreException {
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

/**
 *   
 * @param message
 * @param exception
 * @param code
 * @throws CoreException
 * 
 * abort process and display message if location's value is null in the attribute list 
 */
  protected static void abort(String message, Throwable exception,int code) throws CoreException {
	  throw new CoreException(new Status(4, "org.eclipse.ui.externaltools",
				code, message, exception));
	  
  }
	
/**
 * @category static
 * 	@param  null 
 * @return string variable manager
 * 
 * return a string variable manager object to support string substitution 
 * So that location is able to be converted as expanded location 
 * which is used in the class launch 
 */
	public static IStringVariableManager getStringVariableManager() {
		return VariablesPlugin.getDefault().getStringVariableManager();
	}
	
/**
 * 	Launch a system process and a wrapping IProcess object 
 */
@Override
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

	
	if(process!=null)
	
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


/**
 * 
 * @param commandLine
 * @return  String of commandLine modified
 * 
 * set IProcess.ATTR_CMDLINE'S value with the modified string 
 */

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

}