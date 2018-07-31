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
   * ������������Ժ�����PC���˵Ĵ���
   */
  public static IComm port;
  
  /**
   * ��־λ��������ʾ��֤�Ƿ�ͨ��
   * */
  public static boolean is_confirmed = false;
  
  public static void setConfirmed(boolean isPassed){
    is_confirmed = isPassed;
  }
  
  public static boolean getConfirmed(){
    return is_confirmed;
  }
  
  /**
   * ���͸�����������Կ
   */
  public static byte[] secretKey = new byte[8];
  
  public static boolean connectPort(String comPort, int baud)
      throws UnsupportedCommOperationException, PortInUseException, TooManyListenersException,
      IOException {
    // ������ڱ�ʹ�ã���رմ���
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
    // ��portΪ��ʱ��ʵ��������
    port = SerialFactory.getInstance().getComm("comm");
    
    if (port == null)
      return false;
    
    // ���ô��ں�
    port.setPort(comPort);
    // ���ò�����
    port.setBaud(baud);
    
    if (!port.open()) {
      port = null;
      return false;
    }
    
    return true;
  }
  
  /**
   * ������֤��Ϣ
   */
  public static void sendVerifyInfo() {
    //DTRλ �����������ͽ�����֤ģʽ
    port.setDTR(false);
    port.setDTR(true);
    
    // ������Կ
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
   * ������֤������Ϣ
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
   * ��֤��Ϣ�Ƿ���ȷ
   * 
   * @return ��֤�Ƿ�ͨ��
   */
  public static boolean isConfirm() {
    // �ȷ�����Կ
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
    // �½�һ��temp���飬����������ܺ������
    byte[] temp = new byte[8];
    // �����͵����ݼ��ܣ�������temp������
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        temp[i] |= (secretKey[(i + j) % 8] & (0x01 << j));
      }
    }
    
    // ����õļ��������temp�����е������бȽ�
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
