package runnotepad;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class NpMainTab extends AbstractLaunchConfigurationTab {

	private Combo ECombo;
	
	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
	   Composite comp=createComposite(parent,1,1,GridData.FILL_BOTH);
	   createEGroup(comp); 	
	   setControl(comp);	
	}

	public Composite createComposite(Composite parent,int columns,int hspan,int fill) {
		Composite c=new Composite(parent,SWT.NONE);
		c.setLayout(new GridLayout(columns,false));
		GridData gd=new GridData(fill);
		gd.horizontalSpan=hspan;
        c.setLayoutData(gd);
        return c;
	}
	
	
	public void createEGroup(Composite parent) {
		Group group=new Group(parent,SWT.NONE);
		group.setText("&Type");
		GridData gd=new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(gd);
		GridLayout layout=new GridLayout();
		layout.numColumns=1;
		group.setLayout(layout);
		
	    ECombo=new Combo(group,SWT.READ_ONLY);
		ECombo.add("6.0.0");
		gd=new GridData(GridData.FILL_HORIZONTAL);
		ECombo.setLayoutData(gd);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "main";
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		// TODO Auto-generated method stub
	    try {
	    	String ename=configuration.getAttribute("type", (String)null);
	    	  if(ename==null) {
	    		  ECombo.select(0);
	    	  }
	    	  else {
	    		  for(int i=0;i<ECombo.getItemCount();i++) {
	    			  if(ename.equals(ECombo.getItem(i))) {
	    				  ECombo.select(i);
	    			  }
	    		  }
	    	  }
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy config) {
		// TODO Auto-generated method stub
		String typeName=ECombo.getText().trim();
		if(typeName.length()==0) {
			typeName=null;
		}
		  config.setAttribute(NpConstants._TYPE, typeName);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
