package com.bjsasc.debug.core.sourcelookup;

import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceLookupDirector;
import org.eclipse.swt.widgets.Shell;


public class CygWinSourceContainerBrowser extends
		SourceContainerBrowser
{
	public ISourceContainer[] addSourceContainers( Shell shell, ISourceLookupDirector director ) {
		return new ISourceContainer[] { new CygWinSourceContainer("Cygwin source container" ) };
	}
}
