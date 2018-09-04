package szmonproject.gdb;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Preferences;

/**
 * @since 2018/02/06 
 * @author Galaxy
 *
 * be invoked by GDBClass to return the ICDIsession 
 * parameter are required  
 */
public class SparcElfSession {

	 public static final String PLUGIN_ID = "org.eclipse.cdt.debug.mi.core";
	  private static final String GDBINIT = ".gdbinit";
	  private static final String GDB = "gdb";
	  private static ResourceBundle fgResourceBundle;
	  private static SparcElfSession fSession;

	  static
	  {
	    try
	    {
	      fgResourceBundle = ResourceBundle.getBundle("org.eclipse.cdt.debug.mi.core.MIPluginResources");
	    } catch (MissingResourceException localMissingResourceException) {
	      fgResourceBundle = null;
	    }
	  }

	  public static SparcElfSession getDefault()
	  {
	    if (fSession == null) {
	      fSession = new SparcElfSession();
	    }
	    return fSession;
	  }
	  
/**   define tty to create the ICDI session  
 * 	  
 * @param gdb
 * @param program
 * @param cwd
 * @param gdbinit
 * @param monitor
 * @param ip
 * @return
 */
	public ICDISession createCSession(String gdb,File program,File cwd,String gdbinit,
			IProgressMonitor monitor, String ip) 
					throws IOException, MIException{
		 
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

/**
 *    create the ICDI session
 *    
 * @param gdb
 * @param program
 * @param cwd
 * @param gdbinit
 * @param pty
 * @param monitor
 * @param ip
 * @return
 * @throws IOException
 * @throws MIException
 */
	public ICDISession createCSession(String gdb,File program,File cwd,String gdbinit,IMITTY pty,
			IProgressMonitor monitor, String ip) throws IOException,MIException {
		   
		    //define session obj
		     SparcElfMiSession session; 
		
		    String[] args=new String[0];
		    
		    //create string command 
		    if((gdb==null)||(gdb.length()==0)) {
		    	gdb="gdb";
		    }
		    
		    if((gdbinit==null)||(gdbinit.length()==0)) {
		    	gdbinit="gdbinit";
		    }
		    
		    String cwd_str=cwd.getAbsolutePath().replace("\\", "/");
		    
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
		    
		    //create MIProcess
		    MIProcess pgdb=new MIProcessAdapter(args,monitor);
		    
		    //create MISession 
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
	
/**  
 * 	 create the MiSession
 */
	public SparcElfMiSession createMISession(MIProcess process,IMITTY pty,int commandTimeout
			,int type,int launchTimeout,String ip) throws MIException {
		return new SparcElfMiSession(process, pty, commandTimeout, type, launchTimeout, ip);
	}

	
	/**
	 *    define launch timeout/command timeout 
	 *    to create the MISession
	 *    
	 * @param process
	 * @param pty
	 * @param type
	 * @param ip
	 * @return  Session
	 * @throws MIException 
	 */
    public SparcElfMiSession createMISession(MIProcess process,IMITTY pty
			,int type,String ip) throws MIException {
    	Preferences prefs = MIPlugin.getDefault().getPluginPreferences();
        int commandTimeout = prefs.getInt(IMIConstants.PREF_REQUEST_TIMEOUT);
        int launchTimeout = prefs.getInt(IMIConstants.PREF_REQUEST_LAUNCH_TIMEOUT);
        return createMISession(process, pty, commandTimeout, type, launchTimeout, ip);
	}
	
    public String substituteSpace(String s) {
        int compareInt = Platform.getOS().compareTo("win32");

        return s; 
      }

}
