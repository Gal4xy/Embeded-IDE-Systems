package c8051.debugger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.cdt.debug.core.cdi.ICDISession;
import org.eclipse.cdt.debug.mi.core.IMIConstants;
import org.eclipse.cdt.debug.mi.core.IMITTY;
import org.eclipse.cdt.debug.mi.core.MIException;
import org.eclipse.cdt.debug.mi.core.MIPlugin;
import org.eclipse.cdt.debug.mi.core.MIProcess;
import org.eclipse.cdt.debug.mi.core.MIProcessAdapter;
import org.eclipse.cdt.debug.mi.core.MITTYAdapter;
import org.eclipse.cdt.debug.mi.core.cdi.Session;
import org.eclipse.cdt.utils.pty.PTY;
import org.eclipse.cdt.utils.spawner.ProcessFactory;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Preferences;

import c8051.debugger.DebuggerMISession;
import c8051.debugger.DebuggerSession;

import c8051.debugger.DebuggerSession;

public class DebuggerSession {

	public static final String PLUGIN_ID = "org.eclipse.cdt.debug.mi.core";
	  private static final String GDBINIT = ".gdbinit";
	  private static final String GDB = "gdb";
	  private static ResourceBundle fgResourceBundle;
	  private static DebuggerSession fSession;
	  
	  static
	  {
	    try
	    {
	      fgResourceBundle = ResourceBundle.getBundle("org.eclipse.cdt.debug.mi.core.MIPluginResources");
	    } catch (MissingResourceException localMissingResourceException) {
	      fgResourceBundle = null;
	    }
	  }
	  
	  
	  public static DebuggerSession getDefault()
	  {
	    if (fSession == null) {
	      fSession = new DebuggerSession();
	    }
	    return fSession;
	  }

	  public String substituteSpace(String s) {
	    int compareInt = Platform.getOS().compareTo("win32");
	    return s; 
	  }
	  
	  public DebuggerMISession createMISession(MIProcess process, IMITTY pty, int commandTimeout, int type, int launchTimeout, String ip)
			    throws MIException
			  {
			    return new DebuggerMISession(process, pty, commandTimeout, type, launchTimeout, ip);
			  }
	  
	  public DebuggerMISession createMISession(MIProcess process, IMITTY pty, int type, String ip)
			    throws MIException
			  {
			    Preferences prefs = MIPlugin.getDefault().getPluginPreferences();
			    int commandTimeout = prefs.getInt(IMIConstants.PREF_REQUEST_TIMEOUT);
			    int launchTimeout = prefs.getInt(IMIConstants.PREF_REQUEST_LAUNCH_TIMEOUT);
			    return createMISession(process, pty, commandTimeout, type, launchTimeout, ip);
			  }
	  
	  
	  public ICDISession createCSession(String gdb, File program, File cwd, String gdbinit, IProgressMonitor monitor, String ip)
			    throws IOException, MIException
			  {
			    IMITTY pty = null;
			    boolean failed = false;
			    try
			    {
			      PTY pseudo = new PTY();
			      pty = new MITTYAdapter(pseudo);
			    }
			    catch (IOException localIOException1) {
			    }
			    try {
			      return createCSession(gdb, program, cwd, gdbinit, pty, monitor, ip);
			    }
			    catch (IOException exc)
			    {
			    }
			    catch (MIException exc) {
			    }
			    finally {
			      if ((failed) && 
			        (pty != null))
			        try {
			          OutputStream out = pty.getOutputStream();
			          if (out != null) {
			            out.close();
			          }
			          InputStream in = pty.getInputStream();
			          if (in != null)
			            in.close();
			        }
			        catch (IOException localIOException3)
			        {
			        }
			    }
			    return null;
			  }
	  
	  
	  public ICDISession createCSession(String gdb, File program, File cwd, String gdbinit, IMITTY pty, IProgressMonitor monitor, String ip)
			    throws IOException, MIException
			  {
			    String[] args = new String[0];

			    if ((gdb == null) || (gdb.length() == 0)) {
			      gdb = "gdb";
			    }

			    if ((gdbinit == null) || (gdbinit.length() == 0)) {
			      gdbinit = ".gdbinit";
			    }
			    String cwd_str = cwd.getAbsolutePath().replace("\\", "/");
			    if (pty != null) {
			      if (program == null)
			        args = new String[] { gdb, "--cd=" + substituteSpace(cwd_str), "--command=" + substituteSpace(gdbinit), "-q", "-nw", "-tty", pty.getSlaveName(), "-i", "mi2" };
			      else {
			        args = new String[] { gdb, "--cd=" + substituteSpace(cwd_str), "--command=" + substituteSpace(gdbinit), "-q", "-nw", "-tty", pty.getSlaveName(), "-i", "mi2", substituteSpace(program.getAbsolutePath()) };
			      }
			    }
			    else if (program == null)
			      args = new String[] { gdb, "--cd=" + substituteSpace(cwd_str), "--command=" + substituteSpace(gdbinit), "-q", "-nw", "-i", "mi2" };
			    else {
			      args = new String[] { gdb, "--cd=" + substituteSpace(cwd_str), "--command=" + substituteSpace(gdbinit), "-q", "-nw", "-i", "mi2", substituteSpace(program.getAbsolutePath()) };
			    }
			    StringBuffer sb = new StringBuffer();
			    for (int i=0;i<args.length;i++){
			    	sb.append(args[i]+" ");
			    }
			    System.out.println(sb.toString());
			    MIProcess pgdb = new MIProcessAdapter(args, monitor);
			    DebuggerMISession session;
			    try
			    {
			      session = createMISession(pgdb, pty, 0, ip);
			    } catch (MIException e) {
			      pgdb.destroy();
			      throw e;
			    }
			    session.getMIInferior().setSuspended();
			    session.getMIInferior().update();
			    return new Session(session);
			  }

	  public static String getUniqueIdentifier()
	  {
	    if (MIPlugin.getDefault() == null)
	    {
	      return "org.eclipse.cdt.debug.mi.core";
	    }
	    return MIPlugin.getDefault().getBundle().getSymbolicName();
	  }

	  public void debugLog(String message) {
	    if (!MIPlugin.getDefault().isDebugging())
	      return;
	    message = MessageFormat.format("[{0}] {1}", new Object[] { new Long(System.currentTimeMillis()), message });

	    while (message.length() > 100) {
	      String partial = message.substring(0, 100);
	      message = message.substring(100);
	      System.err.println(partial + "\\");
	    }
	    if (message.endsWith("\n"))
	      System.err.print(message);
	    else
	      System.err.println(message);
	  }

	  public static String getResourceString(String key)
	  {
	    try {
	      return fgResourceBundle.getString(key);
	    } catch (MissingResourceException localMissingResourceException) {
	      return '!' + key + '!'; } catch (NullPointerException localNullPointerException) {
	    }
	    return '#' + key + '#';
	  }

	  protected Process getGDBProcess(String[] args)
	    throws IOException
	  {
	    if (MIPlugin.getDefault().isDebugging())
	    {
	      StringBuffer sb = new StringBuffer();
	      for (int i = 0; i < args.length; ++i)
	      {
	        sb.append(args[i]);
	        sb.append(' ');
	      }
	      MIPlugin.getDefault().debugLog(sb.toString());
	    }
	    final Process pgdb = ProcessFactory.getFactory().exec(args);
//	    Thread syncStartup = new DebuggerSession.1(this, "GDB Start", pgdb);
	    Thread syncStartup = new Thread("GDB Start"){
	    	 public void run()
	    	  {
	    	    try
	    	    {
	    	      InputStream stream = pgdb.getInputStream();
	    	      Reader r = new InputStreamReader(stream);
	    	      BufferedReader reader = new BufferedReader(r);
	    	      String line;
	    	      while ((line = reader.readLine()) != null) {
	    	        line = line.trim();

	    	        if (line.endsWith("(gdb)"));
	    	      }
	    	    }
	    	    catch (Exception localException)
	    	    {
	    	    }

	    	    synchronized (pgdb) {
	    	      pgdb.notifyAll();
	    	    }
	    	  }
	    };
	    syncStartup.start();

	    synchronized (pgdb) {
	      Preferences prefs = MIPlugin.getDefault().getPluginPreferences();
	      int launchTimeout = prefs.getInt(IMIConstants.PREF_REQUEST_LAUNCH_TIMEOUT);
	      while (syncStartup.isAlive())
	        try {
	          pgdb.wait(launchTimeout);
	        }
	        catch (InterruptedException localInterruptedException1)
	        {
	        }
	    }
	    try {
	      syncStartup.interrupt();
	      syncStartup.join(1000L);
	    } catch (InterruptedException localInterruptedException2) {
	    }
	    return pgdb;
	  }
}
