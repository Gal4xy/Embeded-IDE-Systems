package com.bjsasc.debug.confirm;

import java.io.IOException;
import java.util.Random;
import java.util.TooManyListenersException;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.UnsupportedCommOperationException;
import com.bjsasc.serial.core.IComm;
import com.bjsasc.serial.core.SerialFactory;

public class DebugConfirm {
  
  /**
   * 启动多机调试以后，连接PC机端的串口
   */
  public static IComm port;
  
  /**
   * 标志位，用来标示验证是否通过
   * */
  public static boolean is_confirmed = false;
  
  public static void setConfirmed(boolean isPassed){
    is_confirmed = isPassed;
  }
  
  public static boolean getConfirmed(){
    return is_confirmed;
  }
  
  /**
   * 发送给仿真器的密钥
   */
  public static byte[] secretKey = new byte[8];
  
  public static boolean connectPort(String comPort, int baud)
      throws UnsupportedCommOperationException, PortInUseException, TooManyListenersException,
      IOException {
    // 如果串口被使用，则关闭串口
    if (port != null) {
      try {
        port.close();
        port = null;
        return false;
      }
      catch (NoSuchPortException e) {
        e.printStackTrace();
        return false;
      }
    }
    // 当port为空时才实例化串口
    port = SerialFactory.getInstance().getComm("comm");
    
    if (port == null)
      return false;
    
    // 设置串口号
    port.setPort(comPort);
    // 设置波特率
    port.setBaud(baud);
    
    if (!port.open()) {
      port = null;
      return false;
    }
    
    return true;
  }
  
  /**
   * 发送验证信息
   */
  public static void sendVerifyInfo() {
    //DTR位 先拉高再拉低进入验证模式
    port.setDTR(false);
    port.setDTR(true);
    
    // 生成密钥
    for (int i = 0; i < 8; i++) {
      secretKey[i] = (byte) new Random().nextInt(128);
    }
    
    try {
      port.sendByte(secretKey);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * 接受验证返回信息
   * 
   * @return
   */
  public static byte[] getVerifyInfo() {
    byte[] temp = new byte[8];
    try {      
      temp = port.readByte(8);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    
    return temp;
  }
  
  /**
   * 验证信息是否正确
   * 
   * @return 验证是否通过
   */
  public static boolean isConfirm() {
    // 先发送密钥
    sendVerifyInfo();
    
    byte[] backdata = new byte[8];
    backdata = getVerifyInfo();
    if(backdata == null || backdata.length != 8){
      
      try {
        port.setDTR(false);
        port.close();
        port = null;
      }
      catch (NoSuchPortException e) {
        e.printStackTrace();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
      
      return false;
    }
    // 新建一个temp数组，用来保存加密后的数组
    byte[] temp = new byte[8];
    // 将发送的数据加密，保存在temp数组中
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        temp[i] |= (secretKey[(i + j) % 8] & (0x01 << j));
      }
    }
    
    // 将获得的加密数组和temp数组中的数进行比较
    for (int k = 0; k < 8; k++) {
      if (backdata[k] != temp[k]) {
        try {
          port.setDTR(false);
          port.close();
          port = null;
        }
        catch (NoSuchPortException e) {
          e.printStackTrace();
        }
        catch (IOException e) {
          e.printStackTrace();
        }
        
        return false;
      }
    }
    
    try {
      port.close();
      port = null;
    }
    catch (NoSuchPortException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    
    return true;
  }
}
