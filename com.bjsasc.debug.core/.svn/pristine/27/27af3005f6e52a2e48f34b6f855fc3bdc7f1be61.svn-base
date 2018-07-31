package com.bjsasc.debug.core;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IProcess;


public class ProcessDebugServer implements IDebugServer {
	private IProcess fProcess;

	public ProcessDebugServer(IProcess process) {
		this.fProcess = process;
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub

	}

	@Override
	public void CreateDebugger() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		try {
			if (fProcess != null)
				fProcess.terminate();
		} catch (DebugException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub

	}

}
