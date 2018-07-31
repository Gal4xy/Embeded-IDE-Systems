package com.bjsasc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class CRC
{
	private short g_wCRCTable[] = new short[256] ;//crc初始化表
	
	public CRC()
	{}
	
	public int getFileCrc(String filePath) throws IOException
	{
		BuildCRCTable16();
		int nread;  
	    /*第一次传入的值需要固定,如果发送端使用该值计算crc校验码, 
	    **那么接收端也同样需要使用该值进行计算*/  
	  
	    File f = new File(filePath);
	    if(!(f.exists()))
	    {
	    	System.out.println("文件不存在");
	    	return -1;
	    }
	    byte[] contents = new byte[(int) f.length()];
	    FileInputStream 	fin 	= new FileInputStream(f);
	    nread = fin.read(contents, 0, (int) f.length());
	    int crc_result = CalCRC16UseGlobal(contents, nread);
	    return crc_result;
	    
	}
	
	private  void BuildCRCTable16()
	{
		short wIndex = 0,wSize = 0;
		short wData = 0;
		short wCRC = 0;

		//将8位二进制序列数的CRC码全部计算出来，放在CRC表中
		for(wIndex = 0;wIndex < 256; wIndex++)
		{
			wData = (short)(wIndex<<8);
			wCRC = 0;
			for(wSize = 0; wSize < 8 ;wSize++)
			{
				if(((wData^wCRC)&0x8000)!=0)
				{
					wCRC = (short) ((wCRC<<1)^(0x1021));//CRC_POLY
				}
				else
				{
					wCRC <<=1;
				}
				wData <<= 1;
			}
			wCRC&=0xffff;
			g_wCRCTable[wIndex] = wCRC;
		}
	}
	
	private  short CalCRC16UseGlobal(byte[] pbDataBuf,long dwNumOfBytes)
	{
		byte bData = 0;
		short wCRC = 0x0000;
		int j;

		//while(0!=dwNumOfBytes)
		for(int i=0;i<dwNumOfBytes;i++)
		{
			bData = ( byte)(wCRC>>>8);
			wCRC <<=8 ;
			j = bData^pbDataBuf[i];
			if(j<0)
				j = 256+j;
			wCRC =  (short) (g_wCRCTable[j]^wCRC);
		}
		return wCRC;
	}
	//Begin:delete by lichengfei at 2012-08-22
	/*public static void main(String args[]) throws IOException
	{
		CRC c = new CRC();
		int a = c.getFileCrc("D:\\w.bin");
		System.out.println("crc="+a);
	}*/
	//End:delete by lichengfei at 2012-08-22

}
