package com.bjsasc.serial.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import javax.comm.CommDriver;
import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

public class Comm implements IComm, SerialPortEventListener {
  
  /**
   * Timeout period for the phone to respond to jSMSEngine.
   */
  private static final int RECV_TIMEOUT = 3 * 1000;
  
  /**
   * Input/Output buffer size for serial communication.
   */
  private static final int BUFFER_SIZE = 8192;
  
  /**
   * Delay (20ms) after each character sent. Seems that some mobile phones get
   * confused if you send them the commands without any delay, even in slow
   * baud rate.
   */
  
  private String port;
  
  private SerialPort serialPort;
  
  private InputStream inStream;
  
  private OutputStream outStream;
  
  private int baud = 9600;
  
  long timeStamp = 0;
  
  public Comm() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    loadLib();
  }
  
  public void setPort(String port) {
    this.port = port;
  }
  
  public String getPort() {
    return this.port;
  }
  
  public void setBaud(int baud) {
    this.baud = baud;
  }
  
  public int getBaud() {
    return this.baud;
  }
  
  private void loadLib() throws InstantiationException, IllegalAccessException,
      ClassNotFoundException {
    // 初始化加载dll
    String driverName = "com.sun.comm.Win32Driver";
    CommDriver driver = null;
    // 装载动态链接库为win32com
    System.loadLibrary("win32com");
    // 按名取类并实例化、初始化
    driver = (CommDriver) Class.forName(driverName).newInstance();
    if (driver != null)
      driver.initialize();
    else {
      System.out.println("Win32com:实例化失败!");
    }
  }
  
  /**
   * description 向串口发送一个字节数组的数据
   * 
   * @param byte[] b 待发送的数据
   * @throws IOException
   * 
   */
  public void sendByte(byte[] b) throws IOException {
    outStream.write(b);
    outStream.flush();
  }
  
  /**
   * description 从串口读取指定长度的字节
   * 
   * @param length
   *        长度
   * @return byte[] 指定长度的内存里的数据
   * @throws IOException
   */
  public byte[] readByte(int length) throws IOException {
    int len = 0;
    int retrytime = 2;
    
    byte[] ret = new byte[length];
    
    for (int i = 0; i < length; i++) {
      // 失败超过2次则退出循环
      if (retrytime < 1) {
        break;
      }
      int ls = 0;
      // 从串口读取1字节
      ls = inStream.read();
      // 如果是缓冲区没有内容
      if (ls == -1) {
        retrytime--;
        i--;
        continue;
      }
      else {
        ret[len] = (byte) ls;
        len++;
      }
    }
    /* modified by hblee 20111005 */
    if (len < length) {
      byte[] data = new byte[len];
      System.arraycopy(ret, 0, data, 0, len);
      return data;
    }
    return ret;
    /* end of modification */
  }
  
  public void sendString(String str) throws IOException {
    if (str != null) {
      for (byte ch : str.getBytes()) {
        outStream.write((byte) ch);
      }
      outStream.flush();
    }
  }
  
  public void sendChar(char c) throws IOException {
    outStream.write((byte) c);
    outStream.flush();
  }
  
  public char readChar() throws IOException {
    
    StringBuffer buffer;
    int c;
    buffer = new StringBuffer(256);
    c = inStream.read();
    buffer.append((char) c);
    return (char) c;
  }
  
  // 发送一个int类型的数据
  public void sendInteger(int s) throws IOException {
    int len = Integer.SIZE / 8;
    byte[] b = new byte[len];
    for (int i = (len - 1); i >= 0; i--) {
      b[len - 1 - i] = (byte) (s >>> (8 * i));
    }
    sendByte(b);
  }
  
  // 读取一个int类型的数据
  public int readInteger() throws IOException {
    int len = Integer.SIZE / 8;
    byte[] data = new byte[len];
    int integerData = 0;
    data = readByte(len);
    for (int i = 0; i < len; i++)
      integerData += (data[i] << (8 * (len - 1 - i)));
    return integerData;
  }
  
  // 发送一个long类型的数据
  public void sendLong(long data) throws IOException {
    int len = Long.SIZE / 16;
    byte[] b = new byte[len];
    for (int i = (len - 1); i >= 0; i--) {
      b[len - 1 - i] = (byte) (data >>> (8 * i));
    }
    sendByte(b);
  }
  
  // 读取一个long类型的数据
  public long readLong() throws IOException {
    int len = Long.SIZE / 16;
    byte[] data = new byte[len];
    long longData = 0;
    data = readByte(len);
    if (data.length < len)
      return Long.MAX_VALUE;
    for (int i = 0; i < len; i++) {
      long data_long = (data[i] & 0xff);
      longData += (long) (data_long << (8 * (len - 1 - i)));
    }
    longData &= 0x00000000ffffffffL;
    return longData;
  }
  
  public boolean open() throws UnsupportedCommOperationException, PortInUseException,
      TooManyListenersException, IOException {
    boolean result = false;
    Enumeration<?> portList;
    
    portList = CommPortIdentifier.getPortIdentifiers();
    while (portList.hasMoreElements()) {
      CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
      if (portId == null)
        break;
      if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
        if (portId.getName().equalsIgnoreCase(port)) {
          serialPort = (SerialPort) portId.open("Modemn", 5000);
          System.out.println("serial name is :" + serialPort.getName());
          serialPort.notifyOnDataAvailable(true);
          serialPort.notifyOnOutputEmpty(true);
          serialPort.notifyOnBreakInterrupt(true);
          serialPort.notifyOnFramingError(true);
          serialPort.notifyOnOverrunError(true);
          serialPort.notifyOnParityError(true);
          serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
          serialPort.addEventListener(this);
          
          serialPort.setSerialPortParams(baud, SerialPort.DATABITS_8, // 数据位数
              SerialPort.STOPBITS_1, // 停止位
              SerialPort.PARITY_NONE); // 奇偶位
          
          serialPort.setInputBufferSize(BUFFER_SIZE);
          serialPort.setOutputBufferSize(BUFFER_SIZE);
          serialPort.enableReceiveTimeout(RECV_TIMEOUT);
          inStream = serialPort.getInputStream();
          outStream = serialPort.getOutputStream();
        
          //
          result = true;
          // added by mbl at 2010-11-30
          break;
        }
      }
    }
    return result;
  }
  
  /**
   * 设置或消除DTR位
   * 
   * @param paramBoolean 
   */
  public void setDTR(boolean paramBoolean){
    serialPort.setDTR(paramBoolean);
  }
  
  /**
   * 设置或消除RTS位置
   * 
   * @param paramBoolean
   */
  public void setRTS(boolean paramBoolean){
    serialPort.setRTS(paramBoolean);
  }
  
  public void close() throws NoSuchPortException, IOException {
    if (serialPort != null)
      serialPort.close();
  }
  
  public void serialEvent(SerialPortEvent event) {
    switch (event.getEventType()) {
      case SerialPortEvent.BI:
        break;
      case SerialPortEvent.OE:
        
        break;
      case SerialPortEvent.FE:
        
        break;
      case SerialPortEvent.PE:
        
        break;
      case SerialPortEvent.CD:
        break;
      case SerialPortEvent.CTS:
        break;
      case SerialPortEvent.DSR:
        break;
      case SerialPortEvent.RI:
        break;
      case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
        break;
      case SerialPortEvent.DATA_AVAILABLE:
        break;
    }
  }
  
  public void clearBuffer() throws IOException {
    while (dataAvailable()) {
      inStream.read();
    }
  }
  
  private boolean dataAvailable() throws IOException {
    return (inStream.available() > 0 ? true : false);
  }
}
