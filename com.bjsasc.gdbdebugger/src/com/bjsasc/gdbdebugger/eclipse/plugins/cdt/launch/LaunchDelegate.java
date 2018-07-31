package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch;

import com.bjsasc.gdbdebugger.eclipse.plugins.PluginsPlugin;
import com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.external.SzmonLaunchConfigurationDelegate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.cdt.core.IBinaryParser;
import org.eclipse.cdt.core.IBinaryParser.IBinaryObject;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.debug.core.CDIDebugModel;
import org.eclipse.cdt.debug.core.ICDIDebugger;
import org.eclipse.cdt.debug.core.ICDebugConfiguration;
import org.eclipse.cdt.debug.core.cdi.CDIException;
import org.eclipse.cdt.debug.core.cdi.ICDIEventManager;
import org.eclipse.cdt.debug.core.cdi.ICDISession;
import org.eclipse.cdt.debug.core.cdi.model.ICDIRuntimeOptions;
import org.eclipse.cdt.debug.core.cdi.model.ICDITarget;
import org.eclipse.cdt.launch.AbstractCLaunchDelegate;
import org.eclipse.cdt.launch.internal.ui.LaunchMessages;
import org.eclipse.cdt.launch.internal.ui.LaunchUIPlugin;
import org.eclipse.cdt.utils.elf.parser.ElfBinaryObject;
import org.eclipse.cdt.utils.pty.PTY;
import org.eclipse.cdt.utils.spawner.ProcessFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.debug.core.model.RuntimeProcess;
import org.eclipse.debug.internal.core.OutputStreamMonitor;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.cdt.debug.core.cdi.event.ICDIEvent;
import org.eclipse.cdt.debug.core.cdi.event.ICDIEventListener;
import org.eclipse.cdt.debug.mi.core.cdi.event.DestroyedEvent;
import org.eclipse.cdt.debug.mi.core.cdi.event.ExitedEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.progress.IProgressService;

public class LaunchDelegate extends AbstractCLaunchDelegate {
	public class TerminateListener implements ICDIEventListener {
		RuntimeProcess fp;

		public TerminateListener(LaunchDelegate paramLaunchDelegate,
				RuntimeProcess p) {
			this.fp = p;
		}

		public void handleDebugEvents(ICDIEvent[] event) {
			for (int i = 0; i < event.length; ++i) {
				ICDIEvent e = event[i];
				if (!(e instanceof DestroyedEvent))
					continue;
				try {
					this.fp.terminate();
				} catch (DebugException localDebugException) {
				}
			}
		}
	}

	public class TerminateLaunchListener implements ICDIEventListener {
		String name;

		public TerminateLaunchListener(LaunchDelegate paramLaunchDelegate,
				String n) {
			this.name = n;
		}

		public void handleDebugEvents(ICDIEvent[] event) {
			try {
				for (int i = 0; i < event.length; ++i) {
					ICDIEvent e = event[i];
					if (e instanceof ExitedEvent) {
						ILaunchManager manager = DebugPlugin.getDefault()
								.getLaunchManager();
						ILaunchConfigurationType programType = manager
								.getLaunchConfigurationType("com.gaisler.eclipse.plugins.cdt.launch.external.LaunchConfigurationType");
						if (programType != null) {
							ILaunch[] launches = manager.getLaunches();

							String configName = "";

							for (int j = 0; j < launches.length; ++j) {
								try {
									ILaunchConfiguration lconfig = launches[j]
											.getLaunchConfiguration();
									if (lconfig == null) {
										break;
									}
									ILaunchConfigurationType configType = lconfig
											.getType();
									configName = lconfig.getName();
								} catch (CoreException localCoreException) {
									break;
								}
								ILaunchConfiguration lconfig;
								ILaunchConfigurationType configType = null;
								if ((configType!=null&&!configType.equals(programType))//added by lichengfei 2012-07-21  added configType!=null&&
										|| (configName.compareTo(this.name) != 0)
										|| (launches[j].isTerminated()))
									continue;
								launches[j].terminate();
								break;
							}
						}
					}
				}
			} catch (DebugException localDebugException) {
			}
		}
	}

	class ShowProgress implements Runnable {
		Job fJob;

		ShowProgress(LaunchDelegate paramLaunchDelegate, Job job) {
			this.fJob = job;
		}

		public void run() {
			IWorkbench workbench = PluginsPlugin.getDefault().getWorkbench();
			IProgressService progressService = workbench.getProgressService();
			progressService.showInDialog(workbench.getActiveWorkbenchWindow()
					.getShell(), this.fJob);
		}
	}

	public class SzmonTerminateListener implements IDebugEventSetListener {
		RuntimeProcess fp;
		ILaunch fLaunch;

		public SzmonTerminateListener(LaunchDelegate paramLaunchDelegate,
				RuntimeProcess p, ILaunch launch0) {
			this.fp = p;
			this.fLaunch = launch0;
		}

		@Override
		public void handleDebugEvents(DebugEvent[] events) {
			// TODO Auto-generated method stub
			for (int i = 0; i < events.length; ++i) {
				DebugEvent e = events[i];
				if ((e.getKind() != 8) || (e.getSource() != this.fp))
					continue;
				try {
					this.fLaunch.terminate();
				} catch (DebugException localDebugException) {
				}
			}
		}
	}

	public void launch(ILaunchConfiguration srcconfig, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		ILaunchConfigurationWorkingCopy config = srcconfig.getWorkingCopy();

		RuntimeProcess szmonprocess = null;
		LaunchDelegate.TerminateLaunchListener terminateListener = null;

		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		monitor
				.beginTask("连接",
						10);

		if (monitor.isCanceled())
			return;
		try {
			monitor.worked(1);
			String[] arguments = getProgramArgumentsArray(config);

			IPath exePath = verifyProgramPath(config);
			ICProject project = verifyCProject(config);
			IBinaryParser.IBinaryObject exeFile = verifyBinary(project, exePath);
			if(exeFile==null)
				exeFile =new ElfBinaryObject(null,exePath,3);

			setDefaultSourceLocator(launch, config);

			String startmode = config.getAttribute(
					Constants.ATTR_LAUNCH_IPFIELD_starttype, "remote");
			if (startmode.compareTo("local") == 0) {
				String external = config.getAttribute(
						Constants.ATTR_LAUNCH_external, "");
				ILaunchManager manager = DebugPlugin.getDefault()
						.getLaunchManager();
				ILaunchConfigurationType programType = manager
						.getLaunchConfigurationType("com.gaisler.eclipse.plugins.cdt.launch.external.LaunchConfigurationType");
				if (programType != null) {
					ILaunchConfiguration[] launchconf = manager
							.getLaunchConfigurations(programType);

					for (int i = 0; i < launchconf.length; ++i) {
						ILaunchConfiguration l = launchconf[i];
						String n = l.getName();
						if (n.compareTo(external) == 0) {
							boolean isRunning = false;
							ILaunch[] launches = manager.getLaunches();

							String configName = "";

							for (int j = 0; j < launches.length; ++j) {
								try {
									ILaunchConfiguration lconfig = launches[j]
											.getLaunchConfiguration();
									if (lconfig == null) {
										break;
									}
									ILaunchConfigurationType configType = lconfig
											.getType();
									configName = lconfig.getName();
								} catch (CoreException localCoreException1) {
									break;
								}
								ILaunchConfiguration lconfig;
								ILaunchConfigurationType configType = null;
								if ((configType!=null&&!configType.equals(programType))//added by lichengfei 2012-07-21  added configType!=null&&
										|| (configName.compareTo(n) != 0)
										|| (launches[j].isTerminated()))
									continue;
								IProcess[] procs = launches[j].getProcesses();
								if ((procs.length >= 1)
										&& (procs[0] instanceof RuntimeProcess)) {
									szmonprocess = (RuntimeProcess) procs[0];
									DebugPlugin
											.getDefault()
											.addDebugEventListener(
													new LaunchDelegate.SzmonTerminateListener(
															this, szmonprocess,
															launch));
								}

								isRunning = true;

								break;
							}

							if (!isRunning) {
								String port = String
										.valueOf(SzmonLaunchConfigurationDelegate
												.getPort(external));

								config.setAttribute(
										Constants.ATTR_LAUNCH_GDB_type,
										"socket");
								config.setAttribute(
										Constants.ATTR_LAUNCH_GDB_host,
										"localhost");
								config.setAttribute(
										Constants.ATTR_LAUNCH_GDB_port, port);
								config.setAttribute(
										Constants.ATTR_LAUNCH_GDB_tty, "");
								config.setAttribute(
										Constants.ATTR_LAUNCH_GDB_baud, "");

								NullProgressMonitor monitor0 = new NullProgressMonitor();
								monitor0.beginTask(
										"Launching TSIM-LEON3/SZMON configuration "
												+ l.getName(), 10);

								PluginsPlugin.getDefault().launchDetectStart();
								ILaunch launch0 = DebugUIPlugin.buildAndLaunch(
										l, "run", monitor);
								IProcess[] procs = launch0.getProcesses();
								if ((procs.length >= 1)
										&& (procs[0] instanceof RuntimeProcess)) {
									szmonprocess = (RuntimeProcess) procs[0];
									IStreamMonitor om = szmonprocess
											.getStreamsProxy()
											.getOutputStreamMonitor();
									if (om instanceof OutputStreamMonitor) {
										((OutputStreamMonitor) om)
												.setBuffered(false);
										om.getContents();
									}

									DebugPlugin
											.getDefault()
											.addDebugEventListener(
													new LaunchDelegate.SzmonTerminateListener(
															this, szmonprocess,
															launch));
								}

							}

							boolean terminate = config.getAttribute(
									Constants.ATTR_LAUNCH_external_terminate,
									false);
							if (!terminate)
								break;
							terminateListener = new LaunchDelegate.TerminateLaunchListener(
									this, n);

							break;
						}

					}

				}

			} else if (startmode.compareTo("remote") == 0) {
				String temp = null;
				config
						.setAttribute(Constants.ATTR_LAUNCH_GDB_type, config
								.getAttribute(
										Constants.ATTR_LAUNCH_IPFIELD_type,
										temp));
				config
						.setAttribute(Constants.ATTR_LAUNCH_GDB_host, config
								.getAttribute(
										Constants.ATTR_LAUNCH_IPFIELD_host,
										temp));
				config
						.setAttribute(Constants.ATTR_LAUNCH_GDB_port, config
								.getAttribute(
										Constants.ATTR_LAUNCH_IPFIELD_port,
										temp));
				config.setAttribute(Constants.ATTR_LAUNCH_GDB_tty, config
						.getAttribute(Constants.ATTR_LAUNCH_IPFIELD_tty, temp));
				config
						.setAttribute(Constants.ATTR_LAUNCH_GDB_baud, config
								.getAttribute(
										Constants.ATTR_LAUNCH_IPFIELD_BAUD,
										temp));
			} else {
				abort(Messages.getString("SZMONLaunchTab.Typemissing"), null,
						150);
			}
			config.doSave();

			if (mode.equals("debug")) {
				ICDebugConfiguration debugConfig = (ICDebugConfiguration) config;

				ICDISession dsession = null;
				String debugMode = config.getAttribute(
						"org.eclipse.cdt.launch.DEBUGGER_START_MODE", "run");
				if (debugMode.equals("run")) {
					dsession = debugConfig.createDebugger()
							.createDebuggerSession(launch, exeFile,
									new SubProgressMonitor(monitor, 8));

					if (terminateListener != null) {
						ICDIEventManager m = dsession.getEventManager();
						m.addEventListener(terminateListener);
					}
					try {
						try {
							ICDITarget[] dtargets = dsession.getTargets();
							for (int i = 0; i < dtargets.length; ++i) {
								ICDIRuntimeOptions opt = dtargets[i]
										.getRuntimeOptions();
								opt.setArguments(arguments);
								File wd = getWorkingDirectory(config);
								if (wd != null) {
									opt.setWorkingDirectory(wd
											.getAbsolutePath());
								}
								opt
										.setEnvironment(getEnvironmentAsProperty(config));
							}
						} catch (CDIException e) {
							abort("失败", e, 150);
						}
						monitor.worked(1);
						boolean stopInMain = config.getAttribute(
								"org.eclipse.cdt.launch.DEBUGGER_STOP_AT_MAIN",
								false);
//added sim type by mbl at 20110705 
						String type = config.getAttribute(Constants.ATTR_LAUNCH_GDB_type, "tty");
						boolean resume=true;
						if("sim".equals(type))
							resume=false;
						
						
						ICDITarget[] targets = dsession.getTargets();
						for (int i = 0; i < targets.length; ++i) {
							Process process = targets[i].getProcess();
							IProcess iprocess = null;
							if (process != null) {
								iprocess = DebugPlugin.newProcess(launch,
										process, renderProcessLabel(exePath
												.toOSString()));
							}
//							CDIDebugModel.newDebugTarget(launch, project
//									.getProject(), targets[i],
//									renderTargetLabel(debugConfig), iprocess,
//									exeFile, true, false, stopInMain, true);
							//modified for sim type by mbl at 20110705
//							com.bjsasc.debug.core.CDIDebugModel.newDebugTarget(launch, project
//									.getProject(), targets[i],
//									renderTargetLabel(debugConfig), iprocess,
//									exeFile, true, false, "main", resume);
						}
					} catch (CoreException e) {
						try {
							dsession.terminate();
						} catch (CDIException localCDIException1) {
						}
						throw e;
					}
				}
			} else {
				File wd = getWorkingDirectory(config);
				if (wd == null) {
					wd = new File(System.getProperty("user.home", "."));
				}
				ArrayList command = new ArrayList(1 + arguments.length);
				command.add(exePath.toOSString());
				command.addAll((Collection) Arrays.asList(arguments));
				String[] commandArray = (String[]) command
						.toArray(new String[command.size()]);
				boolean usePty = config.getAttribute(
						"org.eclipse.cdt.launch.use_terminal", true);
				monitor.worked(5);
				Process process = exec(commandArray, getEnvironment(config),
						wd, usePty);
				monitor.worked(3);
				DebugPlugin.newProcess(launch, process,
						renderProcessLabel(commandArray[0]));
			}
		} finally {
			monitor.done();
		}
	}

	protected Process exec(String[] cmdLine, String[] environ,
			File workingDirectory, boolean usePty) throws CoreException {
		Process p = null;
		try {
			if (workingDirectory == null) {
				p = ProcessFactory.getFactory().exec(cmdLine, environ);
			} else if ((usePty) && (PTY.isSupported()))
				p = ProcessFactory.getFactory().exec(cmdLine, environ,
						workingDirectory, new PTY());
			else
				p = ProcessFactory.getFactory().exec(cmdLine, environ,
						workingDirectory);
		} catch (IOException e) {
			abort(
					"启动进程失败",
					e, 150);
		} catch (NoSuchMethodError e) {
			IStatus status = new Status(
					4,
					LaunchUIPlugin.getUniqueIdentifier(),
					100,
					"不支持工作目录",
					e);
			IStatusHandler handler = DebugPlugin.getDefault().getStatusHandler(
					status);

			if (handler != null) {
				Object result = handler.handleStatus(status, this);
				if ((result instanceof Boolean)
						&& (((Boolean) result).booleanValue())) {
					p = exec(cmdLine, environ, null, usePty);
				}
			}
		}
		return p;
	}

	protected String getPluginID() {
		return LaunchUIPlugin.getUniqueIdentifier();
	}
}
