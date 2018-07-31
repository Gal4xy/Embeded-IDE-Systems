package com.bjsasc.gdbdebugger.sourcelookup;

import java.util.ArrayList;


public class CygWinSourcePathComputerDelegate extends
		SourcePathComputerDelegate
{
	protected void addSourceContainer(ArrayList containers)
	{
		containers.add(new CygWinSourceContainer("Cygwin型源码路径查询"));
	}

}
