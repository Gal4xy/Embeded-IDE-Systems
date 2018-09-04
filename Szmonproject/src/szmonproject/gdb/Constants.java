package szmonproject.gdb;

import org.eclipse.cdt.debug.mi.core.MIPlugin;

public abstract interface Constants {
	public static final String ATTR_TSIM = MIPlugin.getUniqueIdentifier() + ".sparcelf.TSIM";
	public static final String ATTR_TSIM_LAUNCH = MIPlugin.getUniqueIdentifier() + ".sparcelf.TSIMLAUNCH";
	public static final String ATTR_TSIM_TYPE = MIPlugin.getUniqueIdentifier() + ".sparcelf.TYPE";
	public static final String ATTR_TSIM_HOST = MIPlugin.getUniqueIdentifier() + ".sparcelf.TSIMHOST";
	public static final String ATTR_TSIM_PORT = MIPlugin.getUniqueIdentifier() + ".sparcelf.TSIMPORT";
	public static final String ATTR_TSIM_TTY = MIPlugin.getUniqueIdentifier() + ".sparcelf.TTY";
	public static final String ATTR_TSIM_BAUD = MIPlugin.getUniqueIdentifier() + ".sparcelf.BAUD";
	public static final String DEBUG_NAME = MIPlugin.getUniqueIdentifier() + ".sparcelf.GDB";
	public static final String ATTR_DEBUG_NAME = MIPlugin.getUniqueIdentifier() + ".sparcelf.GDBNAME";
	public static final String ATTR_GDB_INIT = MIPlugin.getUniqueIdentifier() + ".sparcelf.GDBINITSTR";

	public static final String ATTR_DEBUGGER_AUTO_SOLIB = MIPlugin.getUniqueIdentifier() + ".sparcelf.AUTO_SOLIB";

	public static final String ATTR_DEBUGGER_STOP_ON_SOLIB_EVENTS = MIPlugin.getUniqueIdentifier() + ".sparcelf.STOP_ON_SOLIB_EVENTS";

	public static final String ATTR_DEBUGGER_SOLIB_PATH = MIPlugin.getUniqueIdentifier() + ".sparcelf.SOLIB_PATH";
	public static final boolean DEBUGGER_AUTO_SOLIB_DEFAULT = true;
	public static final boolean DEBUGGER_STOP_ON_SOLIB_EVENTS_DEFAULT = false;
	public static final String ATTR_LAUNCH_IPFIELD_type = MIPlugin.getUniqueIdentifier() + ".ATTR_LAUNCH_IPFIELD_type";
	public static final String ATTR_LAUNCH_IPFIELD_host = MIPlugin.getUniqueIdentifier() + ".ATTR_LAUNCH_IPFIELD_host";
	public static final String ATTR_LAUNCH_IPFIELD_port = MIPlugin.getUniqueIdentifier() + ".ATTR_LAUNCH_IPFIELD_port";
	public static final String ATTR_LAUNCH_IPFIELD_tty = MIPlugin.getUniqueIdentifier() + ".ATTR_LAUNCH_IPFIELD_tty";
	public static final String ATTR_LAUNCH_IPFIELD_BAUD = MIPlugin.getUniqueIdentifier() + ".ATTR_LAUNCH_IPFIELD_baud";
	public static final String ATTR_LAUNCH_IPFIELD_starttype = MIPlugin.getUniqueIdentifier() + ".ATTR_LAUNCH_IPFIELD_starttype";
	public static final String ATTR_LAUNCH_external = MIPlugin.getUniqueIdentifier() + ".ATTR_LAUNCH_external";
	public static final String ATTR_LAUNCH_external_terminate = MIPlugin.getUniqueIdentifier() + ".ATTR_LAUNCH_external_terminate";
	public static final String ATTR_LAUNCH_internal_target = MIPlugin.getUniqueIdentifier() + ".ATTR_LAUNCH_internal_target";
	public static final String ATTR_LAUNCH_external_target = MIPlugin.getUniqueIdentifier() + ".ATTR_LAUNCH_external_target";
	public static final String ATTR_LAUNCH_external_target_command = MIPlugin.getUniqueIdentifier() + ".ATTR_LAUNCH_external_target_command";
	public static final String ATTR_LAUNCH_GDB_type = MIPlugin.getUniqueIdentifier() + ".ATTR_LAUNCH_GDB_type";
	public static final String ATTR_LAUNCH_GDB_host = MIPlugin.getUniqueIdentifier() + ".ATTR_LAUNCH_GDB_host";
	public static final String ATTR_LAUNCH_GDB_port = MIPlugin.getUniqueIdentifier() + ".ATTR_LAUNCH_GDB_port";
	public static final String ATTR_LAUNCH_GDB_tty = MIPlugin.getUniqueIdentifier() + ".ATTR_LAUNCH_GDB_tty";
	public static final String ATTR_LAUNCH_GDB_baud = MIPlugin.getUniqueIdentifier() + ".ATTR_LAUNCH_GDB_baud";
	public static final String ATTR_GDBCON_TYPE_SOCKET = "socket";
	public static final String ATTR_GDBCON_TYPE_TTY = "tty";
}
