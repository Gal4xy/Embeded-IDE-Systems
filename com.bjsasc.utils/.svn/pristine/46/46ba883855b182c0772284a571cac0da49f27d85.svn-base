package com.bjsasc.utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;



public class SzideSetting {
	
private static SzideSetting instance=null;
public static final String P_ISREMOVE="isRemove";
//setting.ini文件保存最近打开过的解决方案
public static String settingFileName = System.getProperty("user.dir")+"\\configuration\\.settings\\setting.ini";
/**
 * LEN表示最近打开解决方案的长度
 * */
public static int LEN=6;
public static String RESENT_SLN = "RESENT_SLN";
public static synchronized SzideSetting getInstance(){
	if(instance==null){
		instance=new SzideSetting();
	}
	return instance;
} 

	public static void getSettingToSystem(){
		
		String[] settings = getSettingToArray();
		if(settings!=null){
			for(String setting:settings){
				if(setting!=null&&setting.contains("=")){
					String[] keyValue= setting.split("=");
					if(keyValue!=null&&keyValue.length==2){
						System.setProperty(keyValue[0], keyValue[1]);
					}
				}
			}
		}
	}
	public static String[] getRecentSlnPathToArray(){
		String[] recentSlnPathArray = null;
		String[] settings = getSettingToArray();
		if(settings!=null&&settings.length>0){
			for(String setting:settings){
				if(setting!=null&&setting.contains("=")){
					//用=分割键值对
					String[] keyValue= setting.split("=");
					
					if(keyValue!=null&&keyValue.length>0)
					{
						//如果key为RESENT_SLN
						if(keyValue[0].equals(RESENT_SLN)){
							if(keyValue!=null&&keyValue.length==2){
								String recentSlnPaths=keyValue[1];
								recentSlnPaths= recentSlnPaths.trim();
								recentSlnPathArray = recentSlnPaths.split(";");
							}else{
							}
							break;
						}
					}
				}
			}
		}
		return recentSlnPathArray;
	}
	public static String getSettingContent(){
		String settingContent = FileUtil.ReadFile(settingFileName);
		return settingContent;
	}
	public static String[] getSettingToArray(){
		String settingContent = getSettingContent();
		if(settingContent==null)
			return null;
		String[] settings = settingContent.split("\r\n");
		return settings;
	}
	/**
	 * @param slnPath 新szsln位置
	 * */
	public static void setRecentSln(String slnPath){
		String[] settings = getSettingToArray();
		StringBuffer sb = new StringBuffer();
		if(settings!=null){
			//遍历Setting
			for(String setting:settings){
				//不包含=认为该条setting错误
				if(setting!=null&&setting.contains("=")){
					//用=分割键值对
					String[] keyValue= setting.split("=");
					
					if(keyValue!=null&&keyValue.length>0)
					{
						//如果key为RESENT_SLN
						if(keyValue[0].equals(RESENT_SLN)){
							if(/*keyValue!=null&&*/keyValue.length==2){
								setting = keyValue[0]+"="+appendSlnPath(keyValue[1],slnPath);
							}else{
								setting =setting+slnPath+";";
							}
						}
						sb.append(setting).append("\r\n");
					}
					
					
				}
			}
		}
		System.out.println(sb);
		FileUtil.createFile(settingFileName, sb.toString());
		
	}
	public  void removeRecentSln(String slnPath){
		String[] settings = getSettingToArray();
		StringBuffer sb = new StringBuffer();
		if(settings!=null){
			//遍历Setting
			for(String setting:settings){
				//不包含=认为该条setting错误
				if(setting!=null&&setting.contains("=")){
					//用=分割键值对
					String[] keyValue= setting.split("=");
					
					if(keyValue!=null&&keyValue.length>0)
					{
						//如果key为RESENT_SLN
						if(keyValue[0].equals(RESENT_SLN)){
							if(keyValue!=null&&keyValue.length==2){
								setting = keyValue[0]+"="+keyValue[1].replace(slnPath+";", "");
							}else{
								setting =keyValue[0]+"=";
							}
						}
						sb.append(setting).append("\r\n");
					}
					
					
				}
			}
		}
		System.out.println(sb);
		FileUtil.createFile(settingFileName, sb.toString());
		firePropertyChange(P_ISREMOVE,null,null);
	}
	
	private static String appendSlnPath(String recentSlnPath,String slnPath){
		if(recentSlnPath==null)
			return slnPath+";";
		//去掉空白字符
		slnPath = slnPath.trim();
		recentSlnPath= recentSlnPath.trim();
		if(recentSlnPath.contains(slnPath+";"))
			recentSlnPath = recentSlnPath.replace(slnPath+";", "");
		else if(recentSlnPath.contains(slnPath))
			recentSlnPath = recentSlnPath.replace(slnPath, "");
		String newSetting ="";
		String[] slns = null;
		slns = recentSlnPath.split(";");
		if(slns!=null){
			//如果长度小于LEN
			// {modified by songjie at 2012-8-23
			if(slns.length<LEN){
				if(recentSlnPath.equals(""))
					newSetting=slnPath+";";
				else if(recentSlnPath.endsWith(";"))
					if(slnPath.endsWith(";"))
						newSetting=recentSlnPath+slnPath;
					else
					    newSetting=recentSlnPath+slnPath+";";
				else
					newSetting=recentSlnPath+";"+slnPath+";";
				// }modified by songjie at 2012-8-23
			}else{//如果长度大于LEN
				for(int i=slns.length-LEN+1;i<slns.length;i++){
					newSetting+=slns[i]+";";
				}
				newSetting+=slnPath+";";
			}
			
		}else
			return slnPath+";";
		return newSetting;
	}
	private static void testappendSlnPath(){
		String recentSlnPath = "1;2;3;4;5;6;";
		String recentSlnPath2 = "1;2;";
		String recentSlnPath3 = "1";
		String recentSlnPath4 = "";
		String recentSlnPath5 = null;
		String slnPath0 = "d:\\111\\22\\33.szsln";
		String slnPath = "10";
		System.out.println(appendSlnPath(recentSlnPath,slnPath));
		System.out.println(appendSlnPath(recentSlnPath2,slnPath));
		System.out.println(appendSlnPath(recentSlnPath3,slnPath));
		System.out.println(appendSlnPath(recentSlnPath4,slnPath));
		System.out.println(appendSlnPath(recentSlnPath5,slnPath));
	}
	public static void main(String[] args){
		testappendSlnPath();
	}
	
	
	private PropertyChangeSupport listeners=new PropertyChangeSupport(this);
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	public void firePropertyChange(String propName,Object oldValue,Object newValue){
		listeners.firePropertyChange(propName, oldValue, newValue);
	}
	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}
	public void removeAllPropertyChangeListener(){
		PropertyChangeListener[] tempListeners =listeners.getPropertyChangeListeners();
		for(PropertyChangeListener pcl:tempListeners){
			listeners.removePropertyChangeListener(pcl);
		}
	}
}
