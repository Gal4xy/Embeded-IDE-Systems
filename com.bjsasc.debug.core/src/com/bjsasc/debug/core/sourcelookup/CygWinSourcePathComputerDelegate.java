package com.bjsasc.debug.core.sourcelookup;

import java.util.ArrayList;


public class CygWinSourcePathComputerDelegate extends
		SourcePathComputerDelegate
{
	protected void addSourceContainer(ArrayList containers)
	{
		containers.add(new CygWinSourceContainer("Cygwin��Դ��·����ѯ"));
	}

}
