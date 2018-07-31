/*******************************************************************************
 * Copyright (c) 2004, 2006-7 QNX Software Systems and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * QNX Software Systems - Initial API and implementation
 * Freescale Semiconductor - Address watchpoints, https://bugs.eclipse.org/bugs/show_bug.cgi?id=118299
 * QNX Software Systems - catchpoints - bug 226689
 *******************************************************************************/
package com.bjsasc.debug.core;

import java.math.BigInteger;
import org.eclipse.cdt.core.IAddress;
import org.eclipse.cdt.debug.core.CDebugCorePlugin;
import org.eclipse.cdt.debug.core.cdi.ICDISessionObject;
import org.eclipse.cdt.debug.core.cdi.model.ICDITarget;
import org.eclipse.cdt.debug.core.model.ICAddressBreakpoint;
import org.eclipse.cdt.debug.core.model.ICEventBreakpoint;
import org.eclipse.cdt.debug.core.model.ICFunctionBreakpoint;
import org.eclipse.cdt.debug.core.model.ICLineBreakpoint;
import org.eclipse.cdt.debug.core.model.ICStackFrame;
import org.eclipse.cdt.debug.core.model.ICWatchpoint;
import org.eclipse.cdt.debug.internal.ui.CDebugUIUtils;
import org.eclipse.cdt.debug.mi.core.MIException;
import org.eclipse.cdt.debug.mi.core.cdi.model.Target;
import org.eclipse.cdt.debug.mi.core.command.CLICommand;
import org.eclipse.cdt.debug.mi.core.command.Command;
import org.eclipse.cdt.debug.mi.core.output.MIInfo;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.Launch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.internal.ui.viewers.model.provisional.TreeModelViewer;
import org.eclipse.debug.internal.ui.views.launch.LaunchView;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import org.eclipse.cdt.core.IBinaryParser.IBinaryObject;

import com.bjsasc.cdt.debug.core.model.SzCDebugTarget;

/**
 * Provides utility methods for creating debug sessions, targets and breakpoints
 * specific to the CDI debug model.
 */
public class CDIDebugModel {
	public final static String isLaunchDoubleClick = "isLaunchDoubleClick";
	private static boolean bIsDoubleLaunch = false;

	/**
	 * Returns the identifier for the CDI debug model plug-in
	 * 
	 * @return plugin identifier
	 */
	public static String getPluginIdentifier() {
		return org.eclipse.cdt.debug.core.CDIDebugModel.getPluginIdentifier();
	}

	/**
	 * Creates and returns a debug target for the given CDI target, with the
	 * specified name, and associates it with the given process for console I/O.
	 * The debug target is added to the given launch.
	 * 
	 * @param launch
	 *            the launch the new debug target will be contained in
	 * @param project
	 *            the project to use to persist breakpoints.
	 * @param cdiTarget
	 *            the CDI target to create a debug target for
	 * @param name
	 *            the name to associate with this target, which will be returned
	 *            from <code>IDebugTarget.getName</code>.
	 * @param debuggeeProcess
	 *            the process to associate with the debug target, which will be
	 *            returned from <code>IDebugTarget.getProcess</code>
	 * @param file
	 *            the executable to debug.
	 * @param allowTerminate
	 *            allow terminate().
	 * @param allowDisconnect
	 *            allow disconnect().
	 * @param stopSymbol
	 *            place temporary breakpoint at <code>stopSymbol</code>, ignore
	 *            if <code>null</code> or empty.
	 * @param resumeTarget
	 *            resume target.
	 * @return a debug target
	 * @throws DebugException
	 * @since 3.1
	 */
	public static IDebugTarget newDebugTarget(final ILaunch launch,
			final IProject project, final ICDITarget cdiTarget,
			final String name, final IProcess debuggeeProcess,
			final IBinaryObject file, final boolean allowTerminate,
			final boolean allowDisconnect, final String stopSymbol,
			final boolean resumeTarget) throws DebugException {

		final IDebugTarget[] target = new IDebugTarget[1];
		IWorkspaceRunnable r = new IWorkspaceRunnable() {

			public void run(IProgressMonitor m) throws CoreException {
				target[0] = new SzCDebugTarget(launch, project, cdiTarget,
						name, debuggeeProcess, file, allowTerminate,
						allowDisconnect);
				((SzCDebugTarget) target[0]).start(stopSymbol, resumeTarget);
			}
		};
		try {
			ResourcesPlugin.getWorkspace().run(r, null);
		} catch (CoreException e) {
			CDebugCorePlugin.log(e);
			throw new DebugException(e.getStatus());
		}
		return target[0];
	}

	/**
	 * Creates and returns a debug target for the given CDI target, with the
	 * specified name, and associates it with the given process for console I/O.
	 * The debug target is added to the given launch.
	 * 
	 * @param launch
	 *            the launch the new debug target will be contained in
	 * @param project
	 *            the project to use to persist breakpoints.
	 * @param cdiTarget
	 *            the CDI target to create a debug target for
	 * @param name
	 *            the name to associate with this target, which will be returned
	 *            from <code>IDebugTarget.getName</code>.
	 * @param debuggeeProcess
	 *            the process to associate with the debug target, which will be
	 *            returned from <code>IDebugTarget.getProcess</code>
	 * @param file
	 *            the executable to debug.
	 * @param allowTerminate
	 *            allow terminate().
	 * @param allowDisconnect
	 *            allow disconnect().
	 * @param stopInMain
	 *            place temporary breakpoint at main()
	 * @param resumeTarget
	 *            resume target.
	 * @return a debug target
	 * @throws DebugException
	 * @deprecated
	 */
	@Deprecated
	public static IDebugTarget newDebugTarget(final ILaunch launch,
			final IProject project, final ICDITarget cdiTarget,
			final String name, final IProcess debuggeeProcess,
			final IBinaryObject file, final boolean allowTerminate,
			final boolean allowDisconnect, final boolean stopInMain,
			final boolean resumeTarget) throws DebugException {

		return org.eclipse.cdt.debug.core.CDIDebugModel.newDebugTarget(launch,
				project, cdiTarget, name, debuggeeProcess, file,
				allowTerminate, allowDisconnect, resumeTarget);
	}

	/**
	 * Creates and returns a debug target for the given CDI target, with the
	 * specified name, and associates it with the given process for console I/O.
	 * The debug target is added to the given launch.
	 * 
	 * @param launch
	 *            the launch the new debug target will be contained in
	 * @param project
	 *            the project to use to persist breakpoints.
	 * @param cdiTarget
	 *            the CDI target to create a debug target for
	 * @param name
	 *            the name to associate with this target, which will be returned
	 *            from <code>IDebugTarget.getName</code>.
	 * @param debuggeeProcess
	 *            the process to associate with the debug target, which will be
	 *            returned from <code>IDebugTarget.getProcess</code>
	 * @param file
	 *            the executable to debug.
	 * @param allowTerminate
	 *            allow terminate().
	 * @param allowDisconnect
	 *            allow disconnect().
	 * @param resumeTarget
	 *            resume target.
	 * @return a debug target
	 * @throws DebugException
	 */
	public static IDebugTarget newDebugTarget(ILaunch launch, IProject project,
			ICDITarget cdiTarget, final String name, IProcess debuggeeProcess,
			IBinaryObject file, boolean allowTerminate,
			boolean allowDisconnect, boolean resumeTarget)
			throws DebugException {
		return org.eclipse.cdt.debug.core.CDIDebugModel.newDebugTarget(launch,
				project, cdiTarget, name, debuggeeProcess, file,
				allowTerminate, allowDisconnect, resumeTarget);
	}

	/**
	 * Creates and returns a line breakpoint for the source defined by the given
	 * source handle, at the given line number. The marker associated with the
	 * breakpoint will be created on the specified resource.
	 * 
	 * @param sourceHandle
	 *            the handle to the breakpoint source
	 * @param resource
	 *            the resource on which to create the associated breakpoint
	 *            marker
	 * @param lineNumber
	 *            the line number on which the breakpoint is set - line numbers
	 *            are 1 based, associated with the source file in which the
	 *            breakpoint is set
	 * @param enabled
	 *            whether to enable or disable this breakpoint
	 * @param ignoreCount
	 *            the number of times this breakpoint will be ignored
	 * @param condition
	 *            the breakpoint condition
	 * @param register
	 *            whether to add this breakpoint to the breakpoint manager
	 * @return a line breakpoint
	 * @throws CoreException
	 *             if this method fails. Reasons include:
	 *             <ul>
	 *             <li>Failure creating underlying marker. The exception's
	 *             status contains the underlying exception responsible for the
	 *             failure.</li>
	 *             </ul>
	 * @deprecated as of CDT 5.0 use
	 *             {@link #createLineBreakpoint(String, IResource, int, int, boolean, int, String, boolean)}
	 */
	@Deprecated
	public static ICLineBreakpoint createLineBreakpoint(String sourceHandle,
			IResource resource, int lineNumber, boolean enabled,
			int ignoreCount, String condition, boolean register)
			throws CoreException {
		return org.eclipse.cdt.debug.core.CDIDebugModel.createLineBreakpoint(
				sourceHandle, resource, lineNumber, enabled, ignoreCount,
				condition, register);
	}

	/**
	 * Creates and returns a line breakpoint for the source defined by the given
	 * source handle, at the given line number. The marker associated with the
	 * breakpoint will be created on the specified resource.
	 * 
	 * @param sourceHandle
	 *            the handle to the breakpoint source
	 * @param resource
	 *            the resource on which to create the associated breakpoint
	 *            marker
	 * @param type
	 *            a type constant from ICBreakpointType
	 * @param lineNumber
	 *            the line number on which the breakpoint is set - line numbers
	 *            are 1 based, associated with the source file in which the
	 *            breakpoint is set
	 * @param enabled
	 *            whether to enable or disable this breakpoint
	 * @param ignoreCount
	 *            the number of times this breakpoint will be ignored
	 * @param condition
	 *            the breakpoint condition
	 * @param register
	 *            whether to add this breakpoint to the breakpoint manager
	 * @return a line breakpoint
	 * @throws CoreException
	 *             if this method fails. Reasons include:
	 *             <ul>
	 *             <li>Failure creating underlying marker. The exception's
	 *             status contains the underlying exception responsible for the
	 *             failure.</li>
	 *             </ul>
	 */
	public static ICLineBreakpoint createLineBreakpoint(String sourceHandle,
			IResource resource, int type, int lineNumber, boolean enabled,
			int ignoreCount, String condition, boolean register)
			throws CoreException {

		return org.eclipse.cdt.debug.core.CDIDebugModel.createLineBreakpoint(
				sourceHandle, resource, type, lineNumber, enabled, ignoreCount,
				condition, register);
	}

	/**
	 * Creates and returns an address breakpoint for the source defined by the
	 * given source handle, at the given address. The marker associated with the
	 * breakpoint will be created on the specified resource.
	 * 
	 * @param module
	 *            the module name the breakpoint is set in
	 * @param sourceHandle
	 *            the handle to the breakpoint source
	 * @param resource
	 *            the resource on which to create the associated breakpoint
	 *            marker
	 * @param address
	 *            the address on which the breakpoint is set
	 * @param enabled
	 *            whether to enable or disable this breakpoint
	 * @param ignoreCount
	 *            the number of times this breakpoint will be ignored
	 * @param condition
	 *            the breakpoint condition
	 * @param register
	 *            whether to add this breakpoint to the breakpoint manager
	 * @return an address breakpoint
	 * @throws CoreException
	 *             if this method fails. Reasons include:
	 *             <ul>
	 *             <li>Failure creating underlying marker. The exception's
	 *             status contains the underlying exception responsible for the
	 *             failure.</li>
	 *             </ul>
	 * @deprecated as of CDT 5.0 use
	 *             {@link #createAddressBreakpoint(String, String, IResource, int, int, IAddress, boolean, int, String, boolean)}
	 */
	@Deprecated
	public static ICAddressBreakpoint createAddressBreakpoint(String module,
			String sourceHandle, IResource resource, IAddress address,
			boolean enabled, int ignoreCount, String condition, boolean register)
			throws CoreException {
		return org.eclipse.cdt.debug.core.CDIDebugModel
				.createAddressBreakpoint(module, sourceHandle, resource,
						address, enabled, ignoreCount, condition, register);
	}

	/**
	 * Creates and returns an address breakpoint for the source defined by the
	 * given source handle, at the given address. The marker associated with the
	 * breakpoint will be created on the specified resource.
	 * 
	 * @param module
	 *            the module name the breakpoint is set in
	 * @param sourceHandle
	 *            the handle to the breakpoint source
	 * @param resource
	 *            the resource on which to create the associated breakpoint
	 *            marker
	 * @param type
	 *            a type constant from ICBreakpointType
	 * @param address
	 *            the address on which the breakpoint is set
	 * @param enabled
	 *            whether to enable or disable this breakpoint
	 * @param ignoreCount
	 *            the number of times this breakpoint will be ignored
	 * @param condition
	 *            the breakpoint condition
	 * @param register
	 *            whether to add this breakpoint to the breakpoint manager
	 * @return an address breakpoint
	 * @throws CoreException
	 *             if this method fails. Reasons include:
	 *             <ul>
	 *             <li>Failure creating underlying marker. The exception's
	 *             status contains the underlying exception responsible for the
	 *             failure.</li>
	 *             </ul>
	 */
	public static ICAddressBreakpoint createAddressBreakpoint(String module,
			String sourceHandle, IResource resource, int type,
			IAddress address, boolean enabled, int ignoreCount,
			String condition, boolean register) throws CoreException {
		return createAddressBreakpoint(module, sourceHandle, resource, type,
				-1, address, enabled, ignoreCount, condition, register);
	}

	/**
	 * Creates and returns an address breakpoint for the source defined by the
	 * given source handle, at the given address. The marker associated with the
	 * breakpoint will be created on the specified resource.
	 * 
	 * @param module
	 *            the module name the breakpoint is set in
	 * @param sourceHandle
	 *            the handle to the breakpoint source
	 * @param resource
	 *            the resource on which to create the associated breakpoint
	 *            marker
	 * @param type
	 *            a type constant from ICBreakpointType
	 * @param lineNumber
	 *            the line number in the source file
	 * @param address
	 *            the address on which the breakpoint is set
	 * @param enabled
	 *            whether to enable or disable this breakpoint
	 * @param ignoreCount
	 *            the number of times this breakpoint will be ignored
	 * @param condition
	 *            the breakpoint condition
	 * @param register
	 *            whether to add this breakpoint to the breakpoint manager
	 * @return an address breakpoint
	 * @throws CoreException
	 *             if this method fails. Reasons include:
	 *             <ul>
	 *             <li>Failure creating underlying marker. The exception's
	 *             status contains the underlying exception responsible for the
	 *             failure.</li>
	 *             </ul>
	 */
	public static ICAddressBreakpoint createAddressBreakpoint(String module,
			String sourceHandle, IResource resource, int type, int lineNumber,
			IAddress address, boolean enabled, int ignoreCount,
			String condition, boolean register) throws CoreException {
		return org.eclipse.cdt.debug.core.CDIDebugModel
				.createAddressBreakpoint(module, sourceHandle, resource, type,
						lineNumber, address, enabled, ignoreCount, condition,
						register);
	}

	/**
	 * Creates and returns a watchpoint for the source defined by the given
	 * source handle, at the given expression. The marker associated with the
	 * watchpoint will be created on the specified resource.
	 * 
	 * @param sourceHandle
	 *            the handle to the watchpoint source
	 * @param resource
	 *            the resource on which to create the associated watchpoint
	 *            marker
	 * @param writeAccess
	 *            whether this is write watchpoint
	 * @param readAccess
	 *            whether this is read watchpoint
	 * @param expression
	 *            the expression on which the watchpoint is set
	 * @param enabled
	 *            whether to enable or disable this breakpoint
	 * @param ignoreCount
	 *            the number of times this breakpoint will be ignored
	 * @param condition
	 *            the breakpoint condition
	 * @param register
	 *            whether to add this breakpoint to the breakpoint manager
	 * @return a watchpoint
	 * @throws CoreException
	 *             if this method fails. Reasons include:
	 *             <ul>
	 *             <li>Failure creating underlying marker. The exception's
	 *             status contains the underlying exception responsible for the
	 *             failure.</li>
	 *             </ul>
	 */
	public static ICWatchpoint createWatchpoint(String sourceHandle,
			IResource resource, boolean writeAccess, boolean readAccess,
			String expression, boolean enabled, int ignoreCount,
			String condition, boolean register) throws CoreException {

		return org.eclipse.cdt.debug.core.CDIDebugModel.createWatchpoint(
				sourceHandle, resource, writeAccess, readAccess, expression,
				enabled, ignoreCount, condition, register);
	}

	/**
	 * Creates and returns a watchpoint for the source defined by the given
	 * source handle, at the given expression. The marker associated with the
	 * watchpoint will be created on the specified resource.
	 * 
	 * @param sourceHandle
	 *            the handle to the watchpoint source
	 * @param resource
	 *            the resource on which to create the associated watchpoint
	 *            marker
	 * @param charStart
	 *            the first character index associated with the watchpoint, or
	 *            -1 if unspecified, in the source file in which the watchpoint
	 *            is set
	 * @param charEnd
	 *            the last character index associated with the watchpoint, or -1
	 *            if unspecified, in the source file in which the watchpoint is
	 *            set
	 * @param lineNumber
	 *            the lineNumber on which the watchpoint is set, or -1 if
	 *            unspecified - line numbers are 1 based, associated with the
	 *            source file in which the watchpoint is set
	 * @param writeAccess
	 *            whether this is write watchpoint
	 * @param readAccess
	 *            whether this is read watchpoint
	 * @param expression
	 *            the expression on which the watchpoint is set
	 * @param memorySpace
	 *            the memory space in which the watchpoint is set
	 * @param range
	 *            the range of the watchpoint in addressable units
	 * @param enabled
	 *            whether to enable or disable this breakpoint
	 * @param ignoreCount
	 *            the number of times this breakpoint will be ignored
	 * @param condition
	 *            the breakpoint condition
	 * @param register
	 *            whether to add this breakpoint to the breakpoint manager
	 * @return a watchpoint
	 * @throws CoreException
	 *             if this method fails. Reasons include:
	 *             <ul>
	 *             <li>Failure creating underlying marker. The exception's
	 *             status contains the underlying exception responsible for the
	 *             failure.</li>
	 *             </ul>
	 */
	public static ICWatchpoint createWatchpoint(String sourceHandle,
			IResource resource, int charStart, int charEnd, int lineNumber,
			boolean writeAccess, boolean readAccess, String expression,
			String memorySpace, BigInteger range, boolean enabled,
			int ignoreCount, String condition, boolean register)
			throws CoreException {

		return org.eclipse.cdt.debug.core.CDIDebugModel.createWatchpoint(
				sourceHandle, resource, writeAccess, readAccess, expression,
				memorySpace, range, enabled, ignoreCount, condition, register);
	}

	/**
	 * Creates and returns a watchpoint for the source defined by the given
	 * source handle, at the given expression and over the given range. The
	 * marker associated with the watchpoint will be created on the specified
	 * resource.
	 * 
	 * @param sourceHandle
	 *            the handle to the watchpoint source
	 * @param resource
	 *            the resource on which to create the associated watchpoint
	 *            marker
	 * @param writeAccess
	 *            whether this is write watchpoint
	 * @param readAccess
	 *            whether this is read watchpoint
	 * @param expression
	 *            the expression on which the watchpoint is set
	 * @param memorySpace
	 *            the memory space in which the watchpoint is set
	 * @param range
	 *            the range of the watchpoint in addressable units
	 * @param enabled
	 *            whether to enable or disable this breakpoint
	 * @param ignoreCount
	 *            the number of times this breakpoint will be ignored
	 * @param condition
	 *            the breakpoint condition
	 * @param register
	 *            whether to add this breakpoint to the breakpoint manager
	 * @return the watchpoint that was created
	 * @throws CoreException
	 *             if this method fails. Reasons include:
	 *             <ul>
	 *             <li>Failure creating underlying marker. The exception's
	 *             status contains the underlying exception responsible for the
	 *             failure.</li>
	 *             </ul>
	 */
	public static ICWatchpoint createWatchpoint(String sourceHandle,
			IResource resource, boolean writeAccess, boolean readAccess,
			String expression, String memorySpace, BigInteger range,
			boolean enabled, int ignoreCount, String condition, boolean register)
			throws CoreException {
		return org.eclipse.cdt.debug.core.CDIDebugModel.createWatchpoint(
				sourceHandle, resource, writeAccess, readAccess, expression,
				memorySpace, range, enabled, ignoreCount, condition, register);
	}

	/**
	 * Creates and returns a breakpoint for the function defined by the given
	 * name. The marker associated with the breakpoint will be created on the
	 * specified resource.
	 * 
	 * @param sourceHandle
	 *            the handle to the breakpoint source
	 * @param resource
	 *            the resource on which to create the associated breakpoint
	 *            marker
	 * @param function
	 *            the name of the function this breakpoint suspends execution in
	 * @param charStart
	 *            the first character index associated with the breakpoint, or
	 *            -1 if unspecified, in the source file in which the breakpoint
	 *            is set
	 * @param charEnd
	 *            the last character index associated with the breakpoint, or -1
	 *            if unspecified, in the source file in which the breakpoint is
	 *            set
	 * @param lineNumber
	 *            the lineNumber on which the breakpoint is set, or -1 if
	 *            unspecified - line numbers are 1 based, associated with the
	 *            source file in which the breakpoint is set
	 * @param enabled
	 *            whether to enable or disable this breakpoint
	 * @param ignoreCount
	 *            the number of times this breakpoint will be ignored
	 * @param condition
	 *            the breakpoint condition
	 * @param register
	 *            whether to add this breakpoint to the breakpoint manager
	 * @return an address breakpoint
	 * @throws CoreException
	 *             if this method fails. Reasons include:
	 *             <ul>
	 *             <li>Failure creating underlying marker. The exception's
	 *             status contains the underlying exception responsible for the
	 *             failure.</li>
	 *             </ul>
	 * @deprecated as of CDT 5.0 use
	 *             {@link #createFunctionBreakpoint(String, IResource, int, String, int, int, int, boolean, int, String, boolean)}
	 */
	@Deprecated
	public static ICFunctionBreakpoint createFunctionBreakpoint(
			String sourceHandle, IResource resource, String function,
			int charStart, int charEnd, int lineNumber, boolean enabled,
			int ignoreCount, String condition, boolean register)
			throws CoreException {
		return org.eclipse.cdt.debug.core.CDIDebugModel
				.createFunctionBreakpoint(sourceHandle, resource, function,
						charStart, charEnd, lineNumber, enabled, ignoreCount,
						condition, register);
	}

	/**
	 * Creates and returns a breakpoint for the function defined by the given
	 * name. The marker associated with the breakpoint will be created on the
	 * specified resource.
	 * 
	 * @param sourceHandle
	 *            the handle to the breakpoint source
	 * @param resource
	 *            the resource on which to create the associated breakpoint
	 *            marker
	 * @param type
	 *            a type constant from ICBreakpointType
	 * @param function
	 *            the name of the function this breakpoint suspends execution in
	 * @param charStart
	 *            the first character index associated with the breakpoint, or
	 *            -1 if unspecified, in the source file in which the breakpoint
	 *            is set
	 * @param charEnd
	 *            the last character index associated with the breakpoint, or -1
	 *            if unspecified, in the source file in which the breakpoint is
	 *            set
	 * @param lineNumber
	 *            the lineNumber on which the breakpoint is set, or -1 if
	 *            unspecified - line numbers are 1 based, associated with the
	 *            source file in which the breakpoint is set
	 * @param enabled
	 *            whether to enable or disable this breakpoint
	 * @param ignoreCount
	 *            the number of times this breakpoint will be ignored
	 * @param condition
	 *            the breakpoint condition
	 * @param register
	 *            whether to add this breakpoint to the breakpoint manager
	 * @return an address breakpoint
	 * @throws CoreException
	 *             if this method fails. Reasons include:
	 *             <ul>
	 *             <li>Failure creating underlying marker. The exception's
	 *             status contains the underlying exception responsible for the
	 *             failure.</li>
	 *             </ul>
	 */
	public static ICFunctionBreakpoint createFunctionBreakpoint(
			String sourceHandle, IResource resource, int type, String function,
			int charStart, int charEnd, int lineNumber, boolean enabled,
			int ignoreCount, String condition, boolean register)
			throws CoreException {

		return org.eclipse.cdt.debug.core.CDIDebugModel
				.createFunctionBreakpoint(sourceHandle, resource, type,
						function, charStart, charEnd, lineNumber, enabled,
						ignoreCount, condition, register);
	}

	/**
	 * Returns the line breakpoint that is already registered with the
	 * breakpoint manager for a source with the given handle and the given
	 * resource at the given line number.
	 * 
	 * @param sourceHandle
	 *            the source handle
	 * @param resource
	 *            the breakpoint resource
	 * @param lineNumber
	 *            the line number
	 * @return the line breakpoint that is already registered with the
	 *         breakpoint manager or <code>null</code> if no such breakpoint is
	 *         registered
	 * @exception CoreException
	 *                if unable to retrieve the associated marker attributes
	 *                (line number).
	 */
	public static ICLineBreakpoint lineBreakpointExists(String sourceHandle,
			IResource resource, int lineNumber) throws CoreException {

		return org.eclipse.cdt.debug.core.CDIDebugModel.lineBreakpointExists(
				sourceHandle, resource, lineNumber);
	}

	/**
	 * Returns the watchpoint that is already registered with the breakpoint
	 * manager for a source with the given handle and the given resource at the
	 * given expression.
	 * 
	 * @param sourceHandle
	 *            the source handle
	 * @param resource
	 *            the breakpoint resource
	 * @param expression
	 *            the expression
	 * @return the watchpoint that is already registered with the breakpoint
	 *         manager or <code>null</code> if no such watchpoint is registered
	 * @exception CoreException
	 *                if unable to retrieve the associated marker attributes
	 *                (line number).
	 */
	public static ICWatchpoint watchpointExists(String sourceHandle,
			IResource resource, String expression) throws CoreException {
		return org.eclipse.cdt.debug.core.CDIDebugModel.watchpointExists(
				sourceHandle, resource, expression);
	}

	/**
	 * Returns the function breakpoint that is already registered with the
	 * breakpoint manager for a source with the given handle and the given
	 * resource with the given function name.
	 * 
	 * @param sourceHandle
	 *            the source handle
	 * @param resource
	 *            the breakpoint resource
	 * @param function
	 *            the fully qualified function name
	 * @return the breakpoint that is already registered with the breakpoint
	 *         manager or <code>null</code> if no such breakpoint is registered
	 * @exception CoreException
	 *                if unable to retrieve the associated marker attributes
	 *                (line number).
	 */
	public static ICFunctionBreakpoint functionBreakpointExists(
			String sourceHandle, IResource resource, String function)
			throws CoreException {
		return org.eclipse.cdt.debug.core.CDIDebugModel
				.functionBreakpointExists(sourceHandle, resource, function);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static IDebugTarget newDebugTarget(ILaunch launch,
			ICDITarget target, String name, IProcess iprocess,
			IProcess debuggerProcess, IFile file, boolean allowTerminate,
			boolean allowDisconnect, boolean stopInMain) throws CoreException {
		return org.eclipse.cdt.debug.core.CDIDebugModel.newDebugTarget(launch,
				target, name, iprocess, debuggerProcess, file, allowTerminate,
				allowDisconnect, stopInMain);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static IDebugTarget newAttachDebugTarget(ILaunch launch,
			ICDITarget target, String name, IProcess debuggerProcess, IFile file)
			throws CoreException {
		return org.eclipse.cdt.debug.core.CDIDebugModel.newAttachDebugTarget(
				launch, target, name, debuggerProcess, file);
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public static IDebugTarget newCoreFileDebugTarget(final ILaunch launch,
			final ICDITarget target, final String name,
			final IProcess debuggerProcess, final IFile file)
			throws CoreException {
		return org.eclipse.cdt.debug.core.CDIDebugModel.newCoreFileDebugTarget(
				launch, target, name, debuggerProcess, file);
	}

	public static ICEventBreakpoint eventBreakpointExists(String type,
			String arg) throws CoreException {

		return org.eclipse.cdt.debug.core.CDIDebugModel.eventBreakpointExists(
				type, arg);
	}

	public static ICEventBreakpoint createEventBreakpoint(String type,
			String arg, boolean register) throws CoreException {

		return org.eclipse.cdt.debug.core.CDIDebugModel.createEventBreakpoint(
				type, arg, register);

	}

	public static void postCommand(final Command gdb_cmd) {
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ISelection selection = PlatformUI.getWorkbench()
						.getWorkbenchWindows()[0].getActivePage()
						.getSelection();
				Object obj = null;
				if (selection instanceof IStructuredSelection) {
					IStructuredSelection ss = (IStructuredSelection) selection;
					if (!ss.isEmpty())
						obj = ss.getFirstElement();
				} else {
					selection = null;
				}
				if (obj instanceof IStackFrame) {
					IDebugTarget ict = ((IStackFrame) obj).getDebugTarget();
					postCommand0(gdb_cmd, ict);
				} else if (obj instanceof IThread) {
					IDebugTarget ict = ((IThread) obj).getDebugTarget();
					postCommand0(gdb_cmd, ict);
				} else if (obj instanceof IDebugTarget) {
					postCommand0(gdb_cmd, (IDebugTarget) obj);
				} else if (obj instanceof Launch) {
					Launch acd = (Launch) obj;
					postCommand0(gdb_cmd, acd.getDebugTarget());
				} else {
					if (DebugPlugin.getDefault().getLaunchManager()
							.getLaunches().length == 0) {
						return;
					}
					IDebugTarget ict = DebugPlugin.getDefault()
							.getLaunchManager().getLaunches()[0]
							.getDebugTarget();
					postCommand0(gdb_cmd, ict);
				}
			}
		});

	}

	@Deprecated
	public static void postMyCommand(Command gdb_cmd) {
		final Object[] obj = new Object[] { null };

		try {
			ISelection selection = PlatformUI.getWorkbench()
					.getWorkbenchWindows()[0].getActivePage().getSelection();
			boolean ret = true;
			if (!(selection instanceof IStructuredSelection)) {
				LaunchView debugview = (LaunchView) PlatformUI.getWorkbench()
						.getWorkbenchWindows()[0].getActivePage().findView(
						"org.eclipse.debug.ui.DebugView");
				// 获取debug树
				TreeModelViewer treeviewer = (TreeModelViewer) debugview
						.getViewer();
				treeviewer.refresh();
				selection = treeviewer.getSelection();
			}
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection ss = (IStructuredSelection) selection;
				if (!ss.isEmpty())
					obj[0] = ss.getFirstElement();
			} else {
				selection = null;
			}
		} catch (SWTException swte) {
			Display.getDefault().asyncExec(new Thread() {
				public void run() {
					boolean ret = true;
					ISelection selection = null;
					/* if (!(selection instanceof IStructuredSelection)){ */
					LaunchView debugview = (LaunchView) PlatformUI
							.getWorkbench().getWorkbenchWindows()[0]
							.getActivePage().findView(
									"org.eclipse.debug.ui.DebugView");
					// 获取debug树
					if (debugview == null)
						return;
					TreeModelViewer treeviewer = (TreeModelViewer) debugview
							.getViewer();
					treeviewer.refresh();
					selection = treeviewer.getSelection();
					/* } */
					if (selection instanceof IStructuredSelection) {
						IStructuredSelection ss = (IStructuredSelection) selection;
						if (!ss.isEmpty())
							obj[0] = ss.getFirstElement();
					} else {
						selection = null;
						obj[0] = new Object();
					}
				}
			});
			// swte.printStackTrace();
		}
		int i = 0;
		while (obj[0] == null && i < 5) {
			try {

				Thread.sleep(100);
				i++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (obj[0] == null) {
			return;
		}
		if (obj[0] instanceof IStackFrame) {
			IDebugTarget ict = ((IStackFrame) obj[0]).getDebugTarget();
			postCommand0(gdb_cmd, ict);
		} else if (obj[0] instanceof IThread) {
			IDebugTarget ict = ((IThread) obj[0]).getDebugTarget();
			postCommand0(gdb_cmd, ict);
		} else if (obj[0] instanceof IDebugTarget) {
			postCommand0(gdb_cmd, (IDebugTarget) obj[0]);
		} else if (obj[0] instanceof Launch) {
			Launch acd = (Launch) obj[0];
			postCommand0(gdb_cmd, acd.getDebugTarget());
		} else {
			if (DebugPlugin.getDefault().getLaunchManager().getLaunches().length == 0) {
				return;
			}
			IDebugTarget ict = DebugPlugin.getDefault().getLaunchManager()
					.getLaunches()[0].getDebugTarget();
			postCommand0(gdb_cmd, ict);
		}
	}

	// Begin:added by lichengfei 2012-07-24
	// 返回当前栈帧数目,如果为-1则表示错误，返回值为0无栈帧，n>0表示多个栈帧
	public static int getStackFrameCount() throws DebugException {
		int stackFrameCount = -1;
		final Object[] obj = new Object[] { null };

		try {
			ISelection selection = PlatformUI.getWorkbench()
					.getWorkbenchWindows()[0].getActivePage().getSelection();
			boolean ret = true;
			if (!(selection instanceof IStructuredSelection)) {
				LaunchView debugview = (LaunchView) PlatformUI.getWorkbench()
						.getWorkbenchWindows()[0].getActivePage().findView(
						"org.eclipse.debug.ui.DebugView");
				// 获取debug树
				TreeModelViewer treeviewer = (TreeModelViewer) debugview
						.getViewer();
				treeviewer.refresh();
				selection = treeviewer.getSelection();
			}
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection ss = (IStructuredSelection) selection;
				if (!ss.isEmpty())
					obj[0] = ss.getFirstElement();
			} else {
				selection = null;
			}
		} catch (SWTException swte) {
			Display.getDefault().asyncExec(new Thread() {
				public void run() {
					boolean ret = true;
					ISelection selection = null;
					/* if (!(selection instanceof IStructuredSelection)){ */
					LaunchView debugview = (LaunchView) PlatformUI
							.getWorkbench().getWorkbenchWindows()[0]
							.getActivePage().findView(
									"org.eclipse.debug.ui.DebugView");
					// 获取debug树
					if (debugview == null)
						return;
					TreeModelViewer treeviewer = (TreeModelViewer) debugview
							.getViewer();
					treeviewer.refresh();
					selection = treeviewer.getSelection();
					/* } */
					if (selection instanceof IStructuredSelection) {
						IStructuredSelection ss = (IStructuredSelection) selection;
						if (!ss.isEmpty())
							obj[0] = ss.getFirstElement();
					} else {
						selection = null;
						obj[0] = new Object();
					}
				}
			});
			// swte.printStackTrace();
		}
		int i = 0;
		while (obj[0] == null && i < 5) {
			try {

				Thread.sleep(100);
				i++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (obj[0] == null) {
			return -1;
		}
		if (obj[0] instanceof IStackFrame) {// 加入延时，此处存在线程并行的状况，必须多获取几次
			for (int k = 0; k < 5; k++) {
				stackFrameCount = ((IStackFrame) obj[0]).getThread()
						.getStackFrames().length;
				if (stackFrameCount > 0) {
					// System.out.println("第"+k+"秒才获取正确thread");
					break;
				} else {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else if (obj[0] instanceof IThread) {
			IStackFrame[] sf = ((IThread) obj[0]).getStackFrames();
			if (sf == null) {
				stackFrameCount = -2;
				return -1;
			}
			stackFrameCount = sf.length;
		} else if (obj[0] instanceof IDebugTarget) {
			IThread[] t = ((IDebugTarget) obj[0]).getThreads();
			if (t == null || t.length == 0)
				return -1;
			IStackFrame[] sf = t[0].getStackFrames();
			if (sf == null) {
				stackFrameCount = -2;
				return -1;
			}
			stackFrameCount = sf.length;
		} else if (obj[0] instanceof Launch) {
			IDebugTarget dt = ((Launch) obj[0]).getDebugTarget();
			if (dt == null) {
				stackFrameCount = -2;
				return -1;
			}
			IThread[] t = dt.getThreads();
			if (t == null || t.length == 0) {
				stackFrameCount = -2;
				return -1;
			}
			IStackFrame[] sf = t[0].getStackFrames();
			if (sf == null) {
				stackFrameCount = -2;
				return -1;
			}
			stackFrameCount = sf.length;
		} else {
			if (DebugPlugin.getDefault().getLaunchManager().getLaunches().length == 0) {
				stackFrameCount = -2;
				return -1;
			}
			IDebugTarget ict = DebugPlugin.getDefault().getLaunchManager()
					.getLaunches()[0].getDebugTarget();
			stackFrameCount = ict.getThreads()[0].getStackFrames().length;
		}

		// ISelection selection =
		// PlatformUI.getWorkbench().getWorkbenchWindows()[0].getActivePage().getSelection();

		return stackFrameCount;
	}

	// End: added by lichengfei 2012-07-24
	public static void postCommand0(Command cmd, IDebugTarget idt) {
		ICDISessionObject iso = null;
		if (idt instanceof SzCDebugTarget) {
			iso = ((SzCDebugTarget) idt).getCDITarget();
		} else if (idt instanceof org.eclipse.cdt.debug.internal.core.model.CDebugTarget) {
			iso = ((org.eclipse.cdt.debug.internal.core.model.CDebugTarget) idt)
					.getCDITarget();
		}

		if (iso instanceof org.eclipse.cdt.debug.mi.core.cdi.model.Target) {
			Target target = (Target) iso;
			// micmd = new CLICommand(cmd);
			try {
				target.getMISession().postCommand(cmd);
				// System.out.println(info);
			} catch (MIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}

		}
	}

	public static ICStackFrame getCurrentStackFrame() {
		return CDebugUIUtils.getCurrentStackFrame();
	}

	public static String getDebugExePath() {
		ICStackFrame cStackFrame = getCurrentStackFrame();
		if (cStackFrame == null)
			return null;
		IDebugTarget debugTarget = cStackFrame.getDebugTarget();
		IBinaryObject binary = null;
		if (debugTarget instanceof SzCDebugTarget) {
			binary = ((SzCDebugTarget) debugTarget).getBinaryFile();
		}
		String binaryPath = "";
		if (binary != null)
			binaryPath = binary.getPath().toOSString();
		return binaryPath;
	}

	public static void setDoubleLaunchDisable() {
		DebugPlugin
				.getDefault()
				.getLaunchManager()
				.addLaunchListener(
						new org.eclipse.debug.core.ILaunchListener() {

							@Override
							public void launchRemoved(ILaunch launch) {
								// TODO Auto-generated method stub

							}

							@Override
							public void launchAdded(ILaunch launch) {

								ILaunch[] launches = DebugPlugin.getDefault()
										.getLaunchManager().getLaunches();
								for (ILaunch l : launches) {
									if (l != launch
											&& l.getLaunchConfiguration() == launch
													.getLaunchConfiguration()) {
										// launch.setAttribute(isLaunchDoubleClick,
										// "true"); }
										bIsDoubleLaunch = true;
										return;
									}
								}
								bIsDoubleLaunch = false;
							}

							@Override
							public void launchChanged(ILaunch launch) {
								// TODO Auto-generated method stub

							}
						});

	}

	public static boolean isDoubleLaunch() {
		return bIsDoubleLaunch;
	}
	
	public static void setDoubleLaunch(boolean isDoubleLaunch){
	  bIsDoubleLaunch = false;
	}
}
