package com.bjsasc.debug.core;

import org.eclipse.cdt.debug.mi.core.MIException;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

import com.bjsasc.debug.confirm.DebugConfirm;
import com.bjsasc.debug.core.mi.CLIInfoRecordInfo;
import com.bjsasc.debug.core.mi.command.CLIInfoRecord;


/**
 * @author  Administrator
 */
public class SzDebugEventListener implements IDebugEventSetListener
{
	private IDebugTarget target;
	private ILaunch external;
	private IProcess process;
	private int suspendTime = 0;
	/**
	 * @uml.property  name="dd"
	 * @uml.associationEnd
	 */
	private IDebugServer dd;

	/**
	 * Constructor
	 * @param target The target to watch
	 * @param external The external launch to watch
	 * @param terminate Should the external launch be terminated when the target terminates
	 * @param process
	 */
	public SzDebugEventListener(IDebugTarget target, ILaunch external, boolean terminate, IDebugServer dd)
	{
		this.target = target;
		this.external = external;
		this.dd  =dd;

	}

	public void closeViewById(String ID)
	{
		IViewPart view;

			view = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().findView(ID);
			if(view == null)
				return;
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(view);
			}
	public void handleDebugEvents(DebugEvent[] events)
	{
		for (int i=0; i < events.length; i++)
		{
			//System.out.println("DebugEvent:-->"+events[i]);
			try {
				switch (events[i].getKind()) {
				case DebugEvent.TERMINATE :
				  //结束以后将是否通过验证置为false
				 // DebugConfirm.setConfirmed(false);
				  
					if (target.isTerminated()) {
						/*modified by maobaolong at 2012-03-29*/
						Thread.sleep(1000);
						//{modified start by Lihuina at 2012-8-27
						//process没有被初始化过，所以去除
						//if(process!=null)
							//process.terminate();
						//}modified end by Lihuina at 2012-8-27

						if (external != null)
							external.terminate();
						try {
							if(dd!=null) {
								dd.stop();
								dd.release();
							 }
						}
						catch(Exception e){
							e.printStackTrace();
						}
						ILaunch[] launches = DebugPlugin.getDefault().getLaunchManager().getLaunches();
						boolean allTerminated=true;
						for(ILaunch launch:launches){
							IDebugTarget[]  debugTargets= launch.getDebugTargets();
							for(IDebugTarget debugTarget:debugTargets){
								//******************************************************************//
								//Begin: added by lichengfei 2012-11-22
								/*if(debugTarget!=null&& (debugTarget.isTerminated()))
								{
									//去掉已经死亡的线程
									//获取当前调试窗口中调试的板卡数目
									IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
									//获取调试视图debugview
									for(int k=0;k<windows.length;k++){
										LaunchView debugview = (LaunchView) (windows[k].getActivePage().findView("org.eclipse.debug.ui.DebugView"));
										if(debugview==null){
											try {
												debugview=(LaunchView) windows[k].getActivePage().showView("org.eclipse.debug.ui.DebugView");
											} catch (PartInitException e) {
												e.printStackTrace();
											}
										}
										ILaunch[] mylaunches=null;
										if(debugview!=null)
										{
											//获取debug树
											TreeModelViewer treeviewer = (TreeModelViewer) debugview.getViewer();
											// 获得树的输入————连接管理器
											LaunchManager LM = (LaunchManager) treeviewer.getInput();
											//获取树上的所有debug节点
											mylaunches = LM.getLaunches();
											//遍历树上所有debug节点，找到当前thread的debug节点
											for(int ii=0;ii<mylaunches.length;ii++){
												if(mylaunches[ii]==launch){
													//去掉该lauch
													LM.removeLaunch(launch);
												}
											}
										}
									}
								}*/
								//End :added by lichengfei 2012-11-22
								//******************************************************************//
								//所有连接里有一个没有结束
								if(debugTarget!=null&& (!debugTarget.isTerminated())){
									allTerminated = false;
								}
							}
						}
						if(allTerminated){
							ReverseEnableModel.getInstance().setMultiDebug(false);
							ReverseEnableModel.getInstance().setIsDebugMode(false);
				        	ReverseEnableModel.getInstance().updateEnableState();
				        	ReverseEnableModel.getInstance().setAllReverseDisable();
							/*modified by mbl at 2012-03-08
							 * 无法获得当前的window,只能获得第一个window，在多机调试时，需要考虑这个问题
							 * */
							PlatformUI.getWorkbench().getDisplay().asyncExec(new Thread() {
								public void run() {
									try {
										PlatformUI.getWorkbench().showPerspective("com.bjsasc.CPerspective", PlatformUI.getWorkbench().getWorkbenchWindows()[0]);
										//关闭inforecord视图
										closeViewById("com.bjsasc.reverseDebug.StepGoToView");//modified by lichengfei 2012-2-28

									} catch (WorkbenchException e) {
										// TODO Auto-generated catch block
										//e.printStackTrace();
										System.out.println("切换透视图失败，可能是因为透视图ID不存在:com.bjsasc.CPerspective");
									}
								}
							});

						}
						DebugPlugin.getDefault().removeDebugEventListener(this);
					}
					break;
				case DebugEvent.RESUME :
				  //重启时将是否通过验证置为false，启动时再次验证
          //DebugConfirm.setConfirmed(false);
					if (!events[i].isEvaluation() || !((events[i].getDetail() & DebugEvent.EVALUATION_IMPLICIT) != 0)) {
						ReverseEnableModel.getInstance().setAllReverseDisable();
					}
					break;
				case DebugEvent.SUSPEND :
					String osgiVersion = System.getProperty("osgi.framework.version");
					if(osgiVersion!=null&&osgiVersion.startsWith("3.5")){
						break;
					}
					suspendTime++;
					if(suspendTime%2==0)
						return;
					if(ReverseEnableModel.getInstance().geFfirstDebugMode())
					{
						ReverseEnableModel.getInstance().setInitialDebugState();
						ReverseEnableModel.getInstance().setFirstDebugMode(false);
					}

					ReverseEnableModel.getInstance().updateEnableState();
					break;
				default:

				}
			} catch (DebugException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}