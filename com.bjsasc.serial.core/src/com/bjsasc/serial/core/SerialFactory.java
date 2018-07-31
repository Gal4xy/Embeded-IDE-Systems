package com.bjsasc.serial.core;

import java.util.HashMap;
import java.util.Map;


import com.bjsasc.serial.core.Comm;
import com.bjsasc.serial.core.IComm;

public class SerialFactory {
	protected static SerialFactory ff =new SerialFactory();
	protected Map<String, Object> modelMap = new HashMap<String, Object>();
	//ֻ�����Լ������Լ�
	protected SerialFactory()
	{
		
	}
	public static void init()
	{
		if(!(ff.getClass()==SerialFactory.class))
			ff =new SerialFactory();
	}
	//��ֻ֤��1��ʵ��
	public static SerialFactory getInstance()
	{
		return ff;
	}
	
	
	
	
	public IComm getComm(String modelName)
	{
		Object obj = modelMap.get(modelName);
		/*���MAP��û�и�ʵ��*/
		/*if(obj==null)*/
		{
			if("comm".equals(modelName)) {
				try {
					obj= new Comm();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					obj = Class.forName(modelName).newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			modelMap.put(modelName,obj);
		}
		
		return (IComm)obj;	
	}
}
