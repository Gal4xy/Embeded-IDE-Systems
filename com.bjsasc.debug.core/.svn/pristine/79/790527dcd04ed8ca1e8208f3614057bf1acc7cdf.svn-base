package com.bjsasc.debug.core;
import org.eclipse.cdt.debug.mi.core.command.CLICommand;
import org.eclipse.debug.core.model.IDebugTarget;

import com.bjsasc.utils.AbstractModel;

public class MultiTaskInfoModel  extends AbstractModel{

	private final static MultiTaskInfoModel Instance = new MultiTaskInfoModel();
	
	public static MultiTaskInfoModel  getInstance(){//单例
		return Instance;
	}
	
	public void updateEnableState(String info){
		//将info解析成Task的list,然后firePropertyChange
		firePropertyChange("update", null, info);
	}
	
	public void attachToTask(String command){
		CLICommand cmd = new CLICommand(command);
		CDIDebugModel.postCommand(cmd);
		
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		CDIDebugModel.postCommand(new CLICommand("si"));
	}
	
	public void attachToTask2(String command){
		CLICommand cmd = new CLICommand(command);
		CDIDebugModel.postCommand(cmd);
	}
}
