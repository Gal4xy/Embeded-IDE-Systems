package com.bjsasc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class CRC
{
	private short g_wCRCTable[] = new short[256] ;//crc��ʼ����
	
	public CRC()
	{}
	
	public int getFileCrc(String filePath) throws IOException
	{
		BuildCRCTable16();
		int nread;  
	    /*��һ�δ����ֵ��Ҫ�̶�,������Ͷ�ʹ�ø�ֵ����crcУ����, 
	    **��ô���ն�Ҳͬ����Ҫʹ�ø�ֵ���м���*/  
	  
	    File f = new File(filePath);
	    if(!(f.exists()))
	    {
	    	System.out.println("�ļ�������");
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

		//��8λ��������������CRC��ȫ���������������CRC����
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
