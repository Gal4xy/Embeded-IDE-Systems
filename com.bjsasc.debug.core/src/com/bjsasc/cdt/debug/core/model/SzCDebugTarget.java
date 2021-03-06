package com.bjsasc.cdt.debug.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.cdt.core.IAddress;
import org.eclipse.cdt.core.IBinaryParser.IBinaryObject;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.ICDILocation;
import org.eclipse.cdt.debug.core.cdi.model.ICDITarget;
import org.eclipse.cdt.debug.core.cdi.model.ICDITargetConfiguration;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;
import org.eclipse.cdt.debug.core.model.CDebugElementState;
import org.eclipse.cdt.debug.mi.core.command.CLICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;

import com.bjsasc.debug.core.CDIDebugModel;
import com.bjsasc.utils.PrivateAccessor;

public class SzCDebugTarget extends org.eclipse.cdt.debug.internal.core.model.CDebugTarget{
	private Preferences myfPreferences = null;
	public SzCDebugTarget(ILaunch launch, IProject project, ICDITarget cdiTarget,
			String name, IProcess debuggeeProcess, IBinaryObject file,
			boolean allowsTerminate, boolean allowsDisconnect) {
		super(launch, project, cdiTarget, name, debuggeeProcess, file, allowsTerminate,
				allowsDisconnect); 
		// TODO Auto-generated constructor test stub
	}
	protected SzCThread createThread(ICDIThread cdiThread) {
		SzCThread thread = new SzCThread(this, cdiThread);
		getThreadList().add(thread);
		return thread;
	}
	
	public synchronized List refreshThreads() {
		ArrayList newThreads = new ArrayList(5);
		ArrayList list = new ArrayList(5);
		ArrayList debugEvents = new ArrayList(5);
		List oldList = (List) getThreadList().clone();
		ICDIThread[] cdiThreads = new ICDIThread[0];
		ICDIThread currentCDIThread = null;
		try {
			final ICDITarget cdiTarget = getCDITarget();
			if (cdiTarget != null) {
				cdiThreads = cdiTarget.getThreads();
				currentCDIThread = cdiTarget.getCurrentThread();
			}
		} catch (CDIException e) {
		}
		for (int i = 0; i < cdiThreads.length; ++i) {
			SzCThread thread = (SzCThread) findThread(oldList, cdiThreads[i]);
			if (thread == null) {
				thread = new SzCThread(this, cdiThreads[i]);
				newThreads.add(thread);
			} else {
				oldList.remove(thread);
			}
			thread.setCurrent(cdiThreads[i].equals(currentCDIThread));
			list.add(thread);
		}
		Iterator it = oldList.iterator();
		while (it.hasNext()) {
			SzCThread thread = (SzCThread) it.next();
			thread.terminated();
			debugEvents.add(thread.createTerminateEvent());
		}
		PrivateAccessor.callPrivateMethod((org.eclipse.cdt.debug.internal.core.model.CDebugTarget)this,org.eclipse.cdt.debug.internal.core.model.CDebugTarget.class, "setThreadList",  list);
	//	setThreadList(list);
		it = newThreads.iterator();
		while (it.hasNext()) {
			debugEvents.add(((SzCThread) it.next()).createCreateEvent());
		}
		if (debugEvents.size() > 0)
			fireEventSet((DebugEvent[]) debugEvents
					.toArray(new DebugEvent[debugEvents.size()]));
		return newThreads;
	}
	public void start(String stopSymbol, boolean resume) throws DebugException {
		ICDITargetConfiguration config = getConfiguration();
		if (config.supportsBreakpoints()) {
			getBreakpointManager().setInitialBreakpoints();

			if (stopSymbol != null && stopSymbol.length() != 0) {
				// See if the expression is a numeric address
				try {
					IAddress address = getAddressFactory().createAddress(
							stopSymbol);
					stopAtAddress(address);
				} catch (NumberFormatException nfexc) {
					// OK, expression is not a simple, absolute numeric value;
					// keep trucking and try to resolve as expression
					stopAtSymbol(stopSymbol);
				}
			}
		}
		if (config.supportsResume() && resume) {
			resume0(this);
		}
		// modified for sim type by mbl at 20110705
		else if (config.supportsResume() && (!resume))
			this.restart();
	}
	public void terminate() throws DebugException {
		if (com.bjsasc.cdt.debug.core.model.SzDebugPlugin.isUseSYMFilters()==true) {
			System.out.println("多机同步运行");
			IDebugTarget[] debugTargets = DebugPlugin.getDefault()
					.getLaunchManager().getDebugTargets();
			for (int i = 0; i < debugTargets.length; i++) {
				SzCDebugTarget cdebugTarget = (SzCDebugTarget) (debugTargets[i]);
				if(!cdebugTarget.canTerminate()){
					return;
				}
			}

			for (int i = 0; i < debugTargets.length; i++) {
				SzCDebugTarget cdebugTarget = (SzCDebugTarget) (debugTargets[i]);
				terminate0(cdebugTarget);

			}
		} else {
			super.terminate();
		}
	}
	
	public void terminate0(SzCDebugTarget ct) throws DebugException {
		if (!ct.canTerminate()) {
			return;
		}
		final CDebugElementState newState = CDebugElementState.TERMINATING;
		PrivateAccessor.callPrivateMethod(ct,org.eclipse.cdt.debug.internal.core.model.CDebugTarget.class, "changeState",  newState);
		//ct.changeState(newState);
		try {
			final ICDITarget cdiTarget = ct.getCDITarget();
			if (cdiTarget != null) {
				cdiTarget.terminate();
			}
		} catch (CDIException e) {
			if (ct.getState() == newState) {
				ct.restoreOldState();
			}
			ct.targetRequestFailed(e.getMessage(), null);
		}
	}
	
	public void resume() throws DebugException {
		if (com.bjsasc.cdt.debug.core.model.SzDebugPlugin.isUseSYMFilters()==true) {
			System.out.println("多机同步运行");

			IDebugTarget[] debugTargets = DebugPlugin.getDefault()
					.getLaunchManager().getDebugTargets();
			for (int i = 0; i < debugTargets.length; i++) {
				SzCDebugTarget cdebugTarget = (SzCDebugTarget) (debugTargets[i]);
				if(!cdebugTarget.canResume()){
					return;
				}
			}
			for (int i = 0; i < debugTargets.length; i++) {
				SzCDebugTarget cdebugTarget = (SzCDebugTarget) (debugTargets[i]);
				if(cdebugTarget.canResume())
					resume0(cdebugTarget);

			}
		} else {
			if(canResume())
				super.resume();
		}
	}
	public void resume0(SzCDebugTarget ct) throws DebugException {
		if (!ct.canResume())
			return;
		final CDebugElementState newState = CDebugElementState.RESUMING;
		PrivateAccessor.callPrivateMethod(ct,org.eclipse.cdt.debug.internal.core.model.CDebugTarget.class, "changeState",  newState);
		//ct.changeState(newState);
		try {
			final ICDITarget cdiTarget = ct.getCDITarget();
			if (cdiTarget != null) {
				cdiTarget.resume(false);
			}
		} catch (CDIException e) {
			if (ct.getState() == newState) {
				ct.restoreOldState();
			}
			ct.targetRequestFailed(e.getMessage(), null);
		}
	}
	public void suspend() throws DebugException {
		if (com.bjsasc.cdt.debug.core.model.SzDebugPlugin.isUseSYMFilters()==true) {
			System.out.println("多机同步运行");

			IDebugTarget[] debugTargets = DebugPlugin.getDefault()
					.getLaunchManager().getDebugTargets();
			for (int i = 0; i < debugTargets.length; i++) {
				SzCDebugTarget cdebugTarget = (SzCDebugTarget) (debugTargets[i]);
				if(!cdebugTarget.canSuspend()){
					return;
				}
			}
			for (int i = 0; i < debugTargets.length; i++) {
				SzCDebugTarget cdebugTarget = (SzCDebugTarget) (debugTargets[i]);
				if(cdebugTarget.canSuspend())
					suspend0(cdebugTarget);
			}
		} else {
			if(canSuspend())
				super.suspend();
		}
	}
	
	public void suspend0(SzCDebugTarget ct) throws DebugException {
		if (!ct.canSuspend())
			return;
		final CDebugElementState newState = CDebugElementState.SUSPENDING;
		PrivateAccessor.callPrivateMethod(ct,org.eclipse.cdt.debug.internal.core.model.CDebugTarget.class, "changeState",  newState);
		/*ct.changeState(newState);*/
		try {
			final ICDITarget cdiTarget = ct.getCDITarget();
			if (cdiTarget != null) {
				cdiTarget.suspend();
			}
		} catch (CDIException e) {
			if (ct.getState() == newState) {
				ct.restoreOldState();
			}
			ct.targetRequestFailed(e.getMessage(), null);
		}
	}
	public void restart() throws DebugException {
		
		if (com.bjsasc.cdt.debug.core.model.SzDebugPlugin.isUseSYMFilters()==true) {
			System.out.println("多机同步运行");

			IDebugTarget[] debugTargets = DebugPlugin.getDefault()
					.getLaunchManager().getDebugTargets();
			for (int i = 0; i < debugTargets.length; i++) {
				SzCDebugTarget cdebugTarget = (SzCDebugTarget) (debugTargets[i]);
				if(!cdebugTarget.canResume()){
					return;
				}
			}
			for (int i = 0; i < debugTargets.length; i++) {
				CDIDebugModel.postCommand0(new CLICommand("load"),debugTargets[i]);
				SzCDebugTarget cdebugTarget = (SzCDebugTarget) (debugTargets[i]);
				restart0(cdebugTarget);
			}
		} else {
			CDIDebugModel.postCommand0(new CLICommand("load"),this);
			super.restart();
		}
	}
	public void restart0(SzCDebugTarget ct) throws DebugException {
		if (!ct.canRestart()) {
			return;
		}
		final ICDITarget cdiTarget = ct.getCDITarget();
		if (cdiTarget == null) {
			return;
		}

		try {
			ILaunchConfiguration launchConfig = ct.getLaunch()
					.getLaunchConfiguration();
			if (launchConfig
					.getAttribute(
							ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN,
							ICDTLaunchConfigurationConstants.DEBUGGER_STOP_AT_MAIN_DEFAULT)) {
				String mainSymbol = launchConfig
						.getAttribute(
								ICDTLaunchConfigurationConstants.ATTR_DEBUGGER_STOP_AT_MAIN_SYMBOL,
								ICDTLaunchConfigurationConstants.DEBUGGER_STOP_AT_MAIN_SYMBOL_DEFAULT);
				ICDILocation location = null;
				// See if the expression is a numeric address
				try {
					IAddress address = ct.getAddressFactory().createAddress(
							mainSymbol);
					location = cdiTarget.createAddressLocation(address
							.getValue());
				} catch (NumberFormatException nfexc) {
					// OK, expression is not a simple, absolute numeric value;
					// keep trucking and try to resolve as expression
					location = cdiTarget.createFunctionLocation("", mainSymbol); //$NON-NLS-1$	
				}

				ct.setInternalTemporaryBreakpoint(location);
			}
		} catch (CoreException e) {
			ct.requestFailed(e.getMessage(), e);
		}

		final CDebugElementState newState = CDebugElementState.RESTARTING;
		PrivateAccessor.callPrivateMethod(ct,org.eclipse.cdt.debug.internal.core.model.CDebugTarget.class, "changeState",  newState);
		/*ct.changeState(newState);*/
		try {
			cdiTarget.restart();
		} catch (CDIException e) {
			if (ct.getState() == newState) {
				ct.restoreOldState();
			}
			ct.targetRequestFailed(e.getMessage(), e);
		}
	}
}
