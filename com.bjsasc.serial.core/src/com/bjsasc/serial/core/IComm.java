package com.bjsasc.serial.core;

import java.io.IOException;
import java.util.TooManyListenersException;

import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.UnsupportedCommOperationException;


public interface IComm {
	//设置串口目标
	//打开串口
    //UML视图里返回值是Boolean
	public  boolean open() throws UnsupportedCommOperationException, PortInUseException, TooManyListenersException, IOException;
	//关闭串口
	public void close()throws NoSuchPortException, IOException;
	//设置串口号
	public void setPort(String port);
	//获取串口号
	public String getPort();
	//设置波特率
	public void setBaud(int baud);
	//获取波特率
	public int getBaud();	
	//发送一个字节的数据
	public void sendByte(byte[] data) throws IOException;
	//接收一个字节的数据
	public byte[] readByte(int length) throws IOException;
	//发送一个字符
	public void sendChar(char character)throws IOException;
	//接收一个字符
	public char readChar() throws IOException;
    //发送一个整数 
	public void sendInteger(int data)throws IOException;
	//接收一个整数IOException
	public int readInteger() throws IOException;
    //发送一个长整数的数据
	public void sendLong(long data)throws IOException; 
	//接收一个长整数的数据
	public long readLong() throws IOException;
	//清除缓冲区
	public void clearBuffer() throws IOException;
	//设置DTR位置
	public void setDTR(boolean bool);
	//设置RTS位置
	public void setRTS(boolean bool);
	
}
