package com.bjsasc.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileBufferWriter {
	private File fFile;
	private byte[] fPage;
	private int curPageIndex=0;
	private int fPageSize;

	private RandomAccessFile randomAccessFile;
	public FileBufferWriter(File file,int pageSize){
		this.fFile = file;
		this.fPageSize =pageSize;
	}
	public FileBufferWriter(File file){
		this(file,  4*1024);
	}
	public File getFile(){
		return this.fFile;
	}
	public int getPageSize(){
		return this.fPageSize;
	}
	public int getCurPageIndex(){
		return curPageIndex;
	}
	public void appendPage(byte[] data){
		try {
			randomAccessFile = new RandomAccessFile(fFile.getAbsolutePath(), "rw");
			randomAccessFile.skipBytes((int) randomAccessFile.length());
			randomAccessFile.write(data, 0, data.length);
			randomAccessFile.close();
			this.fPage=data;
			curPageIndex++;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean clear(){
		FileUtil.createFile(fFile.getAbsolutePath(), "");
		curPageIndex=0;
		return fFile.delete();
	}
	
}