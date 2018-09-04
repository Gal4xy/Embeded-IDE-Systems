package szmonproject.gdb;

/**
 * @since 2018/02/06
 */
import org.eclipse.cdt.debug.mi.core.IMITTY;
import org.eclipse.cdt.debug.mi.core.MIException;
import org.eclipse.cdt.debug.mi.core.MIProcess;
import org.eclipse.cdt.debug.mi.core.MISession;
import org.eclipse.cdt.debug.mi.core.command.*;

public class SparcElfMiSession extends MISession {

	int m_launchTimeout;
	private MITargetDownload download;

	/**
	 * remote baud --- command 
	 * post command to the device to notive the beginning point of the communication
	 * 
	 * @param process
	 * @param pty
	 * @param commandTimeout
	 * @param type
	 * @param launchTimeout
	 * @param ip
	 * @throws MIException
	 */
	public SparcElfMiSession(MIProcess process,IMITTY pty,int commandTimeout,
			int type,int launchTimeout,String ip) throws MIException{
		  super(process, pty, commandTimeout, type, launchTimeout);
	      this.m_launchTimeout=launchTimeout;
	      MICommand remote=null;
	      
	      if(ip.startsWith("{")) {
	    	  int i=ip.indexOf(']');
	    	       if(i!=-1) {
	    	    	   String baud=ip.substring(1,i);
	    	    	   ip=ip.substring(i+1);
	    	    	   MICommand remotebaud = new MICommand("mi2", "set remotebaud", new String[] { baud });
	    	    	   postCommand(remotebaud,launchTimeout);
	    	    	   remotebaud.getMIInfo();
	    	       }
	      }
	      
	      
	      if("sim".equals(ip))
	      {
	      	remote = getCommandFactory().createMITargetSelect(new String[] {  ip });       
	      }
	      else
	      {
	  	    remote = getCommandFactory().createMITargetSelect(new String[] { "remote", ip });	   
	      }
	      
	      postCommand(remote,launchTimeout);
	      remote.getMIInfo();
	      
	      this.download = new MITargetDownload("mi2");
	      postCommand(this.download, launchTimeout);
	      remote.getMIInfo();
	}
	
	/**
	 * Post command to GDB 
	 * be invoked in the construct function 
	 * Remind GDB the process is allowed to start  
	 */
	
	public void postCommand(Command cmd,long timeout) throws MIException {
		if(cmd instanceof MICommand) {
			String p=((MICommand)cmd).getOperation();
		  
	       if(p.equals("-exec-run")){
			  super.postCommand(this.download,this.m_launchTimeout);
		  }
		}  
		  super.postCommand(cmd, timeout);
	}
}
