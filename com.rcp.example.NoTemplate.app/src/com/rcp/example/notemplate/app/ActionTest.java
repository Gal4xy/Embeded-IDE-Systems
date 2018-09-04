package com.rcp.example.notemplate.app;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class ActionTest implements IWorkbenchWindowActionDelegate{
    private IWorkbenchWindow window;
    @Override
    public void run(IAction action) {
        // TODO Auto-generated method stub
        Display display=Display.getCurrent();
        Shell shell=new Shell(display);
        MessageDialog.openInformation(
                shell,
                "PlungInClient",
                "我是ActionSet模式实现的。");
    }

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void init(IWorkbenchWindow window) {
        // TODO Auto-generated method stub
        this.window=window;
    }
}
