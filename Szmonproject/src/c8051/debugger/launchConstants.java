package c8051.debugger;


public class launchConstants
{
	public static final String PLUGIN_ID = "com.bjsasc.c8051debugger.launch";
	public static final String LAUNCH_CONFIG_TYPE_ID = PLUGIN_ID + ".dsu_sparc";

	public static final String ATTR_LAUNCH_TYPE = LAUNCH_CONFIG_TYPE_ID + ".launch_type";
	public static final String LAUNCH_TYPE_REMOTE = "remote"; // Connect to remote GRMON / TSIM
	public static final String LAUNCH_TYPE_INTERNAL = "internal"; // Launch GRMON / TSIM from Eclipse
	
	public static final String ATTR_GRMON_PROGRAM_ARGUMENTS = "com.bjsasc.c8051debugger.launch.external.arguments";
	public static final String ATTR_CRMON_PROGRAM_ARGUMENTS_DEVICE =  ATTR_GRMON_PROGRAM_ARGUMENTS +".device";
	public static final String ATTR_CRMON_PROGRAM_ARGUMENTS_BAUDRATE = ATTR_GRMON_PROGRAM_ARGUMENTS + ".baudrate";
	
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
	
	//added by mbl at 2011-04-25
	
	public static final String ATTR_CRMON_PROGRAM_ARGUMENTS_PORTID = ATTR_GRMON_PROGRAM_ARGUMENTS + ".portID";
	public static final String ATTR_GDBSEVER = ATTR_GRMON_PROGRAM_ARGUMENTS + ".gdbsever";
	//added¡¡by mbl at 2011-04-25
	//modified by mbl at 2011-06-08
	public static final String[] ALL_PORTID = { "6000", "3333", "4444", "5555","6666","7777","8888","9999","9991","9992"};
}
