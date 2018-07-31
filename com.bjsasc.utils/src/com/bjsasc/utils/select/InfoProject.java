package com.bjsasc.utils.select;

import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.core.IManagedBuildInfo;
import org.eclipse.cdt.managedbuilder.core.IToolChain;
import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;


public class InfoProject implements IWorkbenchWindowActionDelegate
{
	private IProject fProject;
	public static String simPath = "BM3803";

	@Override
	public void run(IAction action){
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		//Begin : added by lichengfei 2012-11-14 添加对工具链的判断，如果是SPARC V7,V8工程才能使能“启动现场调试”
		if(selection instanceof ITreeSelection){
			//action.setEnabled(true);//added by lichengfei  2012-09-14
			Object obj = ((ITreeSelection) selection).getFirstElement();
			if(obj instanceof IResource ){
				fProject = ((IResource) obj).getProject();
			}else if(obj instanceof ICElement){
				fProject = ((ICElement) obj).getCProject().getProject();
			}
			if(fProject == null)
			{
				action.setEnabled(false);
				return;
			}
			//获取工程的工具链信息
			IManagedBuildInfo infoBuild = ManagedBuildManager.getBuildInfo(fProject);
			if (infoBuild == null)// 如果info为null,返回
			{
				action.setEnabled(false);
				return;
			}
			IConfiguration cfg= infoBuild.getDefaultConfiguration();//得到配置信息，通过配置信息等到工具链
			IToolChain toolchain = cfg.getToolChain(); 
			String toolChainName =toolchain.getName();//获取工具链名称，如果是TSC695F或者AT697工程,则使能模拟器调试菜单
			if (toolChainName.contains("BM3803(V8)"))
					simPath = "BM3803";
			else if(toolChainName.contains("BM3101(V8)"))
				simPath = "BM3101";
			else if(toolChainName.contains("BM3109(V8)"))
				simPath = "BM3109";
			else if(toolChainName.contains("LCSOC3201(V8)"))
				simPath = "BM3803";
			else if(toolChainName.contains("AT697F(V8)"))
				simPath = "BM3803";
			else if(toolChainName.contains("ORBITA(V8)"))
				simPath = "BM3803";
			else if(toolChainName.contains("TSC695F(V7)"))
				simPath = "V7_SIM";
		}
		//End : added by lichengfei 2012-11-14 添加对工具链的判断，如果是SPARC V7,V8工程才能使能“启动现场调试”
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		
	}

}
