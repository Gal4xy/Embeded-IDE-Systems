package com.bjsasc.debug.core.sourcelookup;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;


public class CygWinSourceContainerType extends SourceContainerType
{
	public ISourceContainer createSourceContainer( String memento ) throws CoreException {
		return new CygWinSourceContainer("Cygwin source container");
	}

}
