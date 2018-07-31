package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.external;

import java.io.InputStream;
import java.io.OutputStream;
import org.eclipse.cdt.utils.spawner.Spawner;

public class SzmonLaunchProcess extends Process
{
  private Process m_process;
  private boolean isSpawner = false;

  public SzmonLaunchProcess(Process p) {
    this.m_process = p;
    this.isSpawner = this.m_process instanceof Spawner;
  }

  public void interrupt() {
    if (this.isSpawner)
      ((Spawner)this.m_process).interrupt();
  }

  public void destroy()
  {
    if (this.isSpawner) {
      ((Spawner)this.m_process).interrupt();

      this.m_process.destroy();
    } else {
      this.m_process.destroy();
    }
  }

  public synchronized int waitFor()
    throws InterruptedException
  {
    return this.m_process.waitFor();
  }

  public synchronized int exitValue()
  {
    return this.m_process.exitValue();
  }

  public InputStream getInputStream()
  {
    return this.m_process.getInputStream();
  }

  public OutputStream getOutputStream()
  {
    return this.m_process.getOutputStream();
  }

  public InputStream getErrorStream()
  {
    return this.m_process.getErrorStream();
  }
}
