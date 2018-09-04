package c8051.debugger;

import c8051.debugger.Constants;

import java.io.File;

import org.eclipse.cdt.core.IBinaryParser;
import org.eclipse.cdt.core.IBinaryParser.IBinaryObject;
import org.eclipse.cdt.debug.mi.core.GDBCDIDebugger;
import org.eclipse.cdt.debug.mi.core.MIPlugin;
import org.eclipse.cdt.debug.mi.core.cdi.Session;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfiguration;

public class c51debugger extends GDBCDIDebugger
{
  public static final String DEBUGGER_ID = c51debugger.class.getName();
  public static final String DEBUGGER_CMD= "c8051-gdb.exe";

  public Session createLaunchSession(ILaunchConfiguration config, IBinaryParser.IBinaryObject exe, IProgressMonitor monitor)
    throws CoreException
  {
    Session session = null;
    boolean failed = false;
    try {
      String gdb = config.getAttribute(Constants.ATTR_DEBUG_NAME,DEBUGGER_CMD);

      if (Platform.getOS().compareTo("win32") == 0) {
        String path = exe.getPath().toOSString();
        if (path.trim().indexOf(' ') != -1) {
          throw newCoreException("Due to limitatios of the MINGW GDB debugger only workspace pathes without a \" \" space are allowed. Please relocate your workspace, the path \n\"" + path + "\"\n won't work", null);
        }
      }

      File cwd = exe.getPath().removeLastSegments(1).toFile();
      String gdbinit = config.getAttribute(Constants.ATTR_GDB_INIT, ".gdbinit");

      String type = config.getAttribute(Constants.ATTR_LAUNCH_GDB_type, "socket");
      String host = config.getAttribute(Constants.ATTR_LAUNCH_GDB_host, "localhost");
      String port = config.getAttribute(Constants.ATTR_LAUNCH_GDB_port, "2222");
      String tty = config.getAttribute(Constants.ATTR_LAUNCH_GDB_tty, "/dev/ttyS0");
      String baud = config.getAttribute(Constants.ATTR_LAUNCH_GDB_baud, "");
      

      if (host.length() == 0) {
        host = "localhost";
      }
      if (port.length() == 0) {
        port = "1234";
      }

      String ip = host + ":" + port;
      if (type.equals("tty")) {
        ip = tty;
        if (baud.trim().length() > 0) {
          ip = "[" + baud + "]" + ip;
        }
      }
      else if(type.equals("sim"))
      {
    	  ip="sim";
      }

      session = (Session)DebuggerSession.getDefault().createCSession(gdb, exe.getPath().toFile(), cwd, gdbinit, monitor, ip);
      initializeLibraries(config, session);
      return session;
    }
    catch (Exception e)
    {
      throw newCoreException(e);
    } finally {
      if ((failed) && 
        (session != null))
        try {
          session.terminate();
        }
        catch (Exception localException2)
        {
        }
    }
  }

  public Session createAttachSession(ILaunchConfiguration config, IBinaryParser.IBinaryObject exe, IProgressMonitor monitor)
    throws CoreException
  {
    String msg = MIPlugin.getResourceString("src.GDBServerDebugger.GDBServer_attaching_unsupported");
    throw newCoreException(msg, null);
  }

  public Session createCoreSession(ILaunchConfiguration config, IBinaryParser.IBinaryObject exe, IProgressMonitor monitor)
    throws CoreException
  {
    String msg = MIPlugin.getResourceString("src.GDBServerDebugger.GDBServer_corefiles_unsupported");
    throw newCoreException(msg, null);
  }
}
