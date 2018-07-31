package com.bjsasc.gdbdebugger.eclipse.cdt.sourcelocator;

import java.util.ArrayList;

import org.eclipse.cdt.debug.core.CDebugCorePlugin;
import org.eclipse.cdt.debug.core.sourcelookup.MappingSourceContainer;
import org.eclipse.cdt.debug.internal.core.sourcelookup.MapEntrySourceContainer;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourcePathComputerDelegate;

public class SourcePathComputerDelegate implements
ISourcePathComputerDelegate {

	@Override
	public ISourceContainer[] computeSourceContainers(
			ILaunchConfiguration configuration, IProgressMonitor monitor)
			throws CoreException {
		// TODO Auto-generated method stub
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot rootWorkspace = workspace.getRoot();
		IPath root = rootWorkspace.getLocation();
		String device = root.getDevice();
		IPath src = null;
		
		ISourceContainer[] common = CDebugCorePlugin.getDefault().getCommonSourceLookupDirector().getSourceContainers();
		ArrayList containers = new ArrayList( common.length + 1 );
//		for ( int i = 0; i < common.length; ++i ) {
//			ISourceContainer sc = common[i];
//			if ( sc.getType().getId().equals( MappingSourceContainer.TYPE_ID ) )
//				sc = ((MappingSourceContainer)sc).copy();
//			containers.add( sc );
//		}
//		String projectName = configuration.getAttribute( ICDTLaunchConfigurationConstants.ATTR_PROJECT_NAME, (String)null );
//		if ( projectName != null ) {
//			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject( projectName );
//			if ( project.exists() ) {
//				containers.add( 0, new ProjectSourceContainer( project, true ) );
//			}
//		}
//		containers.add( new AbsolutePathSourceContainer() );
		String d = root.getDevice();
		System.out.println("d is " + d);
		System.out.println("sub d is "+d.substring(0,1));
		MappingSourceContainer paths = new MappingSourceContainer("Sources");
		paths.addMapEntry(new MapEntrySourceContainer(new Path("\\cygdrive\\"+d.substring(0,1)),new Path(d+"\\")));
		paths.addMapEntry(new MapEntrySourceContainer(new Path(d),new Path(d)));
		containers.add(paths);
		
		return (ISourceContainer[])containers.toArray( new ISourceContainer[containers.size()] );
	}

}
