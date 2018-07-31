package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.external;

import com.bjsasc.gdbdebugger.eclipse.plugins.PluginsPlugin;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.externaltools.internal.ui.FileSelectionDialog;
import org.eclipse.ui.help.IWorkbenchHelpSystem;

public class SzmonLaunchProgramTab extends AbstractLaunchConfigurationTab
{
  protected ILaunchConfiguration fLaunchConfiguration;
  protected ILaunchConfigurationWorkingCopy fWorkingCopy;
  private Image ICON = PluginsPlugin.getImageDescriptor("icons/small_gr.gif").createImage();
  private Combo fProgType;
  private Group fOptionsGroup;
  private SzmonOptions fSzmonOptions = new SzmonOptions();
  private TsimOptions fTsimLeon3Options = new TsimOptions();
  private Options fOptions;

  public void dispose()
  {
    this.ICON.dispose();
  }

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
    this.fProgType = new Combo(parent, 12);
    this.fProgType.add("TSIM2");
    this.fProgType.add("SZMON");
    this.fProgType.addModifyListener(new ModifyListener(){
    	 public void modifyText(ModifyEvent e)
    	  {
    	    handle_progtype();
    	  }
    });

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
  }

  void handle_progtype() {
	  String temp =null;
    if (this.fProgType.getText().compareTo("TSIM2") == 0)
      setOptions(this.fTsimLeon3Options);
    else if (this.fProgType.getText().compareTo("SZMON") == 0) {
      setOptions(this.fSzmonOptions);
    }
    loadDynamicDebugArea();

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

  protected void loadDynamicDebugArea()
  {
    Control[] children = this.fOptionsGroup.getChildren();
    for (int i = 0; i < children.length; ++i) {
      children[i].dispose();
    }
    if (getOptions() == null)
      return;
    getOptions().setLaunchConfigurationDialog(getLaunchConfigurationDialog());
    getOptions().createControl(this.fOptionsGroup);

    this.fOptionsGroup.layout(true);
  }

  protected Options getOptions()
  {
    return this.fOptions;
  }

  protected void setOptions(Options o) {
    this.fOptions = o;
  }

  public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
    configuration.setAttribute("com.gaisler.eclipse.plugins.cdt.singlelaunch.PROGRAM_TYPE", "TSIM2");
  }

  public void initializeFrom(ILaunchConfiguration configuration)
  {
    setLaunchConfiguration(configuration);
    try {
      int i = this.fProgType.indexOf(configuration.getAttribute("com.gaisler.eclipse.plugins.cdt.singlelaunch.PROGRAM_TYPE", ""));
      if (i != -1) {
        this.fProgType.select(i);
      }
      loadDynamicDebugArea();
      if (getOptions() != null)
        getOptions().initializeFrom(configuration);
    }
    catch (CoreException localCoreException) {
    }
    setDirty(false);
  }

  public void setLaunchConfigurationDialog(ILaunchConfigurationDialog dialog) {
    super.setLaunchConfigurationDialog(dialog);
  }

  public void performApply(ILaunchConfigurationWorkingCopy configuration)
  {
    String type = this.fProgType.getText().trim();
    String temp = null;
    if (type.length() == 0)
      configuration.setAttribute("com.gaisler.eclipse.plugins.cdt.singlelaunch.PROGRAM_TYPE", temp);
    else {
      configuration.setAttribute("com.gaisler.eclipse.plugins.cdt.singlelaunch.PROGRAM_TYPE", type);
    }
    if (getOptions() != null)
      getOptions().performApply(configuration);
  }

  public String getName()
  {
    return "Main";
  }

  public Image getImage()
  {
    return this.ICON;
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
