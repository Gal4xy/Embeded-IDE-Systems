package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch;

import java.util.Observable;
import java.util.Vector;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TCPSettingsBlock extends Observable
{
 // private Shell fShell;//hidden by lichengfei 2012-08-27
  public Combo fgdbconType;
  public Text fHostNameField;
  public Text fPortNumberField;
  public Text TtyNumberField;
  public Text fBaudField;
  public String fgdbconTypeValue;
  public String fHostNameFieldValue;
  public String fPortNumberFieldValue;
  public String fTtyNumberFieldValue;
  public String fBaudFieldValue;
  private Control fControl;
  private String fErrorMessage;
  private String fconf_type;
  private String fconf_host;
  private String fconf_port;
  private String fconf_tty;
  private String fconf_type_default;
  private String fconf_port_default;
  private String fconf_host_default;
  private String fconf_tty_default;
  Composite fgroup;
  Composite fSelgroup;
  Vector listeners;

  public TCPSettingsBlock(String conf_type, String conf_host, String conf_port, String conf_tty, String conf_type_default, String conf_host_default, String conf_port_default, String conf_tty_default, String type_default, String host_default, String port_default, String tty_default, String baud_default)
  {
    this.fErrorMessage = null;
    this.listeners = new Vector();
    this.fconf_type = conf_type;
    this.fconf_host = conf_host;
    this.fconf_port = conf_port;
    this.fconf_tty = conf_tty;
    this.fconf_type_default = conf_type_default;
    this.fconf_host_default = conf_host_default;
    this.fconf_port_default = conf_port_default;
    this.fconf_tty_default = conf_tty_default;
    this.fgdbconTypeValue = type_default;
    this.fHostNameFieldValue = host_default;
    this.fPortNumberFieldValue = port_default;
    this.fTtyNumberFieldValue = tty_default;
    this.fBaudFieldValue = baud_default;
  }

  protected void removeAll()
  {
    Control[] children = this.fSelgroup.getChildren();
    for (int i = 0; i < children.length; ++i)
      children[i].dispose();
  }

  public void createBlock(Composite parent)
  {
	  System.out.println("createBlock");
    this.fgroup = new Composite(parent, 0);
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    GridData gridData = new GridData(768);
    this.fgroup.setLayout(layout);
    this.fgroup.setLayoutData(gridData);
    Label fgdbconTypeStatus = new Label(this.fgroup, 0);
    fgdbconTypeStatus.setText("类型");
    this.fgdbconType = new Combo(this.fgroup, 12);
    this.fgdbconType.setLayoutData(new GridData(768));
    this.fgdbconType.add("socket");
    this.fgdbconType.add("tty");
    this.fgdbconType.add("sim");
    for(int i=0;i<this.fgdbconType.getItemCount();i++){
    	System.out.println(this.fgdbconType.getItem(i));
    }
    
    if (this.fgdbconTypeValue.compareTo("socket") == 0) {
      this.fgdbconType.select(0);
    }
    else if (this.fgdbconTypeValue.compareTo("tty") == 0)
      this.fgdbconType.select(1);
    else
        this.fgdbconType.select(2);
    this.fgdbconType.addSelectionListener(new SelectionListener(){

    	  public void widgetDefaultSelected(SelectionEvent selectionevent)
    	  {
    	  }
    	  public void widgetSelected(SelectionEvent e)
    	  {
    	    fgdbconTypeValue = fgdbconType.getText();
    	    TypeChanged();
    	    update();
    	  }
    });

    this.fgdbconType.addModifyListener(new ModifyListener(){
    		  public void modifyText(ModifyEvent e)
    		  {
    		    fgdbconTypeValue = fgdbconType.getText();
    		    TypeChanged();
    		  }
    });

    Label fgdbconTypeStatusHelp = new Label(this.fgroup, 0);

    GridData gd = new GridData();
    gd.horizontalSpan = 2;
    fgdbconTypeStatusHelp.setLayoutData(gd);
    this.fSelgroup = new Composite(parent, 0);
    this.fSelgroup.setLayout(new GridLayout(2, false));
    this.fSelgroup.setLayoutData(new GridData(1808));
    update();
  }

  public void update()
  {
    removeAll();
    if (this.fgdbconType.getText().compareTo("tty") == 0)
    {
      Label TtyNumberFieldStatus = new Label(this.fSelgroup, 0);
      TtyNumberFieldStatus.setText("设备");
      this.TtyNumberField = new Text(this.fSelgroup, 2048);
      GridData data = new GridData(768);
      data.widthHint = 200;
      this.TtyNumberField.setLayoutData(data);
      this.TtyNumberField.setText(this.fTtyNumberFieldValue);
      this.TtyNumberField.addModifyListener(new ModifyListener(){
    	  public void modifyText(ModifyEvent e)
    	  {
    	    fTtyNumberFieldValue = TtyNumberField.getText();
    	    TtyChanged();
    	  }
      });

      Label BaudFieldStatus = new Label(this.fSelgroup, 0);
      BaudFieldStatus.setText("波特率");
      this.fBaudField = new Text(this.fSelgroup, 2048);
      GridData BaudFielddata = new GridData(768);
      data.widthHint = 200;
      this.fBaudField.setLayoutData(BaudFielddata);
      this.fBaudField.setText(this.fBaudFieldValue);
      this.fBaudField.addModifyListener(new ModifyListener(){
    	  public void modifyText(ModifyEvent e)
    	  {
    	    fBaudFieldValue = fBaudField.getText();
    	    BaudChanged();
    	  }
      });

      Label BaudvaluesFieldStatus = new Label(this.fSelgroup, 0);
      GridData gd = new GridData();
      gd.horizontalSpan = 2;
      BaudvaluesFieldStatus.setLayoutData(gd);
      BaudvaluesFieldStatus.setText("波特率:300,600,1200,2400,4800,9600,19200,38400,57600,115200,230400,256000");
    }
    else if (this.fgdbconType.getText().compareTo("socket") == 0)
    {
      Label fHostNameFieldStatus = new Label(this.fSelgroup, 0);
      fHostNameFieldStatus.setText("Host");
      this.fHostNameField = new Text(this.fSelgroup, 2048);
      GridData data = new GridData(768);
      data.widthHint = 200;
      this.fHostNameField.setLayoutData(data);
      this.fHostNameField.setText(this.fHostNameFieldValue);
      this.fHostNameField.addModifyListener(new ModifyListener(){
    	  public void modifyText(ModifyEvent e)
    	  {
    	    fHostNameFieldValue = fHostNameField.getText();
    	    HostChanged();
    	  }
      });

      Label fPortNumberFieldStatus = new Label(this.fSelgroup, 0);
      fPortNumberFieldStatus.setText("Port");
      this.fPortNumberField = new Text(this.fSelgroup, 2048);
      GridData pdata = new GridData(768);
      data.widthHint = 200;
      this.fPortNumberField.setLayoutData(pdata);
      this.fPortNumberField.setText(this.fPortNumberFieldValue);
      this.fPortNumberField.addModifyListener(new ModifyListener(){
    	  public void modifyText(ModifyEvent e)
    	  {
    	    fPortNumberFieldValue = fPortNumberField.getText();
    	    PortChanged();
    	  }
      });
    }

    this.fSelgroup.layout(true);
  }

 /* protected Shell getShell()
  {
    return this.fShell;
  }*///hidden by lichengfei 2012-08-27

  public void dispose()
  {
    deleteObservers();
  }

  public void initializeFrom(ILaunchConfiguration configuration)
  {
    try
    {
      this.fgdbconTypeValue = configuration.getAttribute(this.fconf_type, this.fconf_type_default);
      this.fHostNameFieldValue = configuration.getAttribute(this.fconf_host, this.fconf_host_default);
      this.fPortNumberFieldValue = configuration.getAttribute(this.fconf_port, this.fconf_port_default);
      this.fTtyNumberFieldValue = configuration.getAttribute(this.fconf_tty, this.fconf_tty_default);
    } catch (CoreException localCoreException) {
    }
    update();
  }

  public void setDefaults(ILaunchConfigurationWorkingCopy configuration)
  {
    configuration.setAttribute(this.fconf_type, this.fconf_type_default);
    configuration.setAttribute(this.fconf_host, this.fconf_host_default);
    configuration.setAttribute(this.fconf_port, this.fconf_port_default);
    configuration.setAttribute(this.fconf_tty, this.fconf_tty_default);
  }

  public void performApply(ILaunchConfigurationWorkingCopy configuration)
  {
    configuration.setAttribute(this.fconf_type, this.fgdbconTypeValue.trim());
    configuration.setAttribute(this.fconf_host, this.fHostNameFieldValue.trim());
    configuration.setAttribute(this.fconf_port, this.fPortNumberFieldValue.trim());
    configuration.setAttribute(this.fconf_tty, this.fTtyNumberFieldValue.trim());
  }

  protected void hostNameFieldChanged()
  {
    updateErrorMessage();
    setChanged();
    notifyObservers();
  }

  protected void portNumberFieldChanged()
  {
    updateErrorMessage();
    setChanged();
    notifyObservers();
  }

  public Control getControl()
  {
    return this.fControl;
  }

  protected void setControl(Control control)
  {
    this.fControl = control;
  }

  public boolean isValid(ILaunchConfiguration configuration)
  {
    updateErrorMessage();
    return true;
  }

  private void updateErrorMessage()
  {
    setErrorMessage(null);
    if ((this.fHostNameField != null) && (this.fPortNumberField != null))
      if (this.fHostNameField.getText().trim().length() == 0) {
        setErrorMessage(Messages.getString("TCPSettingsBlock.2"));
      }
      else if (!hostNameIsValid(this.fHostNameField.getText().trim())) {
        setErrorMessage(Messages.getString("TCPSettingsBlock.3"));
      }
      else if (this.fPortNumberField.getText().trim().length() == 0) {
        setErrorMessage(Messages.getString("TCPSettingsBlock.4"));
      }
      else if (!portNumberIsValid(this.fPortNumberField.getText().trim()))
        setErrorMessage(Messages.getString("TCPSettingsBlock.5"));
  }

  public String getErrorMessage()
  {
    return this.fErrorMessage;
  }

  private void setErrorMessage(String string)
  {
    this.fErrorMessage = string;
  }

  private boolean hostNameIsValid(String hostName)
  {
    return true;
  }

  private boolean portNumberIsValid(String portNumber)
  {
    int port = Short.parseShort(portNumber);

    return port >= 0;
  }

  void AddListener(TCPSettingsBlockListener l)
  {
    this.listeners.add(l);
  }

  void TypeChanged()
  {
    for (int i = 0; i < this.listeners.size(); ++i)
    {
      TCPSettingsBlockListener l = (TCPSettingsBlockListener)this.listeners.get(i);
      l.Set_Typefield(this.fgdbconTypeValue);
    }
  }

  void HostChanged()
  {
    for (int i = 0; i < this.listeners.size(); ++i)
    {
      TCPSettingsBlockListener l = (TCPSettingsBlockListener)this.listeners.get(i);
      l.Set_Hostfield(this.fHostNameFieldValue);
    }
  }

  void PortChanged()
  {
    for (int i = 0; i < this.listeners.size(); ++i)
    {
      TCPSettingsBlockListener l = (TCPSettingsBlockListener)this.listeners.get(i);
      l.Set_Portfield(this.fPortNumberFieldValue);
    }
  }

  void TtyChanged()
  {
    for (int i = 0; i < this.listeners.size(); ++i)
    {
      TCPSettingsBlockListener l = (TCPSettingsBlockListener)this.listeners.get(i);
      l.Set_Ttyfield(this.fTtyNumberFieldValue);
    }
  }

  void BaudChanged()
  {
    for (int i = 0; i < this.listeners.size(); ++i)
    {
      TCPSettingsBlockListener l = (TCPSettingsBlockListener)this.listeners.get(i);
      l.Set_Baudfield(this.fBaudFieldValue);
    }
  }
}
