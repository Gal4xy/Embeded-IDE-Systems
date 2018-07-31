package com.bjsasc.utils.select;
import java.util.ArrayList;
import java.util.List;

public class StackEnableModel {

	private final static StackEnableModel Instance = new StackEnableModel();
	
	public static StackEnableModel  getInstance(){//����
		return Instance;
	}
	
	private List<IActionEnable> reverseActionList = new ArrayList<IActionEnable>();
	
	public void addReverseAction(IActionEnable action){//ע����簴ť��action
		reverseActionList.add(action);
	}
	public void removeReverseAction(IActionEnable action){//���ע���action
		reverseActionList.remove(action);
	}
	
	/**
	 * ��ע���actionʹ��״̬ȫ����Ϊen
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
