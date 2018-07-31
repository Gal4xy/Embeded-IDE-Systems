package com.bjsasc.utils.select;
import java.util.ArrayList;
import java.util.List;

public class StackEnableModel {

	private final static StackEnableModel Instance = new StackEnableModel();
	
	public static StackEnableModel  getInstance(){//单例
		return Instance;
	}
	
	private List<IActionEnable> reverseActionList = new ArrayList<IActionEnable>();
	
	public void addReverseAction(IActionEnable action){//注册外界按钮的action
		reverseActionList.add(action);
	}
	public void removeReverseAction(IActionEnable action){//清除注册的action
		reverseActionList.remove(action);
	}
	
	/**
	 * 将注册的action使能状态全部置为en
	 * @param en
	 */
	public void setAllActionEnable(boolean en){
		if(reverseActionList==null||reverseActionList.size()<1)
			return;
		for(IActionEnable ia:reverseActionList){
			ia.setEnable(en);
		}
	}
}
