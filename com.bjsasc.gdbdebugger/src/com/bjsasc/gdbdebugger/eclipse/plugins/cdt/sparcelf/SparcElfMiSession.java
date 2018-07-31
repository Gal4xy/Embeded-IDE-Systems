package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.sparcelf;

import org.eclipse.cdt.debug.mi.core.IMITTY;
import org.eclipse.cdt.debug.mi.core.MIException;
import org.eclipse.cdt.debug.mi.core.MIProcess;
import org.eclipse.cdt.debug.mi.core.MISession;
import org.eclipse.cdt.debug.mi.core.command.Command;
import org.eclipse.cdt.debug.mi.core.command.CommandFactory;
import org.eclipse.cdt.debug.mi.core.command.MICommand;
import org.eclipse.cdt.debug.mi.core.command.MITargetDownload;

class SparcElfMiSession extends MISession
{
  int m_launchTimeout;
  private MITargetDownload download;

  public SparcElfMiSession(MIProcess process, IMITTY pty, int commandTimeout, int type, int launchTimeout, String ip)
    throws MIException
  {
    super(process, pty, commandTimeout, type, launchTimeout);
    this.m_launchTimeout = launchTimeout;

    if (ip.startsWith("[")) {
      int i = ip.indexOf(']');
      if (i != -1) {
        String baud = ip.substring(1, i);
        ip = ip.substring(i + 1);
        MICommand remotebaud = new MICommand("mi2", "set remotebaud", new String[] { baud });
        postCommand(remotebaud, launchTimeout);
        remotebaud.getMIInfo();
      }
    }

//modified for sim type by mbl at 20110705
    MICommand remote=null;
    if("sim".equals(ip))
    {
    	remote = getCommandFactory().createMITargetSelect(new String[] {  ip });       
    }
    else
    {
	    remote = getCommandFactory().createMITargetSelect(new String[] { "remote", ip });	   
    }
    postCommand(remote, launchTimeout);
    remote.getMIInfo();

    this.download = new MITargetDownload("mi2");
    postCommand(this.download, launchTimeout);
    remote.getMIInfo();
  }

  public void postCommand(Command cmd, long timeout) throws MIException
  {
    if (cmd instanceof MICommand) {
      String op = ((MICommand)cmd).getOperation();
      if (op.equals("-exec-run")) {
        super.postCommand(this.download, this.m_launchTimeout);
      }
    }
    super.postCommand(cmd, timeout);
  }
}
