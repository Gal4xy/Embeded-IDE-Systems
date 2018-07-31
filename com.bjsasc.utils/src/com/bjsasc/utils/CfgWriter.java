package com.bjsasc.utils;

import java.io.File;

public class CfgWriter {
	private String cfgPath;
	private String pluginsFolderPath;
	public CfgWriter(String cfgPath,String pluginsFolderPath){
		this.cfgPath =cfgPath;
		this.pluginsFolderPath = pluginsFolderPath;
	}
	private String getCfgNewContents(String contents ){
		StringBuffer sb = new StringBuffer();
		String[] lines = contents.split("\r\n");
		for(String line:lines){
			if(line.startsWith("#"))
				sb.append(line);
			else if(line.contains("=")){
				String[] keyValue= line.split("=");
				if(keyValue!=null&&keyValue.length>0){
					if("osgi.bundles".equalsIgnoreCase(keyValue[0])){
						String pluginsAsString=getPluginsAsString();
						sb.append("osgi.budles="+pluginsAsString);
					}else{
						sb.append(line);
					}
				}
			}
		}
		return sb.toString();
	}
	private String getPluginsAsString(){
		StringBuffer sb = new StringBuffer();
		File file = new File(this.pluginsFolderPath);
		File[] children = file.listFiles();
		for(File child:children){
			sb.append(child.getName());
			sb.append(",");
		}
		if(children.length>0){
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		return sb.toString();
	} 
	public void cfgReWriter(){
		
		String contents=FileUtil.ReadFile(this.cfgPath);
		String newContents = getCfgNewContents(contents);
		FileUtil.createFile(this.cfgPath, newContents);
		
	}
	public static void main(String[] args){
		if(args.length!=2)
			return;
		CfgWriter cfgWriter = new CfgWriter(args[0],args[1]);
		cfgWriter.cfgReWriter();
	}
}
