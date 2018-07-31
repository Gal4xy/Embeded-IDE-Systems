package com.bjsasc.debug.core;

import org.eclipse.cdt.debug.core.cdi.model.ICDITarget;
import org.eclipse.cdt.debug.mi.core.GDBServerCDIDebugger2;
import org.eclipse.cdt.debug.mi.core.IGDBServerMILaunchConfigurationConstants;
import org.eclipse.cdt.debug.mi.core.IMILaunchConfigurationConstants;
import org.eclipse.cdt.debug.mi.core.MIException;
import org.eclipse.cdt.debug.mi.core.MIPlugin;
import org.eclipse.cdt.debug.mi.core.MISession;
import org.eclipse.cdt.debug.mi.core.cdi.Session;
import org.eclipse.cdt.debug.mi.core.cdi.model.Target;
import org.eclipse.cdt.debug.mi.core.command.CLICommand;
import org.eclipse.cdt.debug.mi.core.command.Command;
import org.eclipse.cdt.debug.mi.core.command.CommandFactory;
import org.eclipse.cdt.debug.mi.core.command.MIGDBSet;
import org.eclipse.cdt.debug.mi.core.command.MITargetSelect;
import org.eclipse.cdt.debug.mi.core.command.MIVersion;
import org.eclipse.cdt.debug.mi.core.output.MIInfo;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.debug.core.ILaunchConfiguration;


public class SparcElfDebugger extends GDBServerCDIDebugger2 {
	public static String fDebuggerId=SparcElfDebugger.class.getName();
	protected CommandFactory getCommandFactory(ILaunchConfiguration config)
			throws CoreException {
		return new CommandFactory(MIVersion.MI2);
	}
	protected String[] getTargetParams( ILaunchConfiguration config, boolean tcpConnection ) throws CoreException {
		String remote = null;
		if ( tcpConnection ) {
			remote = config.getAttribute( IGDBServerMILaunchConfigurationConstants.ATTR_HOST, "invalid" ); //$NON-NLS-1$
			remote += ":"; //$NON-NLS-1$
			remote += config.getAttribute( IGDBServerMILaunchConfigurationConstants.ATTR_PORT, "invalid" ); //$NON-NLS-1$
		}
		else {
			remote = config.getAttribute( IGDBServerMILaunchConfigurationConstants.ATTR_DEV, "invalid" ); //$NON-NLS-1$		
		}
		return new String[]{ "extended-remote", remote }; //$NON-NLS-1$
	}
	@Override
	protected void startGDBServerSession( ILaunchConfiguration config, Session session, IProgressMonitor monitor ) throws CoreException {
		if ( monitor.isCanceled() ) {
			throw new OperationCanceledException();
		}
		boolean useCoreDump = config.getAttribute("com.bjsasc.szmondebugger.launch.external.arguments.useCoreDump", false);
		if(useCoreDump==true)
			return;
		ICDITarget[] targets = session.getTargets();
		int launchTimeout = MIPlugin.getLaunchTimeout();
		boolean tcpConnection = config.getAttribute( IGDBServerMILaunchConfigurationConstants.ATTR_REMOTE_TCP, false );
		// Set serial line parameters
		if ( !tcpConnection ) {
			String remoteBaud = config.getAttribute( IGDBServerMILaunchConfigurationConstants.ATTR_DEV_SPEED, "invalid" ); //$NON-NLS-1$
			for( int i = 0; i < targets.length; ++i ) {
				if ( monitor.isCanceled() ) {
					throw new OperationCanceledException();
				}
				Target target = (Target)targets[i];
				MISession miSession = target.getMISession();
				org.eclipse.cdt.debug.mi.core.command.CommandFactory factory = miSession.getCommandFactory();
				MIGDBSet setRemoteBaud = factory.createMIGDBSet( new String[]{ "remotebaud", remoteBaud } ); //$NON-NLS-1$
				// Set serial line parameters
				MIInfo info = null;
				MIException ex = null;
				try {
					// shouldn't we use the command timeout instead?
					miSession.postCommand( setRemoteBaud, launchTimeout );
					info = setRemoteBaud.getMIInfo();
				}
				catch( MIException e ) {
					ex = e;
				}
				if ( info == null ) {
					throw newCoreException( MIPlugin.getResourceString( "src.GDBServerDebugger.Can_not_set_Baud" ), ex ); //$NON-NLS-1$
				}
			}		
		}
		for( int i = 0; i < targets.length; ++i ) {
			if ( monitor.isCanceled() ) {
				throw new OperationCanceledException();
			}
			Target target = (Target)targets[i];
			MISession miSession = target.getMISession();
			org.eclipse.cdt.debug.mi.core.command.CommandFactory factory = miSession.getCommandFactory();
			String[] targetParams = getTargetParams( config, tcpConnection );
			MITargetSelect select = factory.createMITargetSelect( targetParams );
			Command cmd = new CLICommand("set remotelogfile debugtrace.txt");
			MIInfo info = null;
			MIException ex = null;
			try {
				miSession.postCommand(cmd);
				info = select.getMIInfo();
				miSession.postCommand( select, launchTimeout );
				info = select.getMIInfo();
			}
			catch( MIException e ) {
				ex = e;
			}
			if ( info == null ) {
				throw newCoreException( MIPlugin.getResourceString( "src.GDBServerCDIDebugger.target_selection_failed" ), ex ); //$NON-NLS-1$
			}
			// @@@ We have to set the suspended state manually
			miSession.getMIInferior().setSuspended();
			miSession.getMIInferior().update();
			//miSession.getMIInferior().setIsRemoteInferior(true);
		}
	}
	@Override
	protected boolean getBreakpointsWithFullNameAttribute( ILaunchConfiguration config ) {
		//modified by maobaolong at 2012-4-28
		//默认全路径加断点
		boolean result =true;// IMILaunchConfigurationConstants.DEBUGGER_FULLPATH_BREAKPOINTS_DEFAULT; 
		try {
			return config.getAttribute( IMILaunchConfigurationConstants.ATTR_DEBUGGER_FULLPATH_BREAKPOINTS, result );
		}
		catch( CoreException e ) {
			// use default
		}
		return result;
	}
}
