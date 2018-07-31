package com.bjsasc.utils.select;

import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;

public class SelectionChooser {
	private IWorkbenchWindow window = null;

	public IResource[] getSelectedResources() {
		IResource[] retRes = null;
		if (this.window == null)
			return null;
		ISelection s = window.getActivePage().getSelection();
		if (s instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) s;
			retRes  = new IResource[ss.size()];
			if (!ss.isEmpty()) {
				for(int i=0;i<ss.size();i++)
				{
					Object obj = ss.toArray()[i];
					//MessageDialog.openInformation(window.getShell(), "TestSelection", obj.getClass().toString());
					if (obj instanceof ICElement) {
						ICElement elem = (ICElement) obj;
						IResource rs = elem.getResource();
						retRes[i]=rs;
					}
					if(obj instanceof IResource)
						retRes[i]=(IResource) obj;
				}
			}
		}

		return retRes;
	} 
	
	public IResource getSelectedResource() {
		if (this.window == null)
			return null;
		ISelection s = window.getActivePage().getSelection();
		if (s instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) s;
			if (!ss.isEmpty()) {
				Object obj = ss.getFirstElement();
				//MessageDialog.openInformation(window.getShell(), "TestSelection", obj.getClass().toString());
				if (obj instanceof ICElement) {
					ICElement elem = (ICElement) obj;
					IResource rs = elem.getResource();
					return rs;
				}
				if(obj instanceof IResource)
					return (IResource)obj;
				
			}
		}

		return null;
	} 
	
	public void setWindow(IWorkbenchWindow window) {
		this.window = window;
	}
	
}
