/**
 * 
 */
package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.sparcelf;

/**
 * @author jonas
 *
 */
public class LaunchConstants
{
	public static final String PLUGIN_ID = "com.bjsasc.dsudebugger.launch";
	public static final String LAUNCH_CONFIG_TYPE_ID = PLUGIN_ID + ".dsu_sparc";

	public static final String ATTR_LAUNCH_TYPE = LAUNCH_CONFIG_TYPE_ID + ".launch_type";
	public static final String LAUNCH_TYPE_REMOTE = "remote"; // Connect to remote SZMON / TSIM
	public static final String LAUNCH_TYPE_INTERNAL = "internal"; // Launch SZMON / TSIM from Eclipse
	
	public static final String ATTR_SZMON_PROGRAM_ARGUMENTS = "com.bjsasc.dsudebugger.launch.external.arguments";
	public static final String ATTR_SZMON_PROGRAM_ARGUMENTS_DEVICE =  ATTR_SZMON_PROGRAM_ARGUMENTS +".device";
	public static final String ATTR_SZMON_PROGRAM_ARGUMENTS_BAUDRATE = ATTR_SZMON_PROGRAM_ARGUMENTS + ".baudrate";
	//added by mbl at 2011-04-25
	public static final String ATTR_SZMON_PROGRAM_ARGUMENTS_PORTID = ATTR_SZMON_PROGRAM_ARGUMENTS + ".portID";
	
	// Which external tool to launch
	public static final String ATTR_EXTERNAL_LAUNCH_NAME = LAUNCH_CONFIG_TYPE_ID + ".external";
	// Should the external tool terminate when finished?
	public static final String ATTR_TERMINATE_EXTERNAL = LAUNCH_CONFIG_TYPE_ID + ".external.terminate";
	public static final boolean TERMINATE_EXTERNAL_DEFAULT = false;
	
	public static final String ATTR_USE_THREADS = LAUNCH_CONFIG_TYPE_ID + ".useThreads";
	public static final boolean USE_THREADS_DEFAULT = true;
	
	public static final boolean BREAK_AT_STARTUP_DEFAULT = true;
	
	public static final String ATTR_ATTACH_MODE = LAUNCH_CONFIG_TYPE_ID + ".attach";
	public static final boolean ATTACH_MODE_DEFAULT = false;
	
	// Error codes
	public static final int LAUNCH_FAILED = 99;
	public static final int LAUNCH_CANCELED = 100;
	
	public static final String[] ALL_DEVICE = { "com1", "com2", "com3", "com4" };

	public static final String[] ALL_BAUDRATE = { "", "9600", "19200", "38400",
			"57600", "115200", "230400", "460800" };
	
	//added¡¡by mbl at 2011-04-25
	public static final String[] ALL_PORTID = { "2222", "3333", "4444", "5555","6666" };
	
}
