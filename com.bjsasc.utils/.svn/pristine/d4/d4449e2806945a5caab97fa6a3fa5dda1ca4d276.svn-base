package com.bjsasc.utils.mylist;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @说明：使用Java序列化把对象存储到文件中 再从文件中读出来 注意读取的时候 读取数据的顺序一定要和存放数据的顺序保持一致
 * 
 * @author maobaolong
 * 
 */
public class RwObj {
	private String path = "";
	private ObjectOutputStream os;

	public RwObj(String path) {
		setPath(path);
		init();
	}

	private void init() {
		try {
			os = new ObjectOutputStream(new FileOutputStream(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void writeObj(Object obj) {
		try {
			os.writeObject(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 将obj对象写进文件

	}

	public Object readObj(int index) {
		Object ret = null;
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(
					path));
			for (int i = 0; i < index - 1; i++)
				is.readObject();
			ret = is.readObject();// 从流中读取数据
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public List readObj(int index, int length) {
		List list = new ArrayList();
		ObjectInputStream is = null;
		try {

			 try {
				is= new ObjectInputStream(new FileInputStream(
						path));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < index; i++)
				try {
					is.readObject();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			for (int i = 0; i < length; i++) {
				Object ret = null;
				try {
					ret = is.readObject();
					list.add(ret);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					return list;
				}// 从流中读取数据
				
			}
			if(is!=null)
				is.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}