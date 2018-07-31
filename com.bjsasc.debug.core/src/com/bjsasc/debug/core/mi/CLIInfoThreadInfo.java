package com.bjsasc.debug.core.mi;
import org.eclipse.cdt.debug.mi.core.output.MIConsoleStreamOutput;
import org.eclipse.cdt.debug.mi.core.output.MIInfo;
import org.eclipse.cdt.debug.mi.core.output.MIOOBRecord;
import org.eclipse.cdt.debug.mi.core.output.MIOutput;
import org.eclipse.cdt.debug.mi.core.output.MIStreamRecord;

public class CLIInfoThreadInfo extends MIInfo{

	private String tasksInfo; 
	
	public CLIInfoThreadInfo(MIOutput record) {
		super(record);
		parse();
	}
	
	public String getTasksInfo(){
		return this.tasksInfo;
	}
	private void parse() {
		if (isDone()) {
			MIOutput out = getMIOutput();
			MIOOBRecord[] oobs = out.getMIOOBRecords();
			String sum = "";
			for (int i = 0; i < oobs.length; i++) {
				if (oobs[i] instanceof MIConsoleStreamOutput) {
					MIStreamRecord cons = (MIStreamRecord) oobs[i];
					String str = cons.getString();
					sum = sum + str;
				}
			}
			parseLineInfo(sum.trim());
		}
	}
	
	private void parseLineInfo(String str) {
		this.tasksInfo = str;
	}


}
