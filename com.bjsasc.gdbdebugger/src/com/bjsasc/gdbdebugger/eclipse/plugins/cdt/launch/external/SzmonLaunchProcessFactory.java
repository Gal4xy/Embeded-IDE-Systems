package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.external;

import java.io.File;
import java.io.IOException;
import org.eclipse.cdt.utils.spawner.ProcessFactory;

public class SzmonLaunchProcessFactory {
	private static SzmonLaunchProcessFactory instance;
	  private ProcessFactory factory;

	  private SzmonLaunchProcessFactory()
	  {
	    try
	    {
	      this.factory = ProcessFactory.getFactory();
	    }
	    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
	    {
	    }
	  }

	  public static SzmonLaunchProcessFactory getFactory() {
	    if (instance == null)
	      instance = new SzmonLaunchProcessFactory();
	    return instance;
	  }

	  public Process exec(String[] cmdarray, String[] envp, File dir) throws IOException
	  {
	    return new SzmonLaunchProcess(this.factory.exec(cmdarray, envp, dir));
	  }
}
