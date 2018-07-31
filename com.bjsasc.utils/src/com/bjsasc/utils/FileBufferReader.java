package com.bjsasc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileBufferReader {
	private File fFile;
	private byte[] fPage;
	private int curPageIndex=0;
	private int fPageSize;
	public FileBufferReader(File file,int pageSize){
		this.fFile = file;
		this.fPageSize = pageSize;
	}
	public FileBufferReader(File file){
		this(file,4*1024);
	}
	public FileBufferReader(FileBufferWriter writer){
		this.fFile = writer.getFile();
	}

	public boolean clear(){
		FileUtil.createFile(fFile.getAbsolutePath(), "");
		return fFile.delete();
	}
	private byte[] getSector(int offset,int length){
		if(offset+length>=fFile.length()){
			length=(int) (fFile.length()-offset);
		}
		byte[] data = new byte[length];
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(fFile.getAbsolutePath(), "rw");
			randomAccessFile.skipBytes(offset);
			randomAccessFile.read(data);
			randomAccessFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	public byte[] getPage(int index){
		 fPage = getSector(index*fPageSize,fPageSize);
		 return fPage;
	}
	
	public int getCurPageIndex(){
		return curPageIndex;
	}
	
	public byte[] jumpPage(int index){
		if(index<0)
			index=0;
		else if(index>getPageCount()-1)
			index=getPageCount()-1;
		this.curPageIndex = index;
		return getPage(index);
	}
	public byte[] getCurPage(){
		return getPage(curPageIndex);
	}
	public byte[] prevPage(){
		if(curPageIndex>0)
			this.curPageIndex--;
		return getPage(curPageIndex);
	}
	public byte[] nextPage(){
		if(curPageIndex<getPageCount()-1)
			this.curPageIndex++;
		return getPage(curPageIndex);
	}
	public int getPageCount(){
		long length = fFile.length();
		return (int) ((length+fPageSize-1)/fPageSize);
	}
}
