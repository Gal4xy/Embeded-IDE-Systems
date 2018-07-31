package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.external.gui;

import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ModuleOptions extends Composite
{
  private Group group = null;
  private Label label = null;
  private Text location = null;
  private Button button = null;

  public ModuleOptions(Composite parent, int style)
  {
    super(parent, style);
    initialize();
  }

  public void setText(String text)
  {
    this.group.setText(text);
  }

  public String getText()
  {
    return this.group.getText();
  }

  private void initialize()
  {
    createGroup();
    setSize(new Point(300, 200));
    setLayout(new FillLayout());
  }

  private void createGroup()
  {
    GridData gridData1 = new GridData();
    gridData1.horizontalAlignment = 4;
    gridData1.verticalAlignment = 2;
    GridData gridData = new GridData();
    gridData.horizontalAlignment = 4;
    gridData.grabExcessHorizontalSpace = true;
    gridData.verticalAlignment = 2;
    GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = 3;
    this.group = new Group(this, 0);
    this.group.setLayout(gridLayout);
    this.label = new Label(this.group, 0);
    this.label.setText("Path");
    this.label.setLayoutData(gridData1);
    this.location = new Text(this.group, 2048);
    this.location.setLayoutData(gridData);
    this.button = new Button(this.group, 0);
    this.button.setText("Browse...");
    this.button.addSelectionListener(new SelectionAdapter(){
    	public void widgetSelected(SelectionEvent e)
    	  {
    	    FileDialog dialog = new FileDialog(getShell());
    	    dialog.setFileName(getText());
    	    String file = dialog.open();
    	    if (file != null)
    	     setText(file);
    	  }
    });
  }

  public String getModulePath()
  {
    return this.location.getText().trim();
  }

  public void setModulePath(String path)
  {
    this.location.setText(path);
  }

  public void addModifyListener(ModifyListener listener)
  {
    this.location.addModifyListener(listener);
  }

  public void removeModifyListener(ModifyListener listener)
  {
    this.location.removeModifyListener(listener);
  }
}
