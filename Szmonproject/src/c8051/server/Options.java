package c8051.server;

/**
 * @since 2018/03/06
 */


import java.io.File;
import java.util.Dictionary;
import java.util.Map;
import org.eclipse.cdt.utils.ui.controls.ControlFactory;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.StringVariableSelectionDialog;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
//import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.externaltools.internal.launchConfigurations.ExternalToolsLaunchConfigurationMessages;
import org.osgi.framework.Bundle;

public  class Options extends AbstractLaunchConfigurationTab
{
  protected Options.WidgetListener fListener = new Options.WidgetListener(this);
 
  
  Text fLocationField;
  protected Text workDirectoryField;
  protected Button fileLocationButton;
  protected Button workspaceLocationButton;
  protected Button variablesLocationButton;
  protected Button fileWorkingDirectoryButton;
  protected Button workspaceWorkingDirectoryButton;
  protected Button variablesWorkingDirectoryButton;
  protected Label fCdtWarningMsg;
  boolean fInitializing = false;
  protected String fBase;
  protected String fAttrname;
  protected String fWorkname;
  protected String fArgname;
  protected String fDefault;
  
  
  public Options() {
	  
  }
  
  public class WidgetListener extends SelectionAdapter
  implements ModifyListener
{
  protected WidgetListener(Options paramOptions)
  {
  }

  public void modifyText(ModifyEvent e)
  {
    if (!fInitializing) {
//      Options.access$0(this.this$0, true);
//      Options.access$1(this.this$0);
      workDirectoryField.update();
      fLocationField.update();
    }
  }

  public void widgetSelected(SelectionEvent e) {
    Object source = e.getSource();
    if (source == workspaceLocationButton)
      return;
    if (source == fileLocationButton) {
      FileDialog fileDialog = new FileDialog(getShell(), 0);
      IPath path = new Path(fLocationField.getText());
      fileDialog.setFilterPath(path.removeLastSegments(1).toOSString());
      fileDialog.setFileName(path.lastSegment());
      String text = fileDialog.open();
      if (text != null)
        fLocationField.setText(text);
    }
    else if (source == workspaceWorkingDirectoryButton) {
      //handleWorkspaceWorkingDirectoryButtonSelected();
    } else if (source == fileWorkingDirectoryButton) {
      handleFileWorkingDirectoryButtonSelected();
    }
    else if (source == variablesLocationButton) {
//      Options.access$3(this.this$0, fLocationField);
    	handleVariablesButtonSelected(fLocationField);
    } else if (source == variablesWorkingDirectoryButton) {
//      Options.access$3(this.this$0, workDirectoryField);
    	handleVariablesButtonSelected(workDirectoryField);
    }
  }
}

  public Options(String base, String def)
  {
    this.fBase = base;
    this.fAttrname = (base + ".name");
    this.fWorkname = (base + ".workspace");
    this.fArgname = (base + ".arguments");
    this.fDefault = def;
  }

  protected String newVariableExpression(String varName, String arg)
  {
    return VariablesPlugin.getDefault().getStringVariableManager().generateVariableExpression(varName, arg);
  }

 protected void handleWorkspaceWorkingDirectoryButtonSelected()
  {
//   ContainerSelectionDialog containerDialog = new ContainerSelectionDialog(
//     getShell(), 
//     ResourcesPlugin.getWorkspace().getRoot(), 
//     false, 
//      ExternalToolsLaunchConfigurationMessages.ExternalToolsMainTab_23);
//    containerDialog.open();
//    Object[] resource = containerDialog.getResult();
//    String text = null;
//    if ((resource != null) && (resource.length > 0)) {
//      text = newVariableExpression("workspace_loc", ((IPath)resource[0]).toString());
//    }
//    if (text != null)
//      this.workDirectoryField.setText(text);
  }

  protected void handleFileWorkingDirectoryButtonSelected()
  {
    DirectoryDialog dialog = new DirectoryDialog(getShell(), 8192);
    dialog.setMessage(ExternalToolsLaunchConfigurationMessages.ExternalToolsMainTab_23);
    dialog.setFilterPath(this.workDirectoryField.getText());
    String text = dialog.open();
    if (text != null)
      this.workDirectoryField.setText(text);
  }

  private void handleVariablesButtonSelected(Text textField)
  {
    String variable = getVariable();
    if (variable != null)
      textField.insert(variable);
  }

  private String getVariable()
  {
    StringVariableSelectionDialog dialog = new StringVariableSelectionDialog(getShell());
    dialog.open();
    return dialog.getVariableExpression();
  }

  public void createControl(Composite parent) {
    Composite subComp = ControlFactory.createCompositeEx(parent, 1, 1808);
    ((GridLayout)subComp.getLayout()).makeColumnsEqualWidth = false;

    createLocationComponent(subComp);
    createWorkDirectoryComponent(subComp);
    //createArgumentComponent(subComp);
    setControl(subComp);
  }

  protected void createLocationComponent(Composite parent) {
    Group group = new Group(parent, 0);
    String locationLabel = "Executable";
    group.setText(locationLabel);
    GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    GridData gridData = new GridData(768);
    group.setLayout(layout);
    group.setLayoutData(gridData);

    this.fLocationField = new Text(group, 2048);
    gridData = new GridData(768);
    gridData.widthHint = 200;
    this.fLocationField.setLayoutData(gridData);
    this.fLocationField.addModifyListener(this.fListener);

    if (Platform.getOS().compareTo("win32") == 0) {
      Bundle b = Platform.getBundle("org.eclipse.cdt.core.win32");
      Dictionary d = b.getHeaders();
      Object v = d.get("Bundle-Version");
      String warn = "Please upgrade to at least CDT 3.0.1 to ensure that program termination will work (so that licenses are checked in)";
      if ((v != null) && (v instanceof String)) {
        String vv = (String)v;
        String[] a = vv.split("\\.");
        if ((a.length >= 3) && ((
          (Integer.decode(a[0]).intValue() >= 3) || 
          (Integer.decode(a[1]).intValue() >= 0) || 
          (Integer.decode(a[2]).intValue() >= 1)))) {
          warn = "";
        }
      }

      if (warn.length() != 0) {
        this.fCdtWarningMsg = new Label(group, 2048);
        this.fCdtWarningMsg.setText(warn);
      }
    }

    Composite buttonComposite = new Composite(group, 0);
    layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    layout.numColumns = 2;
    gridData = new GridData(128);
    buttonComposite.setLayout(layout);
    buttonComposite.setLayoutData(gridData);
    buttonComposite.setFont(parent.getFont());

    this.fileLocationButton = createPushButton(buttonComposite, ExternalToolsLaunchConfigurationMessages.ExternalToolsMainTab_Brows_e_File_System____4, null);
    this.fileLocationButton.addSelectionListener(this.fListener);

    this.variablesLocationButton = createPushButton(buttonComposite, ExternalToolsLaunchConfigurationMessages.ExternalToolsMainTab_31, null);
    this.variablesLocationButton.addSelectionListener(this.fListener);
  }

  protected void createWorkDirectoryComponent(Composite parent)
  {
    Group group = new Group(parent, 0);
    String groupName = getWorkingDirectoryLabel();
    group.setText(groupName);
    GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    GridData gridData = new GridData(768);
    group.setLayout(layout);
    group.setLayoutData(gridData);

    this.workDirectoryField = new Text(group, 2048);
    GridData data = new GridData(768);
    data.widthHint = 200;
    this.workDirectoryField.setLayoutData(data);
    this.workDirectoryField.addModifyListener(this.fListener);

    Composite buttonComposite = new Composite(group, 0);
    layout = new GridLayout();
    layout.marginWidth = 0;
    layout.marginHeight = 0;
    layout.numColumns = 3;
    gridData = new GridData(128);
    buttonComposite.setLayout(layout);
    buttonComposite.setLayoutData(gridData);
    buttonComposite.setFont(parent.getFont());

    this.workspaceWorkingDirectoryButton = createPushButton(buttonComposite, ExternalToolsLaunchConfigurationMessages.ExternalToolsMainTab_Browse_Wor_kspace____6, null);
    this.workspaceWorkingDirectoryButton.addSelectionListener(this.fListener);

    this.fileWorkingDirectoryButton = createPushButton(buttonComposite, ExternalToolsLaunchConfigurationMessages.ExternalToolsMainTab_Browse_F_ile_System____7, null);
    this.fileWorkingDirectoryButton.addSelectionListener(this.fListener);

    this.variablesWorkingDirectoryButton = createPushButton(buttonComposite, ExternalToolsLaunchConfigurationMessages.ExternalToolsMainTab_32, null);
    this.variablesWorkingDirectoryButton.addSelectionListener(this.fListener);
  }

  protected String getWorkingDirectoryLabel()
  {
    return ExternalToolsLaunchConfigurationMessages.ExternalToolsMainTab_Working__Directory__5;
  }

 
  public void setDefaults(ILaunchConfigurationWorkingCopy configuration)
  {
    configuration.setAttribute(this.fAttrname, this.fDefault);
    configuration.setAttribute(this.fWorkname, "");
    configuration.setAttribute(this.fArgname, "");
  }

  public void initializeFrom(ILaunchConfiguration configuration)
  {
    this.fInitializing = true;
    try {
      String n = configuration.getAttribute(this.fAttrname, this.fDefault);
      this.fLocationField.setText(n);
      n = configuration.getAttribute(this.fWorkname, "");
      this.workDirectoryField.setText(n); } catch (CoreException localCoreException) {
    }
    this.fInitializing = false;
  }

  public void performApply(ILaunchConfigurationWorkingCopy configuration)
  {
	  String temp = null;
    String loc = this.fLocationField.getText().trim();
    if (loc.length() == 0)
    	
      configuration.setAttribute(this.fAttrname, temp);
    else {
      configuration.setAttribute(this.fAttrname, loc);
    }
    String work = this.workDirectoryField.getText().trim();
    if (work.length() == 0)
      configuration.setAttribute(this.fWorkname, temp);
    else
      configuration.setAttribute(this.fWorkname, work);
  }

  public boolean testExecutable(IPath path)
  {
    File file = new File(path.toOSString());
    if (file.exists()) {
      return true;
    }
    if (Platform.getOS().compareTo("win32") == 0)
    {
      String ext = path.getFileExtension();
      if ((ext == null) || (!ext.equals("exe")))
        return path.addFileExtension("exe").toFile().isFile();
    }
    return false;
  }

  public boolean checkExistence(String loc) {
    IPath p = new Path(loc);
    if (p.isAbsolute()) {
      if (!testExecutable(p))
      {
        setErrorMessage(loc + " is not a executable!");
        return false;
      }
      return true;
    }
    String sep = System.getProperty("path.separator");
    String path = null;
    try
    {
      if (Platform.getOS().compareTo("win32") == 0)
        path = (String)System.getenv().get("Path");
      else
        path = (String)System.getenv().get("PATH");
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
    }
    if (path != null)
    {
      String[] s = path.split(sep);
      for (int i = 0; i < s.length; ++i) {
        IPath syspath = new Path(s[i]);
        if (testExecutable(syspath.append(p))) {
          return true;
        }
      }
      setErrorMessage(loc + " was not found in PATH!");
      return false;
    }
    return true;
  }

  public boolean isValid(ILaunchConfiguration launchConfig)
  {
    String loc = this.fLocationField.getText();
    try {
      loc = VariablesPlugin.getDefault().getStringVariableManager().performStringSubstitution(loc);
    } catch (CoreException localCoreException) {
    }
    if (!checkExistence(loc)) {
      return false;
    }
    setErrorMessage(null);
    return true;
  }

@Override
public String getName() {
	// TODO 自动生成的方法存根
	return null;
}
}
