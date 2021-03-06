package com.bjsasc.debug.core.mi;

import org.eclipse.cdt.debug.mi.core.output.MIConsoleStreamOutput;
import org.eclipse.cdt.debug.mi.core.output.MIInfo;
import org.eclipse.cdt.debug.mi.core.output.MIOOBRecord;
import org.eclipse.cdt.debug.mi.core.output.MIOutput;
import org.eclipse.cdt.debug.mi.core.output.MIStreamRecord;

public class CLIInfoRecordInfo extends MIInfo{

	
	public CLIInfoRecordInfo(MIOutput record) {
		super(record);
		// TODO Auto-generated constructor stub
		parse();
	}

	/**
	 * @uml.property  name="active"
	 */
	private boolean active = true;//反向调试使能按钮是否激活
	/**
	 * @uml.property  name="hasInstructionLogged"
	 */
	private boolean hasInstructionLogged = true;//反向调试记录是否存在
	/**
	 * @uml.property  name="curInstructionNumber"
	 */
	private int curInstructionNumber = -1;//当前指令位置为
	/**
	 * @uml.property  name="highestRecordedInstruction"
	 */
	private int highestRecordedInstruction = 0;//最高记录指令数
	/**
	 * @uml.property  name="logContainInstructionNumber"
	 */
	private int logContainInstructionNumber = 0;//记录包含指令数
	/**
	 * @uml.property  name="maxLoggedInstructionNumber"
	 */
	private int maxLoggedInstructionNumber = 0;//最大可记录指令数
	/**
	 * @uml.property  name="lowestRecordedInstruction"
	 */
	private int lowestRecordedInstruction = 0;//最低记录指令数

	protected void parse() {
		if (isDone()) {
			MIOutput out = getMIOutput();
			MIOOBRecord[] oobs = out.getMIOOBRecords();
			for (int i = 0; i < oobs.length; i++) {
				if (oobs[i] instanceof MIConsoleStreamOutput) {
					MIStreamRecord cons = (MIStreamRecord) oobs[i];
					String str = cons.getString();
					// We are interested in finding the current thread
					parseLineInfo(str.trim());
				}
			}
		}

	}

	protected void parseLineInfo(String str) {
		if (str.endsWith(".")) //$NON-NLS-1$
			str = str.substring(0, str.length() - 1);
		if (str.equalsIgnoreCase("target record is not active")) {
			active = false;
		} else if (str.equalsIgnoreCase("No instructions have been logged")) {
			hasInstructionLogged = false;
		}
		String[] strbits = str.split("\\s"); //$NON-NLS-1$
		for (int i = 0; i < strbits.length; i++) {
			if (strbits[i].equals("Current")) //$NON-NLS-1$
			{
				curInstructionNumber = i + 4 < strbits.length ? Integer
						.parseInt(strbits[i + 4]) : -1;
			} else if (strbits[i].equals("Highest")) //$NON-NLS-1$
			{
				highestRecordedInstruction = i + 5 < strbits.length ? Integer
						.parseInt(strbits[i + 5]) : -1;
			} else if (strbits[i].equals("Lowest")) //$NON-NLS-1$
			{
				lowestRecordedInstruction = i + 5 < strbits.length ? Integer
						.parseInt(strbits[i + 5]) : -1;
			} else if (strbits[i].equals("Log")) //$NON-NLS-1$
			{
				logContainInstructionNumber = i + 2 < strbits.length ? Integer
						.parseInt(strbits[i + 2]) : -1;
			} else if (strbits[i].equals("Max")) //$NON-NLS-1$
			{
				maxLoggedInstructionNumber = i + 4 < strbits.length ? Integer
						.parseInt(strbits[i + 4]) : -1;
			}else
			{
				
			}
		}
		if (curInstructionNumber == -1 && lowestRecordedInstruction > 0
				&& highestRecordedInstruction > 0) {
			curInstructionNumber = highestRecordedInstruction;
		}
	}

	/**
	 * @return
	 * @uml.property  name="active"
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @return
	 * @uml.property  name="hasInstructionLogged"
	 */
	public boolean isHasInstructionLogged() {
		//Begin:added by lichengfei 2012-08-24 反向调试未使能的时候，没有指令记录
		if(!active)
			hasInstructionLogged = false;
		//End:added by lichengfei 2012-08-24
		return hasInstructionLogged;
	}

	/**
	 * @return
	 * @uml.property  name="curInstructionNumber"
	 */
	public int getCurInstructionNumber() {
		return curInstructionNumber;
	}

	/**
	 * @return
	 * @uml.property  name="highestRecordedInstruction"
	 */
	public int getHighestRecordedInstruction() {
		return highestRecordedInstruction;
	}

	/**
	 * @return
	 * @uml.property  name="logContainInstructionNumber"
	 */
	public int getLogContainInstructionNumber() {
		return logContainInstructionNumber;
	}

	/**
	 * @return
	 * @uml.property  name="maxLoggedInstructionNumber"
	 */
	public int getMaxLoggedInstructionNumber() {
		return maxLoggedInstructionNumber;
	}

	/**
	 * @return
	 * @uml.property  name="lowestRecordedInstruction"
	 */
	public int getLowestRecordedInstruction() {
		return lowestRecordedInstruction;
	}

	// delete by lichengfei at 2012-08-22
	/*
	 * public void setLowestRecordedInstruction(int lowestRecordedInstruction) {
	 * this.lowestRecordedInstruction = lowestRecordedInstruction; }
	 */
}
