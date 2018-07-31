package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch;

import com.bjsasc.gdbdebugger.eclipse.plugins.PluginsPlugin;
import com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.external.SzmonLaunchConfigurationDelegate;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Timer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

public class SzmonLaunchConfigurationTab extends AbstractLaunchConfigurationTab
{
  public static final String FIRST_EDIT = "editedByExternalToolsMainTab";
  private static final int LOCAL = 0;
  private static final int REMOTE = 1;
  protected Image IMAGE;
  protected TCPSettingsBlock ipField;
  protected String fHostNameFieldValue;
  protected String fPortNumberFieldValue;
  protected String fExternalFieldValue;
  protected boolean fTerminateAfterValue;
  protected String fTypeNameFieldValue;
  protected String fTtyNameFieldValue;
  protected String fBaudFieldValue;
  protected Combo startType;
  protected Button fTerminateAfter;
  protected Composite group_local;
  protected Composite group_remote;
  protected Composite mainComposite;
  //protected CLabel fStatus;//hidden by lichengfei 2012-08-27
  protected SelectionAdapter selectionAdapter;
  protected boolean fInitializing;
  private boolean userEdited;
  
  class TCPSettingsListener
  implements TCPSettingsBlockListener
{
  final SzmonLaunchConfigurationTab this$0;

  TCPSettingsListener(SzmonLaunchConfigurationTab paramSzmonLaunchConfigurationTab)
  {
    this.this$0 = paramSzmonLaunchConfigurationTab;
  }
  public void Set_Baudfield(String arg0) {
    this.this$0.fBaudFieldValue = arg0;
  }

  public void Set_Hostfield(String arg0)
  {
    this.this$0.fHostNameFieldValue = arg0;
  }

  public void Set_Portfield(String arg0)
  {
    this.this$0.fPortNumberFieldValue = arg0;
  }

  public void Set_Ttyfield(String arg0)
  {
    this.this$0.fTtyNameFieldValue = arg0;
  }

  public void Set_Typefield(String arg0)
  {
    this.this$0.fTypeNameFieldValue = arg0;
  }
}

  public SzmonLaunchConfigurationTab()
  {
    this.IMAGE = 
      PluginsPlugin.getImageDescriptor("icons/small_gr.gif").createImage();
    this.fTypeNameFieldValue = "";
    this.fTtyNameFieldValue = "";
    this.fBaudFieldValue = "";
    this.fInitializing = false;
    this.userEdited = false;
  }

  public void dispose() {
    this.IMAGE.dispose();
    super.dispose();
  }

  public void createControl(Composite parent) {
    Composite top = new Composite(parent, 0);
    setControl(top);
    GridLayout top_layout = new GridLayout();
    top_layout.numColumns = 1;
    GridData top_gridData = new GridData(1808);
    top.setLayout(top_layout);
    top.setLayoutData(top_gridData);
    Composite comboComp = new Composite(top, 0);
    GridLayout cb_layout = new GridLayout(2, false);
    comboComp.setLayout(cb_layout);
    GridData cb_gd = new GridData(768);
    cb_gd.horizontalSpan = 1;
    comboComp.setLayoutData(cb_gd);
    Label dlabel = new Label(comboComp, 0);
    dlabel.setText(Messages.getString("SZMONLaunchTab.Combo.label"));
    this.startType = new Combo(comboComp, 12);
    this.startType.setLayoutData(new GridData(768));
    this.startType.add(
      Messages.getString("SZMONLaunchTab.Combo.startremote"), 0);
    this.mainComposite = new Group(top, 0);
    this.mainComposite.setFont(parent.getFont());
    this.mainComposite.setLayout(new FillLayout(768));
    this.mainComposite.setLayoutData(new GridData(1808));
    recreate();
    Dialog.applyDialogFont(parent);
  }

  protected void removeAll() {
    removeAllChildren(this.mainComposite);
   // this.fStatus = null;//hidden by lichengfei 2012-08-27
  }

  protected void removeAllChildren(Composite comp) {
    Control[] children = comp.getChildren();
    for (int i = 0; i < children.length; ++i)
      children[i].dispose();
  }

  protected void recreate() {
    removeAll();
    this.group_remote = new Composite(this.mainComposite, 0);
    GridLayout gr_layout = new GridLayout();
    gr_layout.numColumns = 1;
    this.group_remote.setLayout(gr_layout);
    this.ipField = 
      new TCPSettingsBlock(Constants.ATTR_LAUNCH_IPFIELD_type, 
      Constants.ATTR_LAUNCH_IPFIELD_host, 
      Constants.ATTR_LAUNCH_IPFIELD_port, 
      Constants.ATTR_LAUNCH_IPFIELD_tty, "socket", "localhost", 
      "2222", "/dev/ttyS0", this.fTypeNameFieldValue, 
      this.fHostNameFieldValue, this.fPortNumberFieldValue, 
      this.fTtyNameFieldValue, this.fBaudFieldValue);
    this.ipField.createBlock(this.group_remote);
    this.ipField.AddListener(new SzmonLaunchConfigurationTab.TCPSettingsListener(this));
    this.mainComposite.layout(true);
    this.mainComposite.update();
  }

  protected int getStartMode()
  {
    return this.startType.getSelectionIndex();
  }

  private String getStartType(int type) {
    return "remote";
  }

  private int getStartType(String type) {
    return 0;
  }

 /* public void updateStatus() {
    if ((this.fStatus != null) && (!this.fStatus.isDisposed())) {
      this.fStatus.setImage(null);
      String statustext = "";
      try
      {
        if (SzmonLaunchConfigurationDelegate.isRunning(this.fExternalFieldValue)) {
          statustext = "Launcher is already running. It will not be launched again unless you terminate the old one. ";
        } else {
          int port = 
            SzmonLaunchConfigurationDelegate.getPort(this.fExternalFieldValue);
          if (port == -2) {
            statustext = "Launcher is already running. It will not be launched again unless you terminate the old one. ";
          } else if (port != -1) {
            statustext = "Launcher uses port " + port + ":";
            try {
              ServerSocket serverSocket = new ServerSocket(port);
              statustext = statustext + " port is free";
              serverSocket.close();
            } catch (IOException e) {
              statustext = statustext + 
                " port is occupied. \nThis normally means that a TSIM-LEON3/SZMON \nprocess is running outside eclipse,\n or a instance of TSIM-LEON3/SZMON was not properly terminated.\n On Windows use the Taskmanager to terminate a TSIM-LEON3/SZMON process,\n on Linux use the \"killall\" command.";
              Timer localTimer = new Timer();
            }
          }
        }
      } catch (CoreException localCoreException) {
      }
      this.fStatus.setText(statustext);
      this.fStatus.pack();
    }
  }*///hidden by lichengfei 2012-08-27 没有人调用该函数

  public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
    configuration.setAttribute("editedByExternalToolsMainTab", true);
    configuration.setAttribute(Constants.ATTR_LAUNCH_IPFIELD_starttype, 
      getStartType(0));

    configuration
      .setAttribute(Constants.ATTR_LAUNCH_IPFIELD_type, "sim");
    configuration.setAttribute(Constants.ATTR_LAUNCH_IPFIELD_host, 
      "localhost");
    configuration.setAttribute(Constants.ATTR_LAUNCH_IPFIELD_port, "1234");
    configuration.setAttribute(Constants.ATTR_LAUNCH_IPFIELD_tty, 
      "/dev/ttyS0");

    configuration.setAttribute(Constants.ATTR_LAUNCH_IPFIELD_BAUD, "38400");

    configuration.setAttribute(Constants.ATTR_LAUNCH_external_terminate, 
      false);
  }

  public void initializeFrom(ILaunchConfiguration configuration) {
    this.fInitializing = true;
    updateStartType(configuration);
    updateIpfields(configuration);
    updateExternal(configuration);
    this.fInitializing = false;
    setDirty(false);
    recreate();
  }

  protected void updateStartType(ILaunchConfiguration configuration) {
    int type = 0;
    try {
      String starttype = configuration.getAttribute(
        Constants.ATTR_LAUNCH_IPFIELD_starttype, "remote");
      type = getStartType(starttype);
    }
    catch (CoreException ce) {
      PluginsPlugin.getDefault()
        .log(
        "SzmonLaunchConfigurationTab - updateStartType: Error reading configuration", 
        ce);
    }
    this.startType.select(type);
  }

  protected void updateExternal(ILaunchConfiguration configuration) {
    try {
      this.fExternalFieldValue = configuration
        .getAttribute(Constants.ATTR_LAUNCH_external, "");
      this.fTerminateAfterValue = configuration
        .getAttribute(Constants.ATTR_LAUNCH_external_terminate, false);
    }
    catch (CoreException ce) {
      PluginsPlugin.getDefault()
        .log(
        "SzmonLaunchConfigurationTab - updateExternal: Error reading configuration", 
        ce);
    }
  }

  protected void updateIpfields(ILaunchConfiguration configuration)
  {
    String type = "tty";
    String host = "localhost";
    String port = "2222";
    String tty = "/dev/ttyS0";
    String baud = "38400";
    try
    {
      type = configuration.getAttribute(
        Constants.ATTR_LAUNCH_IPFIELD_type, "");
      host = configuration.getAttribute(
        Constants.ATTR_LAUNCH_IPFIELD_host, "");
      port = configuration.getAttribute(
        Constants.ATTR_LAUNCH_IPFIELD_port, "");
      tty = configuration.getAttribute(Constants.ATTR_LAUNCH_IPFIELD_tty, 
        "");
      baud = configuration.getAttribute(
        Constants.ATTR_LAUNCH_IPFIELD_BAUD, "");
    }
    catch (CoreException ce) {
      PluginsPlugin.getDefault()
        .log(
        "SzmonLaunchConfigurationTab - updateIpfields: Error reading configuration", 
        ce);
    }
    this.fTypeNameFieldValue = type;
    this.fHostNameFieldValue = host;
    this.fPortNumberFieldValue = port;
    this.fTtyNameFieldValue = tty;
    this.fBaudFieldValue = baud;
    getStartMode();
  }

  public void performApply(ILaunchConfigurationWorkingCopy configuration) {
    String type = this.fTypeNameFieldValue.trim();
    if (type.length() == 0)
      configuration.setAttribute(Constants.ATTR_LAUNCH_IPFIELD_type, "");
    else
      configuration
        .setAttribute(Constants.ATTR_LAUNCH_IPFIELD_type, type);
    String host = this.fHostNameFieldValue.trim();
    if (host.length() == 0)
      configuration.setAttribute(Constants.ATTR_LAUNCH_IPFIELD_host, "");
    else
      configuration
        .setAttribute(Constants.ATTR_LAUNCH_IPFIELD_host, host);
    String port = this.fPortNumberFieldValue.trim();
    if (port.length() == 0)
      configuration.setAttribute(Constants.ATTR_LAUNCH_IPFIELD_port, "");
    else
      configuration
        .setAttribute(Constants.ATTR_LAUNCH_IPFIELD_port, port);
    String tty = this.fTtyNameFieldValue.trim();
    if (tty.length() == 0)
      configuration.setAttribute(Constants.ATTR_LAUNCH_IPFIELD_tty, "");
    else
      configuration.setAttribute(Constants.ATTR_LAUNCH_IPFIELD_tty, tty);
    String baud = this.fBaudFieldValue.trim();
    if (baud.length() == 0)
      configuration.setAttribute(Constants.ATTR_LAUNCH_IPFIELD_BAUD, "");
    else
      configuration
        .setAttribute(Constants.ATTR_LAUNCH_IPFIELD_BAUD, baud);
    String external = this.fExternalFieldValue.trim();
    if (external.length() == 0)
      configuration.setAttribute(Constants.ATTR_LAUNCH_external, "");
    else
      configuration
        .setAttribute(Constants.ATTR_LAUNCH_external, external);
    configuration.setAttribute(Constants.ATTR_LAUNCH_external_terminate, 
      this.fTerminateAfterValue);
    int sel = this.startType.getSelectionIndex();
    configuration.setAttribute(Constants.ATTR_LAUNCH_IPFIELD_starttype, 
      getStartType(sel));
    if (getStartMode() == 0) {
      port = "2222";
      host = "localhost";
    }
    configuration.setAttribute(Constants.ATTR_LAUNCH_GDB_host, host);
    configuration.setAttribute(Constants.ATTR_LAUNCH_GDB_port, port);
    if (this.userEdited)
      configuration.setAttribute("editedByExternalToolsMainTab", "");
  }

  public String getName() {
    return Messages.getString("SZMONLaunchTab.0");
  }

  public boolean isValid(ILaunchConfiguration launchConfig) {
    setErrorMessage(null);
    setMessage(null);
    try {
      launchConfig.getAttribute("editedByExternalToolsMainTab", false);
    } catch (CoreException localCoreException) {
    }
    if (!validateStartType()) {
      return false;
    }
    return validateIPFields();
  }

  protected boolean validateStartType() {
    int sel = this.startType.getSelectionIndex();
    if (sel == -1) {
      setErrorMessage(Messages.getString("SZMONLaunchTab.Typeselect"));
      return false;
    }

    return true;
  }

  protected boolean validateExternal() {
    ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
    ILaunchConfigurationType programType = manager
      .getLaunchConfigurationType("com.gaisler.eclipse.plugins.cdt.launch.external.LaunchConfigurationType");
    ILaunchConfiguration[] launch = (ILaunchConfiguration[])null;
    try {
      launch = manager.getLaunchConfigurations(programType);
    } catch (CoreException e) {
      e.printStackTrace();
    }
  //Begin: added by lichengfei 2012-08-27
    if(launch==null)
    	return false;
  //End: added by lichengfei 2012-08-27
    for (int i = 0; i < launch.length; ++i) {
      ILaunchConfiguration l = launch[i];
      String n = l.getName();
      if (this.fExternalFieldValue.compareTo(n) == 0) {
        return true;
      }
    }
    setErrorMessage("No launcher selected");
    return false;
  }

  protected boolean validateIPFields() {
    if ((getStartMode() == 1) && ((
      (this.fHostNameFieldValue.trim().length() == 0) || (
      this.fPortNumberFieldValue.trim().length() == 0)))) {
      setErrorMessage(
        Messages.getString("SZMONLaunchTab.IPArgumentmissing"));
      return false;
    }

    return true;
  }

  public Image getImage() {
    return this.IMAGE;
  }

  public void deactivated(ILaunchConfigurationWorkingCopy ilaunchconfigurationworkingcopy1)
  {
  }

  public void activated(ILaunchConfigurationWorkingCopy ilaunchconfigurationworkingcopy1)
  {
  }
}
