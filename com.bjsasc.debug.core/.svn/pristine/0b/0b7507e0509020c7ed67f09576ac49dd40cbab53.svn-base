package com.bjsasc.debug.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.debug.mi.core.MIException;
import org.eclipse.debug.core.DebugException;

import com.bjsasc.debug.core.mi.CLIInfoRecordInfo;
import com.bjsasc.debug.core.mi.command.CLIInfoRecord;
import com.bjsasc.utils.AbstractModel;

//Begin:added by maobaolong ,lichengfei 2012-07-26
/**
 * @author  Administrator
 */
public  class ReverseEnableModel extends AbstractModel{
	/**
	 * @uml.property  name="isDebugMode"
	 */
	private boolean isDebugMode = false;
	private boolean canCoreDump = true;
	private  int initialStackFrameCount = 1;
	private boolean firstDebugMode = false;//标志是否为刚刚起调试
	private boolean multiDebug = false;//标志是否为多机调试
	/**
	 * @uml.property  name="instance"
	 * @uml.associationEnd  
	 */
	private final static ReverseEnableModel Instance = new ReverseEnableModel();
	/**
	 * @return
	 * @uml.property  name="instance"
	 */
	public static ReverseEnableModel  getInstance(){//单例
		return Instance;
	}
	/**
	 * @uml.property  name="reverseEnable"
	 * @uml.associationEnd  
	 */
	public IReverseEnable reverseEnable;
	private List<IReverseEnable> reverseActionList = new ArrayList<IReverseEnable>();
	/**
	 * @uml.property  name="info"
	 * @uml.associationEnd  
	 */
	private CLIInfoRecordInfo info;
	/**
	 * @return
	 * @uml.property  name="info"
	 */
	public CLIInfoRecordInfo getInfo() {
		return info;
	}
	public void addReverseEnableAction(IReverseEnable action){
		this.reverseEnable = action;
	}
	public void addReverseAction(IReverseEnable action){//注册外界按钮的action
		reverseActionList.add(action);
	}
	public void removeReverseAction(IReverseEnable action){//清除注册的action
		reverseActionList.remove(action);
	}
	public void setMultiDebug(boolean multi){
		multiDebug = multi;
	}
	/**
	 * @param result
	 * @uml.property  name="firstDebugMode"
	 */
	public void setFirstDebugMode(boolean result)//设置是否为刚刚启调试
	{
		this.firstDebugMode = result;
	}
	public boolean geFfirstDebugMode()//获取是否为刚刚启调试
	{
		return this.firstDebugMode;
	}
	//设置反向调试使能一刹那栈帧数目
	/**
	 * @param n
	 * @uml.property  name="initialStackFrameCount"
	 */
	public void setInitialStackFrameCount(int n)
	{
		this.initialStackFrameCount = n;
	}
	//将info的获取单独提出来
	public CLIInfoRecordInfo getInfoRecord()
	{
		CLIInfoRecord miCmd =new CLIInfoRecord(); 
		CDIDebugModel.postCommand(miCmd);
		CLIInfoRecordInfo  info = null;

		try 
		{
			info = miCmd.getMIInfoRecordInfo();
		} 
		catch (MIException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return info;
	}
	//判断当前调试使能按钮是否为check状态
	public boolean isCheckedReverseEnable()
	{
		CLIInfoRecordInfo  info = getInfoRecord();

		if(info ==null)
			return false;
		if(info.isActive())//如果反向调试使能了
			return true;
		else
			return false;
	}
	
	//设置当前是否为调试状态
	public void setIsDebugMode(boolean isDebugMode)
	{
		this.isDebugMode = isDebugMode;
	}
	/**
	 * @return
	 * @uml.property  name="isDebugMode"
	 */
	public boolean getIsDebugMode()
	{
		return this.isDebugMode;
	}
	//反向调试相关按钮设置全灰色
	public void setAllReverseDisable()
	{
		for(IReverseEnable action:reverseActionList)
		{
			action.setEnable(false);
		}
	}
	
	//设置调试模式的初始状态
	public void setInitialDebugState()
	{
		for(IReverseEnable action:reverseActionList)
		{
			if(action.reverseButtonName().equals(ReverseButtonName.REVERSE_MSG_SEND))
			{
				action.setEnable(canCoreDump);//added by lichengfei 2012-11-23
			}
			else if(action.reverseButtonName().equals(ReverseButtonName.REVERSE_INFO_RECORD))
			{
				action.setEnable(true);
			}
			else if(action.reverseButtonName().equals(ReverseButtonName.REVERSE_ENABLE))
			{
				action.setChecked(false);
				action.setEnable(true);
			}
			else
			{
				action.setEnable(false);
			}
		}
	}
	
	//根据info record返回的信息来设置反向调试各个按钮的状态，在调试过程中使用
	public void updateEnableState()
	{
		//如果当前启动调试后，关闭调试前，则向gdb发送info record命令，获取其他按钮状态信息
		if(isDebugMode&&!multiDebug)
		{
			//获取info record命令之后返回的信息
			CLIInfoRecordInfo  info = getInfoRecord();
			
			if(info ==null)
			{
				return ;
			}
			if(info.isActive())//如果反向调试check了
			{
				//遍历注册的所有action，并设置其状态
				for(IReverseEnable action:reverseActionList)
				{
					if(action.reverseButtonName().equals(ReverseButtonName.REVERSE_ENABLE))
					{
						action.setEnable(true);
						action.setChecked(true);
					}
					else if(action.reverseButtonName().equals(ReverseButtonName.REVERSE_MSG_SEND))
					{
						action.setEnable(canCoreDump);//added by lichengfei 2012-11-23
					}
					else if(action.reverseButtonName().equals(ReverseButtonName.REVERSE_INFO_RECORD))
					{
						action.setEnable(true);
					}
					else if(action.reverseButtonName().equals(ReverseButtonName.REVERSE_CONTINUE)
							||action.reverseButtonName().equals(ReverseButtonName.REVERSE_STEP_INTO)
							||action.reverseButtonName().equals(ReverseButtonName.REVERSE_STEP_OVER))
					{
						int lowest = info.getLowestRecordedInstruction();
						int current = info.getCurInstructionNumber();
						
						if(lowest<current&&lowest>0||current==lowest&&current==1)//如果不是处在反向调试第一条记录
							action.setEnable(true);
						else							 //如果是处在反向调试第一条记录
							action.setEnable(false);
					}
					/*else if(action.reverseButtonName().equals(ReverseButtonName.REVERSE_INFO_RECORD))
					{
						int lowest = info.getLowestRecordedInstruction();
						int highest = info.getHighestRecordedInstruction();
						
						if(lowest<highest||lowest==highest&&lowest==1)//如果有记录就使能
							action.setEnable(true);
						else									//无记录则不使能
							action.setEnable(false);
					}*/
					else if(action.reverseButtonName().equals(ReverseButtonName.REVERSE_STEP_RETURN))
					{
						try {
							int lowest = info.getLowestRecordedInstruction();
							int current = info.getCurInstructionNumber();
							int stackFrameLen =getStackFrameCount();
							if(stackFrameLen>1&&(lowest<current&&lowest>0||current==lowest&&current==1))//如果栈帧大于1，且有记录
							{
								action.setEnable(true);
							}
							else
							{
								action.setEnable(false);
							}
						} catch (DebugException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if(action.reverseButtonName().equals(ReverseButtonName.REVERSE_DELETE_RECORD))
					{
						int highest = info.getHighestRecordedInstruction();
						int current = info.getCurInstructionNumber();
						
						if(current>-1&&current<highest)
						{
							action.setEnable(true);
						}
						else
						{
							action.setEnable(false);
						}
					}
				}
			}
			else//如果反向调试未使能,则仅将生成转储文件按钮和调试使能按钮设置为非灰，其他都为灰色
			{
				setInitialDebugState();
			}
			firePropertyChange("update", null, info);
		}
		//如果当前为开发模式，则将反向调试的所有按钮都设置成灰色
		else
		{
			setAllReverseDisable();
		}
	}
	//返回当前栈帧数目
	public int getStackFrameCount() throws DebugException
	{
		return CDIDebugModel.getStackFrameCount();
	}
	/**
	 * @param info
	 * @uml.property  name="info"
	 */
	public void setInfo(CLIInfoRecordInfo info) {
		// TODO Auto-generated method stub
		this.info = info;
	}
	//Begin:added by lichengfei 2012-11-23
	//设置是否能够coredump操作，如果设置为false，则任何情况下均非使能该按钮
	public void setCanCoreDump(boolean result)
	{
		this.canCoreDump = result;
	}
	//End:added by lichengfei 2012-11-23
	
}
//End:added by maobaolong ,lichengfei 2012-07-26