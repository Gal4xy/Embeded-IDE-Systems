package javax.comm;

import java.util.TooManyListenersException;

public abstract class SerialPort extends CommPort
{
  public static final int DATABITS_5 = 5;
  public static final int DATABITS_6 = 6;
  public static final int DATABITS_7 = 7;
  public static final int DATABITS_8 = 8;
  public static final int STOPBITS_1 = 1;
  public static final int STOPBITS_2 = 2;
  public static final int STOPBITS_1_5 = 3;
  public static final int PARITY_NONE = 0;
  public static final int PARITY_ODD = 1;
  public static final int PARITY_EVEN = 2;
  public static final int PARITY_MARK = 3;
  public static final int PARITY_SPACE = 4;
  public static final int FLOWCONTROL_NONE = 0;
  public static final int FLOWCONTROL_RTSCTS_IN = 1;
  public static final int FLOWCONTROL_RTSCTS_OUT = 2;
  public static final int FLOWCONTROL_XONXOFF_IN = 4;
  public static final int FLOWCONTROL_XONXOFF_OUT = 8;

  public abstract int getBaudRate();

  public abstract int getDataBits();

  public abstract int getStopBits();

  public abstract int getParity();

  public abstract void sendBreak(int paramInt);

  public abstract void setFlowControlMode(int paramInt)
    throws UnsupportedCommOperationException;

  public abstract int getFlowControlMode();

  /** @deprecated */
  public void setRcvFifoTrigger(int paramInt)
  {
  }

  public abstract void setSerialPortParams(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws UnsupportedCommOperationException;

  public abstract void setDTR(boolean paramBoolean);

  public abstract boolean isDTR();

  public abstract void setRTS(boolean paramBoolean);

  public abstract boolean isRTS();

  public abstract boolean isCTS();

  public abstract boolean isDSR();

  public abstract boolean isRI();

  public abstract boolean isCD();

  public abstract void addEventListener(SerialPortEventListener paramSerialPortEventListener)
    throws TooManyListenersException;

  public abstract void removeEventListener();

  public abstract void notifyOnDataAvailable(boolean paramBoolean);

  public abstract void notifyOnOutputEmpty(boolean paramBoolean);

  public abstract void notifyOnCTS(boolean paramBoolean);

  public abstract void notifyOnDSR(boolean paramBoolean);

  public abstract void notifyOnRingIndicator(boolean paramBoolean);

  public abstract void notifyOnCarrierDetect(boolean paramBoolean);

  public abstract void notifyOnOverrunError(boolean paramBoolean);

  public abstract void notifyOnParityError(boolean paramBoolean);

  public abstract void notifyOnFramingError(boolean paramBoolean);

  public abstract void notifyOnBreakInterrupt(boolean paramBoolean);
}

/* Location:           E:\project\szide_projects_3.7_20111017\com.bjsasc.Dsuflashwriter\comm.jar
 * Qualified Name:     javax.comm.SerialPort
 * JD-Core Version:    0.6.0
 */