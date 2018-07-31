package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.sparcelf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.internal.core.model.Util;
import org.eclipse.cdt.managedbuilder.core.BuildException;
import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.core.IManagedBuildInfo;
import org.eclipse.cdt.managedbuilder.core.IManagedCommandLineGenerator;
import org.eclipse.cdt.managedbuilder.core.IManagedCommandLineInfo;
import org.eclipse.cdt.managedbuilder.core.IOutputType;
import org.eclipse.cdt.managedbuilder.core.IResourceConfiguration;
import org.eclipse.cdt.managedbuilder.core.ITool;
import org.eclipse.cdt.managedbuilder.core.IToolChain;
import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;
import org.eclipse.cdt.managedbuilder.core.ManagedBuilderCorePlugin;
import org.eclipse.cdt.managedbuilder.internal.core.ManagedMakeMessages;
import org.eclipse.cdt.managedbuilder.macros.BuildMacroException;
import org.eclipse.cdt.managedbuilder.macros.IBuildMacroProvider;
import org.eclipse.cdt.managedbuilder.makegen.IManagedBuilderMakefileGenerator;
import org.eclipse.cdt.managedbuilder.makegen.IManagedDependencyCommands;
import org.eclipse.cdt.managedbuilder.makegen.IManagedDependencyGenerator2;
import org.eclipse.cdt.managedbuilder.makegen.IManagedDependencyGeneratorType;
import org.eclipse.cdt.managedbuilder.makegen.IManagedDependencyInfo;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;

import org.eclipse.cdt.managedbuilder.core.IManagedBuildInfo;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.resources.IResourceProxy;

public class SparcElfGnuMakefileGenerator implements
		IManagedBuilderMakefileGenerator {
	private static final String COMMENT = "MakefileGenerator.comment";
	private static final String HEADER = "MakefileGenerator.comment.header";
	protected static final String MESSAGE_FINISH_BUILD = ManagedMakeMessages
			.getResourceString("MakefileGenerator.message.finish.build");
	protected static final String MESSAGE_FINISH_FILE = ManagedMakeMessages
			.getResourceString("MakefileGenerator.message.finish.file");
	protected static final String MESSAGE_START_BUILD = ManagedMakeMessages
			.getResourceString("MakefileGenerator.message.start.build");
	protected static final String MESSAGE_START_FILE = ManagedMakeMessages
			.getResourceString("MakefileGenerator.message.start.file");
	private static final String MOD_LIST = "MakefileGenerator.comment.module.list";
	private static final String MOD_RULES = "MakefileGenerator.comment.build.rule";
	private static final String ALL_TARGET = "MakefileGenerator.comment.build.alltarget";
	private static final String MAINBUILD_TARGET = "MakefileGenerator.comment.build.mainbuildtarget";
	private static final String SRC_LISTS = "MakefileGenerator.comment.source.list";
	private static final String EMPTY_STRING = new String();
	private static final String[] EMPTY_STRING_ARRAY = new String[0];
	private static final String PREBUILD = "pre-build";
	private static final String MAINBUILD = "main-build";
	private static final String POSTBUILD = "post-build";
	private static final String SECONDARY_OUTPUTS = "secondary-outputs";
	private String buildTargetName;
	private Vector buildTools;
	private Vector deletedFileList;
	private Vector deletedDirList;
	private Vector dependencyMakefiles;
	private String extension;
	private IManagedBuildInfo info;
	private Vector invalidDirList;
	private Vector modifiedList;
	private IProgressMonitor monitor;
	private Set outputExtensionsSet;
	private IProject project;
	private Vector ruleList;
	private Vector subdirList;
	private IPath topBuildDir;
	public static String ECHO_BLANK_LINE = "echo ' '" + NEWLINE;

	// added by wk
	private boolean preprocessOnly = false;

	public class ResourceDeltaVisitor implements IResourceDeltaVisitor {
		private SparcElfGnuMakefileGenerator generator;
		private IManagedBuildInfo info;

		public ResourceDeltaVisitor(
				SparcElfGnuMakefileGenerator paramSparcElfGnuMakefileGenerator1,
				SparcElfGnuMakefileGenerator generator, IManagedBuildInfo info) {
			this.generator = generator;
			this.info = info;
		}

		public boolean visit(IResourceDelta delta) throws CoreException {
			boolean keepLooking = false;
			IResource resource = delta.getResource();
			if (resource.getType() == 1) {
				String ext = resource.getFileExtension();
				switch (delta.getKind()) {
				case 1:
					if ((!this.generator.isGeneratedResource(resource))
							&& (this.info.buildsFileType(ext))) {
						this.generator.appendModifiedSubdirectory(resource);
					}
					break;
				case 2:
					if ((!this.generator.isGeneratedResource(resource))
							&& (this.info.buildsFileType(ext))) {
						this.generator.appendDeletedFile(resource);
						this.generator.appendModifiedSubdirectory(resource);
					}
					break;
				default:
					keepLooking = true;
				}
			}
			if (resource.getType() == 2) {
				switch (delta.getKind()) {
				case 2:
					if (!this.generator.isGeneratedResource(resource)) {
						this.generator
								.appendDeletedSubdirectory((IContainer) resource);
					}
				}
			}
			if (resource.getType() == 4) {
				IResourceDelta[] children = delta.getAffectedChildren();
				if ((children != null) && (children.length > 0)) {
					keepLooking = true;
				}
			} else if (!this.generator.isGeneratedResource(resource)) {
				keepLooking = true;
			}
			return keepLooking;
		}
	}

	public class ResourceProxyVisitor implements IResourceProxyVisitor {
		private SparcElfGnuMakefileGenerator generator;
		private IManagedBuildInfo info;

		public ResourceProxyVisitor(
				SparcElfGnuMakefileGenerator paramSparcElfGnuMakefileGenerator1,
				SparcElfGnuMakefileGenerator generator, IManagedBuildInfo info) {
			this.generator = generator;
			this.info = info;
		}

		public boolean visit(IResourceProxy proxy) throws CoreException {
			if (this.generator == null) {
				return false;
			}

			if (proxy.getType() == 1) {
				IResource resource = proxy.requestResource();
				String ext = resource.getFileExtension();
				if ((this.info.buildsFileType(ext))
						&& (!this.generator.isGeneratedResource(resource))) {
					this.generator.appendBuildSubdirectory(resource);
				}

				return false;
			}

			return true;
		}
	}

	public static String escapedEcho(String string) {
		String escapedString = string.replaceAll("'", "'\"'\"'");
		return "echo '" + escapedString + "'" + NEWLINE;
	}

	protected StringBuffer addDefaultHeader() {
		StringBuffer buffer = new StringBuffer();
		outputCommentLine(buffer);
		buffer.append("# "
				+ ManagedMakeMessages
						.getResourceString("MakefileGenerator.comment.header")
				+ NEWLINE);
		outputCommentLine(buffer);
		buffer.append(NEWLINE);
		return buffer;
	}

	protected StringBuffer addFragmentDependenciesHeader() {
		return addDefaultHeader();
	}

	protected StringBuffer addFragmentMakefileHeader() {
		return addDefaultHeader();
	}

	private StringBuffer addMacros() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("ROOT := .." + NEWLINE);
		buffer.append(NEWLINE);

		buffer.append("-include $(ROOT)/makefile.init" + NEWLINE);
		buffer.append(NEWLINE);

		buffer.append("RM := ");
		buffer.append(this.info.getCleanCommand() + NEWLINE);
		buffer.append(NEWLINE);

		buffer
				.append("# "
						+ ManagedMakeMessages
								.getResourceString("MakefileGenerator.comment.source.list")
						+ NEWLINE);
		buffer.append("-include sources.mk" + NEWLINE);
		buffer.append("-include $(SUBDIRS:%=%/subdir.mk)" + NEWLINE);
		buffer.append("-include objects.mk" + NEWLINE);
		buffer.append("-include $(DEPS)" + NEWLINE);

		buffer.append("-include $(ROOT)/makefile.defs" + NEWLINE);

		return buffer.append(NEWLINE);
	}

	private void addRule(String relativePath, StringBuffer buffer,
			IResource resource) {
		String resourceName = getFileName(resource);
		String inputExtension = resource.getFileExtension();
		String cmd = this.info.getToolForSource(inputExtension);
		String outputExtension = this.info.getOutputExtension(inputExtension);
		String outflag = null;
		String outputPrefix = null;
		IPath outputLocation = new Path(relativePath);
		IManagedDependencyGenerator2 depGen = null;

		IManagedDependencyGeneratorType dep = this.info
				.getDependencyGenerator(inputExtension);
		if (dep instanceof IManagedDependencyGenerator2) {
			depGen = (IManagedDependencyGenerator2) dep;
		}
		boolean doDepGen = (depGen != null)
				&& (depGen.getCalculatorType() == 5);

		if (doDepGen) {
			String depFile = relativePath + resourceName + "." + "d";
			getDependencyMakefiles().add(depFile);
		}

		IPath resourceLocation = resource.getLocation();
		String projectLocation = this.project.getLocation().toString();
		String resourcePath = null;
		String buildRule = null;
		String OptDotExt = "";
		if (outputExtension != "") {
			OptDotExt = "." + outputExtension;
		}
		IConfiguration config = this.info.getDefaultConfiguration();

		IResourceConfiguration resConfig = null;
		if (config != null)
			resConfig = config.getResourceConfiguration(resource.getFullPath()
					.toString());

		if (!resourceLocation.toString().startsWith(projectLocation)) {
			resourcePath = resourceLocation.toString();

			buildRule = relativePath + resourceName + OptDotExt + ":" + " "
					+ resourcePath;
		} else {
			resourcePath = relativePath;

			if (resConfig != null)
				buildRule = resourcePath + resourceName + OptDotExt + ":" + " "
						+ ".." + "/" + resourcePath + resourceName + "."
						+ inputExtension;
			else {
				buildRule = relativePath + "%" + OptDotExt + ":" + " " + ".."
						+ "/" + resourcePath + "%" + "." + inputExtension;
			}

		}

		if (getRuleList().contains(buildRule)) {
			return;
		}

		getRuleList().add(buildRule);

		buffer.append(buildRule + NEWLINE);
		buffer.append("\t@echo '" + MESSAGE_START_FILE + " " + "$<" + "'"
				+ NEWLINE);

		IManagedCommandLineInfo cmdLInfo = null;

		String[] depFlags = (String[]) null;

		if ((doDepGen) && (depGen != null) && (depGen.getCalculatorType() == 5)) {
			IManagedDependencyInfo depInfo = depGen.getDependencySourceInfo(
					resource.getFullPath(), resource, config, this.info
							.getToolFromInputExtension(inputExtension),
					outputLocation);
			if (depInfo instanceof IManagedDependencyCommands) {
				IManagedDependencyCommands depCmds = (IManagedDependencyCommands) depInfo;
				depFlags = depCmds.getDependencyCommandOptions();
			}
		}

		if (resConfig != null) {
			ITool[] tools = resConfig.getTools();
			outflag = tools[0].getOutputFlag();
			outputPrefix = tools[0].getOutputPrefix();
			cmd = tools[0].getToolCommand();
			String[] inputs = new String[1];
			inputs[0] = "$<";
			String[] toolFlags = (String[]) null;
			try {
				toolFlags = tools[0].getToolCommandFlags(
						resource.getFullPath(), outputLocation);
			} catch (BuildException localBuildException) {
				toolFlags = EMPTY_STRING_ARRAY;
			}
			String[] flags;
			if ((depFlags != null) && (depFlags.length > 0)) {
				flags = new String[toolFlags.length + depFlags.length];
				System.arraycopy(toolFlags, 0, flags, 0, toolFlags.length);
				System.arraycopy(depFlags, 0, flags, toolFlags.length,
						depFlags.length);
			} else {
				flags = toolFlags;
			}

			IManagedCommandLineGenerator cmdLGen = tools[0]
					.getCommandLineGenerator();
			cmdLInfo = cmdLGen.generateCommandLineInfo(tools[0], cmd, flags,
					outflag, outputPrefix, "$@", inputs, tools[0]
							.getCommandLinePattern());

			String buildCmd = cmdLInfo.getCommandLine();
			buffer.append("\t" + buildCmd);
		} else {
			String buildFlags = this.info.getToolFlagsForSource(inputExtension,
					resource.getFullPath(), outputLocation);
			String[] buildFlagsArray = buildFlags.split("\\s");
			// added by wk
			if (buildFlagsArray[0].equals("-E")) {
				preprocessOnly = true;
				return;
			}
			// end
			outflag = this.info.getOutputFlag(outputExtension);
			outputPrefix = this.info.getOutputPrefix(outputExtension);
			String[] flags;
			if ((depFlags != null) && (depFlags.length > 0)) {
				flags = new String[buildFlagsArray.length + depFlags.length];
				System.arraycopy(buildFlagsArray, 0, flags, 0,
						buildFlagsArray.length);
				System.arraycopy(depFlags, 0, flags, buildFlagsArray.length,
						depFlags.length);
			} else {
				flags = buildFlagsArray;
			}
			String[] inputs = new String[1];
			inputs[0] = "$<";
			cmdLInfo = this.info.generateToolCommandLineInfo(inputExtension,
					flags, outflag, outputPrefix, "$@", inputs, resource
							.getFullPath(), outputLocation);

			String buildCmd = null;
			if (cmdLInfo == null) {
				if ((depFlags != null) && (depFlags.length > 0)) {
					for (int i = 0; i < depFlags.length; ++i)
						buildFlags = buildFlags + " " + depFlags[i];
				}
				buildCmd = cmd + " " + buildFlags + " " + outflag + " "
						+ outputPrefix + "$@" + " " + "$<";
			} else {
				buildCmd = cmdLInfo.getCommandLine();
			}
			buffer.append("\t" + buildCmd);
		}

		buffer.append(NEWLINE);
		buffer.append("\t@echo '" + MESSAGE_FINISH_FILE + " " + "$<" + "'"
				+ NEWLINE);
		buffer.append("\t@echo ' '" + NEWLINE + NEWLINE);
	}

	private StringBuffer addSources(IContainer module) throws CoreException {
		IPath moduleRelativePath = module.getProjectRelativePath();
		String relativePath = moduleRelativePath.toString();
		relativePath = relativePath + ((relativePath.length() == 0) ? "" : "/");
		relativePath = escapeWhitespaces(relativePath);

		HashMap extensionToRuleStringMap = new HashMap();

		Iterator iter = this.buildTools.iterator();
		while (iter.hasNext()) {
			List extensionsList = ((ITool) iter.next()).getInputExtensions();

			Iterator exListIterator = extensionsList.iterator();
			while (exListIterator.hasNext()) {
				String extensionName = exListIterator.next().toString();
				if ((extensionToRuleStringMap.containsKey(extensionName))
						|| (getOutputExtensions().contains(extensionName))) {
					continue;
				}
				StringBuffer macroName = getMacroName(extensionName);

				StringBuffer tempBuffer = new StringBuffer();
				tempBuffer.append(macroName + " " + "+=" + " " + "\\\n");
				tempBuffer.append("${addprefix $(ROOT)/" + relativePath + ","
						+ " " + "\\\n");

				extensionToRuleStringMap.put(extensionName, tempBuffer
						.toString());
			}

		}

		StringBuffer buffer = new StringBuffer();
		StringBuffer ruleBuffer = new StringBuffer(
				"# "
						+ ManagedMakeMessages
								.getResourceString("MakefileGenerator.comment.build.rule")
						+ NEWLINE);

		IResource[] resources = module.members();
		IConfiguration config = this.info.getDefaultConfiguration();

		for (int i = 0; i < resources.length; ++i) {
			IResource resource = resources[i];
			if (resource.getType() != 1)
				continue;
			IResourceConfiguration resConfig = config
					.getResourceConfiguration(resource.getFullPath().toString());
			if ((resConfig != null) && (resConfig.isExcluded())) {
				continue;
			}
			String ext = resource.getFileExtension();
			if (!this.info.buildsFileType(ext))
				continue;
			StringBuffer bufferForExtension = new StringBuffer();
			if (!extensionToRuleStringMap.containsKey(ext)) {
				continue;
			}

			bufferForExtension.append(extensionToRuleStringMap.get(ext)
					.toString());
			if (getOutputExtensions().contains(bufferForExtension.toString()))
				continue;
			bufferForExtension.append(resource.getName() + " " + "\\\n");

			extensionToRuleStringMap.put(ext, bufferForExtension.toString());

			addRule(relativePath, ruleBuffer, resource);
		}

		Collection bufferCollection = extensionToRuleStringMap.values();
		Iterator collectionIterator = bufferCollection.iterator();
		while (collectionIterator.hasNext()) {
			StringBuffer currentBuffer = new StringBuffer();
			currentBuffer.append(collectionIterator.next().toString());
			currentBuffer.append("}" + NEWLINE + NEWLINE);

			buffer.append(currentBuffer);
		}
		return buffer.append(ruleBuffer + NEWLINE);
	}

	private StringBuffer addSubdirectories() {
		StringBuffer buffer = new StringBuffer();

		buffer
				.append("# "
						+ ManagedMakeMessages
								.getResourceString("MakefileGenerator.comment.module.list")
						+ NEWLINE);

		buffer.append("SUBDIRS := \\\n");

		Iterator iter = (Iterator) getSubdirList().listIterator();
		while (iter.hasNext()) {
			IContainer container = (IContainer) iter.next();
			updateMonitor(ManagedMakeMessages.getFormattedString(
					"MakefileGenerator.message.adding.source.folder", container
							.getFullPath().toString()));

			if (container.getFullPath() == this.project.getFullPath()) {
				buffer.append(". \\\n");
			} else {
				IPath path = container.getProjectRelativePath();
				buffer.append(path.toString() + " " + "\\\n");
			}
		}

		buffer.append(NEWLINE);
		return buffer;
	}

	private StringBuffer addTargets(boolean rebuild) {
		StringBuffer buffer = new StringBuffer();
		String tool_extension = this.extension;
		IConfiguration config = this.info.getDefaultConfiguration();

		String prebuildStep = this.info.getPrebuildStep();
		try {
			prebuildStep = ManagedBuildManager.getBuildMacroProvider()
					.resolveValueToMakefileFormat(prebuildStep, EMPTY_STRING,
							" ", 3, config);
		} catch (BuildMacroException localBuildMacroException1) {
		}
		prebuildStep = prebuildStep.trim();

		String postbuildStep = this.info.getPostbuildStep();
		try {
			postbuildStep = ManagedBuildManager.getBuildMacroProvider()
					.resolveValueToMakefileFormat(postbuildStep, EMPTY_STRING,
							" ", 3, config);
		} catch (BuildMacroException localBuildMacroException2) {
		}
		postbuildStep = postbuildStep.trim();
		String preannouncebuildStep = this.info.getPreannouncebuildStep();
		String postannouncebuildStep = this.info.getPostannouncebuildStep();
		String targets = (rebuild) ? "clean all" : "all";

		String cmd = this.info.getToolForConfiguration(tool_extension);
		if (cmd == null) {
			tool_extension = "exe";
			cmd = this.info.getToolForConfiguration(tool_extension);
		}
		String flags = this.info.getFlagsForConfiguration(tool_extension);
		
		//added by wk,将lib参数从flags变量中提出并将其放到 连接目标的后面
		String[] flagTemp = flags.split(" ");
		StringBuffer flagsBuffer = new StringBuffer();
		StringBuffer libsBuffer = new StringBuffer();
		for(String str : flagTemp){
			str = str.trim();//modified by lichengfei from str.trim(); to str = str.trim();
			if(str.matches("-l.*")){
				libsBuffer.append(str).append(" ");
			}else{
				flagsBuffer.append(str).append(" ");
			}
		}
		flags = flagsBuffer.toString(); 
		String libs = libsBuffer.toString();
		//end
		
		String oflags = this.info.getFlagsForConfiguration("o");
		if (oflags.indexOf("-mv8") != -1) {
			flags = flags + " -mv8";
		}
		if (oflags.indexOf("-msoft-float") != -1) {
			flags = flags + " -msoft-float";
		}
		if (oflags.indexOf("-mflat") != -1) {
			flags = flags + " -mflat";
		}

		String outflag = this.info.getOutputFlag(tool_extension);
		String outputPrefix = this.info.getOutputPrefix(tool_extension);

		IProject[] refdProjects = (IProject[]) null;
		try {
			refdProjects = this.project.getReferencedProjects();
		} catch (CoreException localCoreException) {
		}

		String defaultTarget = "all:";
		if (prebuildStep.length() > 0) {
			buffer
					.append("# "
							+ ManagedMakeMessages
									.getResourceString("MakefileGenerator.comment.build.alltarget")
							+ NEWLINE);

			buffer.append(defaultTarget + " ");
			buffer.append("pre-build ");

			defaultTarget = "main-build";
			buffer.append(defaultTarget);

			defaultTarget = defaultTarget.concat(":");
			buffer.append(NEWLINE + NEWLINE);

			buffer
					.append("# "
							+ ManagedMakeMessages
									.getResourceString("MakefileGenerator.comment.build.mainbuildtarget")
							+ NEWLINE);
		} else {
			buffer
					.append("# "
							+ ManagedMakeMessages
									.getResourceString("MakefileGenerator.comment.build.alltarget")
							+ NEWLINE);
		}

		buffer
				.append(defaultTarget + " " + outputPrefix
						+ this.buildTargetName);
		if (this.extension.length() > 0) {
			buffer.append("." + this.extension);
		} else {
			buffer.append("." + tool_extension);
		}

		IOutputType[] secondaryOutputs = config.getToolChain()
				.getSecondaryOutputs();
		if (secondaryOutputs.length > 0) {
			buffer.append(" secondary-outputs");
		}

		buffer.append(NEWLINE + NEWLINE);

		Vector managedProjectOutputs = null;
		if(refdProjects!=null)//added by lichengfei 2012-08-28
			managedProjectOutputs = new Vector(refdProjects.length);
		if (refdProjects!=null&&refdProjects.length > 0) {
			boolean addDeps = true;
			//if (refdProjects != null)//hidden by lichengfei 2012-08-28
				for (int i = 0; i < refdProjects.length; ++i) {
					IProject dep = refdProjects[i];
					if (dep.exists()) {
						if (addDeps) {
							buffer.append("dependents:" + NEWLINE);
							addDeps = false;
						}
						String buildDir = dep.getLocation().toString();
						String depTargets = targets;
						if (ManagedBuildManager.manages(dep)) {
							IManagedBuildInfo depInfo = ManagedBuildManager
									.getBuildInfo(dep);
							buildDir = buildDir + "/"
									+ depInfo.getConfigurationName();

							String depTarget = depInfo.getBuildArtifactName();
							String depExt = depInfo.getBuildArtifactExtension();
							try {
								depExt = ManagedBuildManager
										.getBuildMacroProvider()
										.resolveValueToMakefileFormat(
												depExt,
												"",
												" ",
												3,
												this.info
														.getDefaultConfiguration());
							} catch (BuildMacroException localBuildMacroException3) {
							}
							try {
								String resolved = ManagedBuildManager
										.getBuildMacroProvider()
										.resolveValueToMakefileFormat(
												depTarget,
												"",
												" ",
												3,
												this.info
														.getDefaultConfiguration());
								if ((resolved = resolved.trim()).length() > 0)
									depTarget = resolved;
							} catch (BuildMacroException localBuildMacroException4) {
							}
							String depPrefix = depInfo.getOutputPrefix(depExt);
							if (depInfo.needsRebuild()) {
								depTargets = "clean all";
							}
							String dependency = buildDir + "/" + depPrefix
									+ depTarget;
							if (depExt.length() > 0) {
								dependency = dependency + "." + depExt;
							}
							dependency = escapeWhitespaces(dependency);
							if(managedProjectOutputs!=null)//added by lichengfei 2012-08-28
								managedProjectOutputs.add(dependency);
						}
						buffer.append("\t-cd " + escapeWhitespaces(buildDir)
								+ " " + "&&" + " " + "$(MAKE) " + depTargets
								+ NEWLINE);
					}
				}
			buffer.append(NEWLINE);
		}

		buffer.append(outputPrefix + this.buildTargetName);
		if (this.extension.length() > 0) {
			buffer.append("." + this.extension);
		} else {
			buffer.append("." + tool_extension);
		}
		buffer.append(": $(OBJS)");
		Iterator refIter = null;
		if(managedProjectOutputs!=null)//added by lichengfei 2012-08-28
		{
			refIter = (Iterator) managedProjectOutputs.listIterator();
			while (refIter.hasNext()) {
				buffer.append(" " + (String) refIter.next());
			}
		}
		buffer.append(NEWLINE);
		buffer.append("\t@echo '" + MESSAGE_START_BUILD + " " + "$@" + "'"
				+ NEWLINE);
		buffer.append("\t" + cmd + " " + flags + " " + outflag + " " + "$@"
				+ " " + "$(OBJS) $(USER_OBJS) $(LIBS)" + libs +NEWLINE);
		buffer.append("\t@echo '" + MESSAGE_FINISH_FILE + " " + "$@" + "'"
				+ NEWLINE);

		if (postbuildStep.length() > 0) {
			buffer.append("\t$(MAKE) --no-print-directory post-build" + NEWLINE
					+ NEWLINE);
		} else {
			buffer.append(NEWLINE);
		}

		buffer.append("clean:" + NEWLINE);
		/**
		 * by wk, 删除debug或release下的所有文件，
		 * 但是如果debug或release文件夹下所有文件已经被删除再次选择清楚项目编译器会报错
		 */
		// buffer.append("\t-$(RM) * makefile");
		buffer.append("\t-$(RM) $(OBJS) $(DEPS) " + outputPrefix
				+ this.buildTargetName);
		/**
		 * extension未得到文件扩展名 临时修改
		 */
		buffer.append("." + "*");
		// if (this.extension.length() > 0) {
		// buffer.append("." + this.extension);
		// }
		buffer.append(NEWLINE + NEWLINE);

		if (prebuildStep.length() > 0) {
			buffer.append("pre-build:" + NEWLINE);
			if (preannouncebuildStep.length() > 0) {
				buffer.append("\t-@" + escapedEcho(preannouncebuildStep));
			}
			buffer.append("\t-" + prebuildStep + NEWLINE);
			buffer.append("\t-@" + ECHO_BLANK_LINE + NEWLINE);
		}

		if (postbuildStep.length() > 0) {
			buffer.append("post-build:" + NEWLINE);
			if (postannouncebuildStep.length() > 0) {
				buffer.append("\t-@" + escapedEcho(postannouncebuildStep));
			}
			buffer.append("\t-" + postbuildStep + NEWLINE);
			buffer.append("\t-@" + ECHO_BLANK_LINE + NEWLINE);
		}

		buffer.append(".PHONY: all clean dependents" + NEWLINE);
		buffer.append(".SECONDARY:");
		if (prebuildStep.length() > 0) {
			buffer.append(" main-build pre-build");
		}
		if (postbuildStep.length() > 0) {
			buffer.append(" post-build");
		}
		buffer.append(NEWLINE);

		buffer.append(NEWLINE);

		buffer.append("-include $(ROOT)/makefile.targets" + NEWLINE);

		return buffer;
	}

	protected StringBuffer addTopHeader() {
		return addDefaultHeader();
	}

	protected void appendBuildSubdirectory(IResource resource) {
		IContainer container = resource.getParent();

		if (resource.getProjectRelativePath().toString().indexOf(" ") != -1) {
			if (!getInvalidDirList().contains(container)) {
				getInvalidDirList().add(container);
			}

		} else if (!getSubdirList().contains(container))
			getSubdirList().add(container);
	}

	protected void appendDeletedSubdirectory(IContainer container) {
		IContainer parent = container.getParent();
		if ((getDeletedDirList().contains(container))
				|| (getDeletedDirList().contains(parent)))
			return;
		getDeletedDirList().add(container);
	}

	protected void appendDeletedFile(IResource resource) {
		getDeletedFileList().add(resource);
	}

	protected void appendModifiedSubdirectory(IResource resource) {
		IContainer container = resource.getParent();

		if (resource.getProjectRelativePath().toString().indexOf(" ") != -1) {
			if (!getInvalidDirList().contains(container)) {
				getInvalidDirList().add(container);
			}
		} else if (!getModifiedList().contains(container))
			getModifiedList().add(container);
	}

	protected void cancel(String message) {
		if ((this.monitor != null) && (!this.monitor.isCanceled()))
			throw new OperationCanceledException(message);
	}

	protected void checkCancel() {
		if ((this.monitor != null) && (this.monitor.isCanceled()))
			throw new OperationCanceledException();
	}

	private IPath createDirectory(String dirName) throws CoreException {
		IFolder folder = this.project.getFolder(dirName);
		if (!folder.exists()) {
			IPath parentPath = new Path(dirName).removeLastSegments(1);

			if (!parentPath.isEmpty()) {
				IFolder parent = this.project.getFolder(parentPath);
				if (!parent.exists()) {
					createDirectory(parentPath.toString());
				}
			}

			try {
				folder.create(true, true, null);
			} catch (CoreException e) {
				if (e.getStatus().getCode() == 374)
					folder.refreshLocal(0, null);
				else {
					throw e;
				}
			}

			if (!folder.isDerived()) {
				folder.setDerived(true);
			}
		}

		return folder.getFullPath();
	}

	private IFile createFile(IPath makefilePath) throws CoreException {
		IWorkspaceRoot root = CCorePlugin.getWorkspace().getRoot();
		IFile newFile = root.getFileForLocation(makefilePath);
		if (newFile == null) {
			newFile = root.getFile(makefilePath);
		}

		ByteArrayInputStream contents = new ByteArrayInputStream(new byte[0]);
		try {
			newFile.create(contents, false, new SubProgressMonitor(
					this.monitor, 1));

			if (!newFile.isDerived()) {
				newFile.setDerived(true);
			}

		} catch (CoreException e) {
			if (e.getStatus().getCode() == 374)
				newFile.refreshLocal(0, null);
			else {
				throw e;
			}
		}
		return newFile;
	}

	private void deleteBuildTarget(IResource deletedFile) {
		String fileName = getFileName(deletedFile);
		String srcExtension = deletedFile.getFileExtension();
		String targetExtension = this.info.getOutputExtension(srcExtension);
		if (targetExtension != "")
			fileName = fileName + "." + targetExtension;
		IPath projectRelativePath = deletedFile.getProjectRelativePath()
				.removeLastSegments(1);
		IPath targetFilePath = getBuildWorkingDir().append(projectRelativePath)
				.append(fileName);
		IResource depFile = this.project.findMember(targetFilePath);
		if ((depFile == null) || (!depFile.exists()))
			return;
		try {
			depFile.delete(true, new SubProgressMonitor(this.monitor, 1));
		} catch (CoreException localCoreException) {
		}
	}

	private void deleteDepFile(IResource deletedFile) {
		String fileName = getFileName(deletedFile);
		fileName = fileName + ".d";
		IPath projectRelativePath = deletedFile.getProjectRelativePath()
				.removeLastSegments(1);
		IPath depFilePath = getBuildWorkingDir().append(projectRelativePath)
				.append(fileName);
		IResource depFile = this.project.findMember(depFilePath);
		if ((depFile == null) || (!depFile.exists()))
			return;
		try {
			depFile.delete(true, new SubProgressMonitor(this.monitor, 1));
		} catch (CoreException localCoreException) {
		}
	}

	protected String escapeWhitespaces(String path) {
		String[] segments = path.split("\\s");
		if (segments.length > 1) {
			StringBuffer escapedPath = new StringBuffer();
			for (int index = 0; index < segments.length; ++index) {
				escapedPath.append(segments[index]);
				if (index + 1 < segments.length) {
					escapedPath.append("\\ ");
				}
			}
			return escapedPath.toString().trim();
		}
		return path;
	}

	public void generateDependencies() throws CoreException {
		IWorkspaceRoot root = CCorePlugin.getWorkspace().getRoot();
		Iterator subDirs = (Iterator) getSubdirList().listIterator();
		while (subDirs.hasNext()) {
			IContainer subDir = (IContainer) subDirs.next();
			IPath projectRelativePath = subDir.getProjectRelativePath();
			IPath buildRelativePath = this.topBuildDir
					.append(projectRelativePath);
			IFolder buildFolder = root.getFolder(buildRelativePath);
			if (buildFolder == null) {
				continue;
			}
			IResource[] files = buildFolder.members();
			for (int index = 0; index < files.length; ++index) {
				IResource file = files[index];
				if ("d".equals(file.getFileExtension())) {
					IFile depFile = root.getFile(file.getFullPath());
					if (depFile == null)
						continue;
					try {
						updateMonitor(ManagedMakeMessages
								.getFormattedString(
										"GnuMakefileGenerator.message.postproc.dep.file",
										depFile.getName()));
						populateDummyTargets(depFile, false);
					} catch (CoreException e) {
						throw e;
					} catch (IOException localIOException) {
					}
				}
			}
		}
	}

	public MultiStatus generateMakefiles(IResourceDelta delta)
			throws CoreException {
		IFolder folder = this.project.getFolder(this.info
				.getConfigurationName());
		if (!folder.exists()) {
			return regenerateMakefiles();
		}

		updateMonitor(ManagedMakeMessages.getFormattedString(
				"MakefileGenerator.message.calc.delta", this.project.getName()));
		SparcElfGnuMakefileGenerator.ResourceDeltaVisitor visitor = new SparcElfGnuMakefileGenerator.ResourceDeltaVisitor(
				this, this, this.info);
		delta.accept(visitor);
		checkCancel();

		updateMonitor(ManagedMakeMessages.getFormattedString(
				"MakefileGenerator.message.finding.sources", this.project
						.getName()));
		SparcElfGnuMakefileGenerator.ResourceProxyVisitor resourceVisitor = new SparcElfGnuMakefileGenerator.ResourceProxyVisitor(
				this, this, this.info);
		this.project.accept(resourceVisitor, 0);
		checkCancel();

		if (getSubdirList().isEmpty()) {
			String info = ManagedMakeMessages.getFormattedString(
					"MakefileGenerator.warning.no.source", this.project
							.getName());
			updateMonitor(info);
			MultiStatus status = new MultiStatus(ManagedBuilderCorePlugin
					.getUniqueIdentifier(), 1, info, null);
			status.add(new Status(1, ManagedBuilderCorePlugin
					.getUniqueIdentifier(), 1, new String(), null));
			return status;
		}

		this.topBuildDir = createDirectory(this.info.getConfigurationName());
		checkCancel();

		IPath srcsFilePath = this.topBuildDir.addTrailingSeparator().append(
				"sources.mk");
		IFile srcsFileHandle = createFile(srcsFilePath);
		populateSourcesMakefile(srcsFileHandle);
		checkCancel();

		Iterator iter = (Iterator) getSubdirList().listIterator();
		while (iter.hasNext()) {
			IContainer subdirectory = (IContainer) iter.next();
			if (getModifiedList().contains(subdirectory))
				continue;
			if (!subdirectory.exists()) {
				appendDeletedSubdirectory(subdirectory);
			} else {
				IPath fragmentPath = getBuildWorkingDir().append(
						subdirectory.getProjectRelativePath())
						.addTrailingSeparator().append("subdir.mk");
				IFile makeFragment = this.project.getFile(fragmentPath);
				if (makeFragment.exists())
					continue;
				getModifiedList().add(subdirectory);
			}

		}

		iter = (Iterator) getDeletedFileList().listIterator();
		while (iter.hasNext()) {
			IResource deletedFile = (IResource) iter.next();
			deleteDepFile(deletedFile);
			deleteBuildTarget(deletedFile);
		}

		IPath makefilePath = this.topBuildDir.addTrailingSeparator().append(
				"makefile");
		IFile makefileHandle = createFile(makefilePath);
		if (preprocessOnly) {
			populatePreprocessOnlyMakefile(makefileHandle, false);
		} else {
			populateTopMakefile(makefileHandle, false);
		}
		checkCancel();

		iter = (Iterator) getModifiedList().listIterator();
		while (iter.hasNext()) {
			IContainer subDir = (IContainer) iter.next();

			if (!subDir.exists()) {
				appendDeletedSubdirectory(subDir);
			} else {
				populateFragmentMakefile(subDir);
				checkCancel();
			}
		}

		iter = (Iterator) getDeletedDirList().listIterator();
		while (iter.hasNext()) {
			IContainer subDir = (IContainer) iter.next();
			removeGeneratedDirectory(subDir);
			checkCancel();
		}
		MultiStatus status;
		if (!getInvalidDirList().isEmpty()) {
			status = new MultiStatus(ManagedBuilderCorePlugin
					.getUniqueIdentifier(), 2, new String(), null);

			iter = getInvalidDirList().iterator();
			while (iter.hasNext())
				status.add(new Status(2, ManagedBuilderCorePlugin
						.getUniqueIdentifier(), 0, ((IContainer) iter.next())
						.getFullPath().toString(), null));
		} else {
			status = new MultiStatus(ManagedBuilderCorePlugin
					.getUniqueIdentifier(), 0, new String(), null);
		}

		return status;
	}

	public IPath getBuildWorkingDir() {
		if (this.topBuildDir != null) {
			return this.topBuildDir.removeFirstSegments(1);
		}
		return null;
	}

	private Vector getDeletedDirList() {
		if (this.deletedDirList == null) {
			this.deletedDirList = new Vector();
		}
		return this.deletedDirList;
	}

	private Vector getDeletedFileList() {
		if (this.deletedFileList == null) {
			this.deletedFileList = new Vector();
		}
		return this.deletedFileList;
	}

	private Vector getDependencyMakefiles() {
		if (this.dependencyMakefiles == null) {
			this.dependencyMakefiles = new Vector();
		}
		return this.dependencyMakefiles;
	}

	private String getFileName(IResource file) {
		String answer = new String();
		String lastSegment = file.getName();
		int extensionSeparator = lastSegment.lastIndexOf(".");
		if (extensionSeparator != -1) {
			answer = lastSegment.substring(0, extensionSeparator);
		}
		return answer;
	}

	private Vector getInvalidDirList() {
		if (this.invalidDirList == null) {
			this.invalidDirList = new Vector();
		}
		return this.invalidDirList;
	}

	protected StringBuffer getMacroName(String extensionName) {
		StringBuffer macroName = new StringBuffer();

		if (extensionName.equals(extensionName.toUpperCase())) {
			macroName.append(extensionName.toUpperCase() + "_UPPER");
		} else {
			macroName.append(extensionName.toUpperCase());
		}
		macroName.append("_SRCS");
		return macroName;
	}

	public String getMakefileName() {
		return new String("makefile");
	}

	private Vector getModifiedList() {
		if (this.modifiedList == null) {
			this.modifiedList = new Vector();
		}
		return this.modifiedList;
	}

	protected Set getOutputExtensions() {
		if (this.outputExtensionsSet == null) {
			this.outputExtensionsSet = new HashSet();

			Iterator iter = this.buildTools.iterator();
			while (iter.hasNext()) {
				ITool tool = (ITool) iter.next();
				String[] outputs = tool.getOutputExtensions();
				if (outputs != null) {
					this.outputExtensionsSet.addAll((Collection) Arrays
							.asList(outputs));
				}
			}
		}
		return this.outputExtensionsSet;
	}

	private Vector getRuleList() {
		if (this.ruleList == null) {
			this.ruleList = new Vector();
		}
		return this.ruleList;
	}

	private Vector getSubdirList() {
		if (this.subdirList == null) {
			this.subdirList = new Vector();
		}
		return this.subdirList;
	}

	public void initialize(IProject project, IManagedBuildInfo info,
			IProgressMonitor monitor) {
		this.project = project;

		this.monitor = monitor;

		this.info = info;

		this.buildTargetName = info.getBuildArtifactName();

		this.extension = info.getBuildArtifactExtension();
		if (this.extension == null) {
			this.extension = new String();
		}

		IConfiguration c = info.getDefaultConfiguration();
		if (c != null) {
			this.buildTools = new Vector((Collection) Arrays.asList(c
					.getFilteredTools()));
		}
	}

	public boolean isGeneratedResource(IResource resource) {
		IPath path = resource.getProjectRelativePath();
		String[] configNames = this.info.getConfigurationNames();
		for (int i = 0; i < configNames.length; ++i) {
			String name = configNames[i];
			IPath root = new Path(name);

			if (root.isPrefixOf(path))
				return true;
		}
		return false;
	}

	protected void outputCommentLine(StringBuffer buffer) {
		for (int i = 0; i < 80; ++i) {
			buffer.append("#");
		}
		buffer.append(NEWLINE);
	}

	protected void populateDummyTargets(IFile makefile, boolean force)
			throws CoreException, IOException {
		if ((makefile == null) || (!makefile.exists()))
			return;

		InputStream contentStream = makefile.getContents(false);
		Reader in = new InputStreamReader(contentStream);
		StringBuffer inBuffer = null;
		int chunkSize = contentStream.available();
		inBuffer = new StringBuffer(chunkSize);
		char[] readBuffer = new char[chunkSize];
		int n = in.read(readBuffer);
		while (n > 0) {
			inBuffer.append(readBuffer);
			n = in.read(readBuffer);
		}
		contentStream.close();

		String inBufferString = inBuffer.toString();
		if ((!force) && (inBufferString.startsWith("#"))) {
			return;
		}

		Vector bufferTokens = new Vector((Collection) Arrays
				.asList(inBufferString.split("\\s")));
		Vector deps = new Vector(bufferTokens.size());
		Iterator tokenIter = bufferTokens.iterator();
		while (tokenIter.hasNext()) {
			String token = (String) tokenIter.next();
			if ((token.lastIndexOf("\\") == token.length() - 1)
					&& (token.length() > 1)) {
				while (tokenIter.hasNext()) {
					String nextToken = (String) tokenIter.next();
					token = token + " " + nextToken;
					if (!nextToken.endsWith("\\")) {
						break;
					}
				}
			}
			deps.add(token);
		}

		deps.trimToSize();

		boolean save = false;
		StringBuffer outBuffer = null;
		String firstLine;
		try {
			firstLine = (String) deps.get(0);
		} catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException1) {
			return;
		}
		if (!firstLine.startsWith("#"))
			outBuffer = addDefaultHeader();
		else {
			outBuffer = new StringBuffer();
		}

		if (firstLine.startsWith("-n")) {
			String secondLine;
			try {
				secondLine = (String) deps.get(1);
			} catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException2) {
				secondLine = new String();
			}
			if (secondLine.startsWith("'")) {
				outBuffer.append(secondLine.substring(1) + " ");
			} else
				outBuffer.append(secondLine + " ");

			String thirdLine;
			try {
				thirdLine = (String) deps.get(2);
			} catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException3) {
				thirdLine = new String();
			}
			int lastIndex = thirdLine.lastIndexOf("'");
			if (lastIndex != -1) {
				if (lastIndex == 0)
					outBuffer.append(" ");
				else
					outBuffer.append(thirdLine.substring(0, lastIndex - 1));
			} else {
				outBuffer.append(thirdLine);
			}
			String fourthLine;
			try {
				fourthLine = (String) deps.get(3);
			} catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException4) {
				fourthLine = new String();
			}
			outBuffer.append(fourthLine + " ");
			try {
				Iterator iter = (Iterator) deps.listIterator(4);
				while (iter.hasNext()) {
					String nextElement = (String) iter.next();
					if (nextElement.endsWith("\\"))
						outBuffer.append(nextElement + NEWLINE + " ");
					else
						outBuffer.append(nextElement + " ");
				}
			} catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {
			}
		} else {
			outBuffer.append(inBuffer);
		}

		outBuffer.append(NEWLINE);
		save = true;

		Iterator dummyIter = deps.iterator();
		while (dummyIter.hasNext()) {
			String dummy = (String) dummyIter.next();
			IPath dep = new Path(dummy);
			String extension = dep.getFileExtension();
			if (!this.info.isHeaderFile(extension)) {
				continue;
			}

			outBuffer.append(dummy + ":" + NEWLINE + NEWLINE);
		}

//		if (save)
//			Util.save(outBuffer, makefile);
	}

	protected void populateFragmentMakefile(IContainer module)
			throws CoreException {
		IPath moduleRelativePath = module.getProjectRelativePath();
		IPath buildRoot = getBuildWorkingDir();
		if (buildRoot == null) {
			return;
		}

		IPath moduleOutputPath = buildRoot.append(moduleRelativePath);
		updateMonitor(ManagedMakeMessages.getFormattedString(
				"MakefileGenerator.message.gen.source.makefile",
				moduleOutputPath.toString()));

		IPath moduleOutputDir = createDirectory(moduleOutputPath.toString());

		IFile modMakefile = createFile(moduleOutputDir.addTrailingSeparator()
				.append("subdir.mk"));
		StringBuffer makeBuf = new StringBuffer();
		makeBuf.append(addFragmentMakefileHeader());
		makeBuf.append(addSources(module));

//		Util.save(makeBuf, modMakefile);
	}

	protected void populateObjectsMakefile(IFile fileHandle)
			throws CoreException {
		StringBuffer macroBuffer = new StringBuffer();
		macroBuffer.append(addDefaultHeader());
		StringBuffer objectsBuffer = new StringBuffer();
		objectsBuffer.append("OBJS := \\\n");
		StringBuffer depFilesBuffer = new StringBuffer();
		depFilesBuffer.append("DEPS := \\\n");

		macroBuffer.append("LIBS := ");
		String[] libs = this.info.getLibsForConfiguration(this.extension);
//		String[] temp = { "-lm", "-lc", "-lszcc", "-lcygmon", "-lnosys" };
//		String[] l = this.info.getLibsForConfiguration(this.extension);
//		if (l.length == 0) {
//			libs = temp;
//		}
		for (int i = 0; i < libs.length; ++i) {
			String string = libs[i];
			macroBuffer.append("\\\n" + string);
		}
		macroBuffer.append(NEWLINE);

		macroBuffer.append("USER_OBJS := ");
		String[] userObjs = this.info
				.getUserObjectsForConfiguration(this.extension);
		for (int j = 0; j < userObjs.length; ++j) {
			String string = userObjs[j];
			macroBuffer.append("\\\n" + string);
		}
		macroBuffer.append(NEWLINE);

		HashSet handledInputExtensions = new HashSet();

		Iterator iter = this.buildTools.iterator();
		while (iter.hasNext()) {
			ITool tool = (ITool) iter.next();
			List extensionsList = tool.getInputExtensions();

			Iterator exListIterator = extensionsList.iterator();
			while (exListIterator.hasNext()) {
				String extensionName = exListIterator.next().toString();

				if ((!getOutputExtensions().contains(extensionName))
						&& (!handledInputExtensions.contains(extensionName))) {
					handledInputExtensions.add(extensionName);
					StringBuffer macroName = getMacroName(extensionName);
					String OptDotExt = "";
					if (tool.getOutputExtension(extensionName) != "") {
						OptDotExt = "."
								+ tool.getOutputExtension(extensionName);
					}

					objectsBuffer.append(" $(" + macroName + ":" + "$(ROOT)"
							+ "/" + "%" + "." + extensionName + "=" + "%"
							+ OptDotExt + ")");

					depFilesBuffer.append(" $(" + macroName + ":" + "$(ROOT)"
							+ "/" + "%" + "." + extensionName + "=" + "%" + "."
							+ "d" + ")");
				}
			}

		}

		macroBuffer.append(NEWLINE + NEWLINE + objectsBuffer);
		macroBuffer.append(NEWLINE + NEWLINE + depFilesBuffer);

//		Util.save(macroBuffer, fileHandle);
	}

	protected void populateSourcesMakefile(IFile fileHandle)
			throws CoreException {
		StringBuffer buffer = addDefaultHeader();

		HashSet handledInputExtensions = new HashSet();
		Iterator iter = this.buildTools.iterator();
		while (iter.hasNext()) {
			List extensionsList = ((ITool) iter.next()).getInputExtensions();
			Iterator exListIterator = extensionsList.iterator();
			while (exListIterator.hasNext()) {
				String extensionName = exListIterator.next().toString();
				if ((!getOutputExtensions().contains(extensionName))
						&& (!handledInputExtensions.contains(extensionName))) {
					handledInputExtensions.add(extensionName);
					StringBuffer macroName = getMacroName(extensionName);
					buffer.append(macroName + " " + ":=" + " " + NEWLINE);
				}
			}

		}

		buffer.append(NEWLINE + addSubdirectories());

//		Util.save(buffer, fileHandle);
	}

	protected void populateTopMakefile(IFile fileHandle, boolean rebuild)
			throws CoreException {
		StringBuffer buffer = new StringBuffer();

		buffer.append(addTopHeader());

		buffer.append(addMacros());

		buffer.append(addTargets(rebuild));

//		Util.save(buffer, fileHandle);
	}

	protected void populatePreprocessOnlyMakefile(IFile fileHandle,
			boolean rebuild) throws CoreException {
		StringBuffer buffer = new StringBuffer();

		String current = System.getProperty("user.dir");

		buffer.append(addTopHeader());
		
		buffer.append(addMacros());

		buffer.append("# 所有目标").append('\n');

		buffer.append("all: " + this.buildTargetName).append("\n");
		
		buffer.append(this.buildTargetName+": ").append("$(C_SRCS)").append('\n');

		buffer.append("\t@echo '正在构建文件'").append('\n');

		buffer.append(
				"\tszcc -Tsparc-elf -E -I" + current
						+ "\\tools\\opt\\sparc-elf\\sparc-elf\\include -o\""+ this.buildTargetName+".i\" $(C_SRCS)")
				.append('\n');

		buffer.append("\t@echo '已结束构建:'").append('\n');

		buffer.append("clean:").append('\n');

		buffer.append("\t-$(RM) $(OBJS) $(DEPS) " + this.buildTargetName + ".*").append('\n');
		
//		Util.save(buffer, fileHandle);

	}

	public void regenerateDependencies(boolean force) throws CoreException {
		IWorkspaceRoot root = CCorePlugin.getWorkspace().getRoot();

		Iterator iter = (Iterator) getDependencyMakefiles().listIterator();
		while (iter.hasNext()) {
			IPath relDepFilePath = this.topBuildDir.append(new Path(
					(String) iter.next()));
			IFile depFile = root.getFile(relDepFilePath);
			if (depFile == null)
				continue;
			if (!depFile.isAccessible())
				continue;
			try {
				updateMonitor(ManagedMakeMessages.getFormattedString(
						"GnuMakefileGenerator.message.postproc.dep.file",
						depFile.getName()));
				populateDummyTargets(depFile, true);
			} catch (CoreException e) {
				throw e;
			} catch (IOException localIOException) {
			}
		}
	}

	public MultiStatus regenerateMakefiles() throws CoreException {
		SparcElfGnuMakefileGenerator.ResourceProxyVisitor visitor = new SparcElfGnuMakefileGenerator.ResourceProxyVisitor(
				this, this, this.info);
		this.project.accept(visitor, 0);

		checkCancel();

		if (getSubdirList().isEmpty()) {
			String info = ManagedMakeMessages.getFormattedString(
					"MakefileGenerator.warning.no.source", this.project
							.getName());
			updateMonitor(info);
			MultiStatus status = new MultiStatus(ManagedBuilderCorePlugin
					.getUniqueIdentifier(), 1, info, null);
			status.add(new Status(1, ManagedBuilderCorePlugin
					.getUniqueIdentifier(), 1, new String(), null));
			return status;
		}

		this.topBuildDir = createDirectory(this.info.getConfigurationName());
		checkCancel();

		IPath srcsFilePath = this.topBuildDir.addTrailingSeparator().append(
				"sources.mk");
		IFile srcsFileHandle = createFile(srcsFilePath);
		populateSourcesMakefile(srcsFileHandle);
		checkCancel();

		Iterator iter = (Iterator) getSubdirList().listIterator();
		while (iter.hasNext()) {
			IContainer subDir = (IContainer) iter.next();
			try {
				populateFragmentMakefile(subDir);
			} catch (CoreException localCoreException) {
				checkCancel();
				break;
			}
			label204: checkCancel();
		}

		IPath makefilePath = this.topBuildDir.addTrailingSeparator().append(
				"makefile");
		IFile makefileHandle = createFile(makefilePath);
		if (preprocessOnly) {
			populatePreprocessOnlyMakefile(makefileHandle, true);
		} else {
			populateTopMakefile(makefileHandle, true);
		}
		checkCancel();

		IPath objFilePath = this.topBuildDir.addTrailingSeparator().append(
				"objects.mk");
		IFile objsFileHandle = createFile(objFilePath);
		populateObjectsMakefile(objsFileHandle);
		checkCancel();
		MultiStatus status;
		if (!getInvalidDirList().isEmpty()) {
			status = new MultiStatus(ManagedBuilderCorePlugin
					.getUniqueIdentifier(), 2, new String(), null);

			iter = getInvalidDirList().iterator();
			while (iter.hasNext())
				status.add(new Status(2, ManagedBuilderCorePlugin
						.getUniqueIdentifier(), 0, ((IContainer) iter.next())
						.getFullPath().toString(), null));
		} else {
			status = new MultiStatus(ManagedBuilderCorePlugin
					.getUniqueIdentifier(), 0, new String(), null);
		}
		return status;
	}

	private void removeGeneratedDirectory(IContainer subDir) {
		try {
			if ((!subDir.exists()) || (subDir.members().length <= 0))
				// break label21;
				// label21:
				return;
		} catch (CoreException localCoreException1) {
			IPath moduleRelativePath = subDir.getProjectRelativePath();
			IPath buildRoot = getBuildWorkingDir();
			if (buildRoot == null) {
				return;
			}
			IPath moduleOutputPath = buildRoot.append(moduleRelativePath);
			IFolder folder = this.project.getFolder(moduleOutputPath);
			if (!folder.exists())
				return;
			try {
				folder.delete(true, new SubProgressMonitor(this.monitor, 1));
			} catch (CoreException localCoreException2) {
			}
		}
	}

	protected void updateMonitor(String msg) {
		if ((this.monitor != null) && (!this.monitor.isCanceled())) {
			this.monitor.subTask(msg);
			this.monitor.worked(1);
		}
	}
}
