package com.bjsasc.utils;

public class ByteUtility {
	int index=0;
	byte[] data;
	boolean isDebug = true;
	public ByteUtility(byte[] data){
		this.data = data;
	}
	public long readLong(){
		if(isReachEnd()){
			return -1;
		}
		byte[] temp = new byte[4];
		System.arraycopy(data, index, temp, 0, 4);
		index+=4;
		long value=byte4ToLong(temp);
		if(isDebug)
			System.out.println(Long.toHexString(value));
		return value;
	}
	public boolean isReachEnd(){
		return index>=data.length;
	}
	private long byte4ToLong(byte[] data){
		if(data==null||data.length!=4){
			return -1;
		}
		long count =(((long)(0xff&data[0]))<<24)+(((long)(0xff&data[1]))<<16)+(((long)(0xff&data[2]))<<8)+((long)(0xff&data[3]));
		return count;
	}
}
