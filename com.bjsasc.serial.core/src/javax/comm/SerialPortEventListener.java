package javax.comm;

import java.util.EventListener;

public interface SerialPortEventListener extends EventListener
{
  public abstract void serialEvent(SerialPortEvent paramSerialPortEvent);
}

/* Location:           E:\project\szide_projects_3.7_20111017\com.bjsasc.Dsuflashwriter\comm.jar
 * Qualified Name:     javax.comm.SerialPortEventListener
 * JD-Core Version:    0.6.0
 */