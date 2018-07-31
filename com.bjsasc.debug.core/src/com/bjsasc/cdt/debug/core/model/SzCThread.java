package com.bjsasc.cdt.debug.core.model;

import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.model.ICDIThread;
import org.eclipse.cdt.debug.core.model.CDebugElementState;
import org.eclipse.cdt.debug.core.model.IRestart;
import org.eclipse.cdt.debug.internal.core.model.CStackFrame;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;

import com.bjsasc.utils.PrivateAccessor;

public class SzCThread extends org.eclipse.cdt.debug.internal.core.model.CThread{

	public SzCThread(SzCDebugTarget target, ICDIThread cdiThread) {
		super(target, cdiThread);
		// TODO Auto-generated constructor stub
	}
	protected void setCurrent(boolean current) {
		super.setCurrent(current);
	}
	protected void terminated() {
		super.terminated();
	}
	
	public void stepInto() throws DebugException {
		if (com.bjsasc.cdt.debug.core.model.SzDebugPlugin.isUseSYMFilters()==true) {
			System.out.println("多机同步运行");

			IDebugTarget[] debugTargets = SzDebugPlugin.getDefault()
					.getLaunchManager().getDebugTargets();
			for (int i = 0; i < debugTargets.length; i++) {
				SzCDebugTarget cdebugTarget = (SzCDebugTarget) (debugTargets[i]);
				if(!cdebugTarget.canResume()){
					return;
				}
			}
			for (int i = 0; i < debugTargets.length; i++) {
				SzCDebugTarget cdebugTarget = (SzCDebugTarget) (debugTargets[i]);
				/* modified by hblee */
				IThread[] it=cdebugTarget.getThreads();
				if(it.length==0)
					continue;
				SzCThread thread = (SzCThread) it[0];
				if (thread == null) {
					continue;
				}
				stepInto0(thread);

			}
		} else {
			super.stepInto();
		}

	}
	public void stepInto0(SzCThread ct) throws DebugException {
		/*
		 * if (this.hasStackFrames()) this.getTopStackFrame().stepInto();
		 */

		if (! ct.canStepInto())
			return;
		CDebugElementState oldState =  ct.getState();
		 ct.setState(CDebugElementState.STEPPING);
		try {
			final ICDIThread cdiThread =  ct.getCDIThread();
			if (cdiThread != null) {
				if (! ct.isInstructionsteppingEnabled()) {
					cdiThread.stepInto(1);
				} else {
					cdiThread.stepIntoInstruction(1);
				}
			}
		} catch (CDIException e) {
			 ct.setState(oldState);
			 ct.targetRequestFailed(e.getMessage(), null);
		}

	}

	public void stepOver() throws DebugException {
		if (com.bjsasc.cdt.debug.core.model.SzDebugPlugin.isUseSYMFilters()==true) {
			System.out.println("多机同步运行");

			IDebugTarget[] debugTargets = SzDebugPlugin.getDefault()
					.getLaunchManager().getDebugTargets();
			for (int i = 0; i < debugTargets.length; i++) {
				SzCDebugTarget cdebugTarget = (SzCDebugTarget) (debugTargets[i]);
				if(!cdebugTarget.canResume()){
					return;
				}
			}
			for (int i = 0; i < debugTargets.length; i++) {
				SzCDebugTarget cdebugTarget = (SzCDebugTarget) (debugTargets[i]);
				/* modified by hblee */
				IThread[] it=cdebugTarget.getThreads();
				if(it.length==0)
					continue;
				SzCThread thread = (SzCThread) it[0];
				if (thread == null) {
					continue;
				}
				stepOver0(thread);

			}
		} else {
			super.stepOver();
		}

	}
	public void stepOver0(SzCThread ct) throws DebugException {
		/*
		 * if (this.hasStackFrames()) this.getTopStackFrame().stepOver();
		 */

		if (!ct.canStepOver())
			return;
		CDebugElementState oldState = ct.getState();
		ct.setState(CDebugElementState.STEPPING);
		try {
			final ICDIThread cdiThread = ct.getCDIThread();
			if (cdiThread != null) {

				if (!ct.isInstructionsteppingEnabled()) {
					cdiThread.stepOver(1);
				} else {
					cdiThread.stepOverInstruction(1);
				}
			}
		} catch (CDIException e) {
			ct.setState(oldState);
			ct.targetRequestFailed(e.getMessage(), null);
		}

	}
	public void stepReturn() throws DebugException {
		if (com.bjsasc.cdt.debug.core.model.SzDebugPlugin.isUseSYMFilters()==true) {
			System.out.println("多机同步运行");

			IDebugTarget[] debugTargets = SzDebugPlugin.getDefault()
					.getLaunchManager().getDebugTargets();
			for (int i = 0; i < debugTargets.length; i++) {
				SzCDebugTarget cdebugTarget = (SzCDebugTarget) (debugTargets[i]);
				if(!cdebugTarget.canResume()){
					return;
				}
			}
			for (int i = 0; i < debugTargets.length; i++) {
				SzCDebugTarget cdebugTarget = (SzCDebugTarget) (debugTargets[i]);
				/* modified by hblee */
				IThread[] it=cdebugTarget.getThreads();
				if(it.length==0)
					continue;
				SzCThread thread = (SzCThread) it[0];
				if (thread == null) {
					continue;
				}
				stepReturn0(thread);

			}
		} else {
			super.stepReturn();
		}

	}
	public void stepReturn0(SzCThread ct ) throws DebugException {
		/*
		 * if (this.hasStackFrames()) this.getTopStackFrame().stepReturn();
		 */
		if (! ct.canStepReturn())
			return;
		IStackFrame[] frames = ct.getStackFrames();
		if (frames.length == 0)
			return;
		CStackFrame f = (CStackFrame) frames[0];

		CDebugElementState oldState =  ct.getState();
		 ct.setState(CDebugElementState.STEPPING);
		//f.doStepReturn();
		PrivateAccessor.callPrivateMethod(f,org.eclipse.cdt.debug.internal.core.model.CStackFrame.class, "doStepReturn",  null);
	}
	public void suspend() throws DebugException {
		// modified by mbl at 2011-09-22
		if (!canSuspend())
			return;
		this.getDebugTarget().suspend();
	}
	public void terminate() throws DebugException {
		getDebugTarget().terminate();
	}
	public void resume() throws DebugException {
		
		if (!canResume())
			return;
		this.getDebugTarget().resume();
	}
	public void restart() throws DebugException {
		if (canRestart()) {
			((IRestart) getDebugTarget()).restart();
		}
	}
}
