package com.bjsasc.debug.core.mi.command;
import org.eclipse.cdt.debug.mi.core.MIException;
import org.eclipse.cdt.debug.mi.core.command.CLICommand;
import org.eclipse.cdt.debug.mi.core.output.MIInfo;
import org.eclipse.cdt.debug.mi.core.output.MIOutput;
import com.bjsasc.debug.core.mi.CLIInfoThreadInfo;

public class CLIInfoThread extends CLICommand {

	public CLIInfoThread() {
		super("info threads");
	}

	public CLIInfoThreadInfo getMIInfoThreadInfo() throws MIException {
		return (CLIInfoThreadInfo)getMIInfo();
	}
	@Override
	public MIInfo getMIInfo() throws MIException {
		MIInfo info = null;
		MIOutput out = getMIOutput();
		if (out != null) {
			info = new CLIInfoThreadInfo(out);
			if (info.isError()) {
				throwMIException(info, out);
			}
		}
		return info;
	}

}