package c8051.server;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.externaltools.internal.ui.FileSelectionDialog;

import c8051.server.Options;


// Main Tab to start up server/target

public class ServerMainTab  extends AbstractLaunchConfigurationTab {

	  protected ILaunchConfiguration fLaunchConfiguration;
	  protected ILaunchConfigurationWorkingCopy fWorkingCopy;
	
	  private Group fOptionsGroup;
	  private Options fOptions;
	
	  
	  
	      protected ILaunchConfigurationWorkingCopy getLaunchConfigurationWorkingCopy() {
		    return this.fWorkingCopy;
		  }

		  protected void setLaunchConfiguration(ILaunchConfiguration launchConfiguration) {
		    this.fLaunchConfiguration = launchConfiguration;
		    setLaunchConfigurationWorkingCopy(null);
		  }

		  protected ILaunchConfiguration getLaunchConfiguration() {
		    return this.fLaunchConfiguration;
		  }

		  protected void setLaunchConfigurationWorkingCopy(ILaunchConfigurationWorkingCopy workingCopy) {
		    this.fWorkingCopy = workingCopy;
		  }
	  
		  //button_selected :handle workspace
		  
		  protected void handleWorkspaceLocationButtonSelected()
		  {
		    FileSelectionDialog dialog = new FileSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), "Select program");
		    dialog.open();
		    IStructuredSelection result = dialog.getResult();
		    if (result == null) {
		      return;
		    }
		    Object file = result.getFirstElement();
		    if (file instanceof IFile) {
		      StringBuffer expression = new StringBuffer();
		      expression.append("${workspace_loc:");
		      expression.append(((IFile)file).getFullPath().toString());
		      expression.append("}");
		    }
		  }
	  
	  
	@Override
	      public void createControl(Composite parent) 
	     {
			Composite mainComposite = new Composite(parent, 0);
		    setControl(mainComposite);
		    mainComposite.setFont(parent.getFont());
		    GridLayout layout = new GridLayout();
		    layout.numColumns = 1;
		    GridData gridData = new GridData(768);
		    mainComposite.setLayout(layout);
		    mainComposite.setLayoutData(gridData);
	
		    createLocationComponent(mainComposite);
	
		    Dialog.applyDialogFont(parent);
		    PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), "org.eclipse.ui.externaltools.program_main_tab_context");
		}

	
	
	
	      protected void createLocationComponent(Composite parent)
	  {
            String temp=null;
		    this.fOptionsGroup = new Group(parent, 16);
		    this.fOptionsGroup.setText("Program options");
	
		    GridLayout tabHolderLayout = new GridLayout();
		    tabHolderLayout.marginHeight = 0;
		    tabHolderLayout.marginWidth = 0;
		    tabHolderLayout.numColumns = 1;
		    this.fOptionsGroup.setLayout(tabHolderLayout);
		    GridData gd = new GridData(1808);
		    gd.horizontalSpan = 1;
		    this.fOptionsGroup.setLayoutData(gd);
		
		  
		    ILaunchConfigurationWorkingCopy wc = getLaunchConfigurationWorkingCopy();
		    if (getOptions() == null)
		    {
		      if ((wc == null) && 
		        (getLaunchConfiguration().isWorkingCopy())) {
		        wc = (ILaunchConfigurationWorkingCopy)getLaunchConfiguration();
		      }
	
		      if (wc != null)
		    	  
		        wc.setAttribute("org.eclipse.cdt.launch.DEBUGGER_SPECIFIC_ATTRS_MAP", temp);
		    }
		    else {
		      if (wc == null) {
		        try {
		          if (getLaunchConfiguration().isWorkingCopy())
		            setLaunchConfigurationWorkingCopy((ILaunchConfigurationWorkingCopy)getLaunchConfiguration());
		          else {
		            setLaunchConfigurationWorkingCopy(getLaunchConfiguration().getWorkingCopy());
		          }
		          wc = getLaunchConfigurationWorkingCopy();
		        }
		        catch (CoreException localCoreException) {
		          return;
		        }
	
		      }
	
		      getOptions().initializeFrom(wc);
		    }
	
		    setDirty(true);
		    updateLaunchConfigurationDialog();
		  
		  
		  
	  }

	   
	  
	
		  protected Options getOptions()
				{
				  return this.fOptions;
				}
			
		   protected void setOptions(Options o) 
			    {
				  this.fOptions = o;
				}
	
	@Override
	public String getName() {
		// TODO 自动生成的方法存根
		return "server main";
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		setLaunchConfiguration(configuration);
	      if (getOptions() != null)
	        getOptions().initializeFrom(configuration);
	        setDirty(false);
		}

	public void setLaunchConfigurationDialog(ILaunchConfigurationDialog dialog) {
	    super.setLaunchConfigurationDialog(dialog);
	  }
	
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		 String temp = null;
		    if (getOptions() != null)
		      getOptions().performApply(configuration);
		
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy arg0) {
		// TODO 自动生成的方法存根
		
	}

	
	 public boolean isValid(ILaunchConfiguration launchConfig)
	  {
	    setErrorMessage(null);
	    if (getOptions() == null) {
	      setErrorMessage("Select which program to start");
	      return false;
	    }
	    return getOptions().isValid(launchConfig);
	  }

	  public String getErrorMessage()
	  {
	    return this.fOptions.getErrorMessage();
	  }
	

}
