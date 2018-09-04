package c8051.server;

/**
 * @author Galaxy
 * @since 2018/03/06
 */

import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public class SzmonLaunchOptions extends LaunchOptions
{
  private static final int SERIAL = 0;
  private static final int USB = 1;
  private static final int JTAG = 2;
  private static final int ETHERNET = 3;
  private static final int PCI = 4;
  private static final int GRESB = 5;
  private static final int GRUSB = 6;
  private static final int ALTJTAG = 7;
  private Combo interfaceCombo = null;
  private Combo deviceCombo = null;
  private Combo baudCombo = null;
  private Text textField = null;

  private Button loopback = null;
  private Label label3 = null;
  private Text extra = null;
  private Group group = null;
  private Button nosram = null;
  private ModuleOptions moduleOptions = null;
  private Group group1 = null;
  private Group group2 = null;
  private Label label4 = null;
  private Label label = null;
  private Label label5 = null;
  private Label textFieldLabel = null;
  private Composite composite = null;
  private Group group3 = null;
  private Text sna = null;
  private Text dna = null;
  private Label label1 = null;
  private Label label2 = null;
  private Label label6 = null;
  private Text link = null;
  private Label label7 = null;
  private Spinner clkdiv = null;
  private Composite composite1 = null;

  public SzmonLaunchOptions(Composite parent, int style)
  {
    super(parent, style);
    initialize();
  }

  public void dispose()
  {
    this.interfaceCombo.removeModifyListener(this.listener);
    this.deviceCombo.removeModifyListener(this.listener);
    this.baudCombo.removeModifyListener(this.listener);
    this.textField.removeModifyListener(this.listener);
    this.loopback.removeSelectionListener(this.listener);
    this.extra.removeModifyListener(this.listener);
    this.nosram.removeSelectionListener(this.listener);
    super.dispose();
  }

  private void initialize()
  {
    setLayout(new FillLayout());
    setSize(new Point(655, 343));
    GridLayout gridLayout10 = new GridLayout();
    gridLayout10.numColumns = 2;
    setLayout(new FillLayout());
    this.group = new Group(this, 0);
    this.group.setText("SZMON startup options");
    createComposite();
    this.group.setLayout(gridLayout10);
    GridData gridData51 = new GridData();
    gridData51.horizontalAlignment = 4;
    gridData51.verticalAlignment = 2;
    GridData gridData1 = new GridData();
    gridData1.horizontalAlignment = 4;
    gridData1.grabExcessHorizontalSpace = true;
    gridData1.verticalAlignment = 2;
    this.label3 = new Label(this.group, 0);
    this.label3.setText("Extra options");
    this.label3.setLayoutData(gridData51);
    this.extra = new Text(this.group, 2048);
    this.extra.setLayoutData(gridData1);
    this.extra.addModifyListener(this.listener);

    updateInterface();
    pack();
  }

  private void createInterfaceCombo()
  {
    GridData gridData5 = new GridData();
    gridData5.horizontalAlignment = 4;
    gridData5.grabExcessHorizontalSpace = true;
    gridData5.verticalAlignment = 2;
    this.interfaceCombo = new Combo(this.group1, 12);
    this.interfaceCombo.setLayoutData(gridData5);
    this.interfaceCombo.add("Serial", 0);
    this.interfaceCombo.add("USB", 1);
    this.interfaceCombo.add("JTAG", 2);
    this.interfaceCombo.add("Ethernet", 3);
    this.interfaceCombo.add("PCI", 4);
    this.interfaceCombo.add("GRESB", 5);
    this.interfaceCombo.add("GRUSB", 6);
    this.interfaceCombo.add("Altera JTAG", 7);
    this.interfaceCombo.addSelectionListener(new SelectionAdapter(){
    	 public void widgetSelected(SelectionEvent e)
    	  {
    	    sendUpdateEvent(new Event());
    	  }
    });
  }

  private void createDeviceCombo()
  {
    GridData gridData4 = new GridData();
    gridData4.horizontalAlignment = 4;
    gridData4.grabExcessHorizontalSpace = true;
    gridData4.verticalAlignment = 2;
    this.deviceCombo = new Combo(this.group1, 0);
    this.deviceCombo.setLayoutData(gridData4);
    this.deviceCombo.add("com1");
    this.deviceCombo.add("com2");
    this.deviceCombo.add("com3");
    this.deviceCombo.add("com4");
    this.deviceCombo.add("/dev/ttyS0");
    this.deviceCombo.add("/dev/ttyS1");
    this.deviceCombo.add("/dev/USB0");
    this.deviceCombo.add("/dev/USB1");
    this.deviceCombo.add("/dev/phob0a");
    this.deviceCombo.add("/dev/phob1a");
    this.deviceCombo.addModifyListener(this.listener);
  }

  private void createBaudCombo()
  {
    GridData gridData3 = new GridData();
    gridData3.horizontalAlignment = 4;
    gridData3.grabExcessHorizontalSpace = true;
    gridData3.verticalAlignment = 2;
    this.baudCombo = new Combo(this.group1, 0);
    this.baudCombo.setLayoutData(gridData3);
    this.baudCombo.add("");
    this.baudCombo.add("9600");
    this.baudCombo.add("19200");
    this.baudCombo.add("38400");
    this.baudCombo.add("57600");
    this.baudCombo.add("115200");
    this.baudCombo.add("230400");
    this.baudCombo.add("460800");
    this.baudCombo.addModifyListener(this.listener);
  }

  private void updateInterface()
  {
    int type = this.interfaceCombo.getSelectionIndex();
    switch (type)
    {
    case 0:
      this.deviceCombo.setEnabled(true);
      this.baudCombo.setEnabled(true);

      this.textField.setEnabled(false);
      this.dna.setEnabled(false);
      this.sna.setEnabled(false);
      this.link.setEnabled(false);
      this.clkdiv.setEnabled(false);
      break;
    case 1:
    case 2:
    case 6:
      this.deviceCombo.setEnabled(false);
      this.baudCombo.setEnabled(false);

      this.textField.setEnabled(false);
      this.dna.setEnabled(false);
      this.sna.setEnabled(false);
      this.link.setEnabled(false);
      this.clkdiv.setEnabled(false);
      break;
    case 5:
      this.deviceCombo.setEnabled(false);
      this.baudCombo.setEnabled(false);
      this.textFieldLabel.setText("Target IP");
      this.textField.setEnabled(true);
      this.dna.setEnabled(true);
      this.sna.setEnabled(true);
      this.link.setEnabled(true);
      this.clkdiv.setEnabled(true);
      break;
    case 3:
      this.deviceCombo.setEnabled(false);
      this.baudCombo.setEnabled(false);
      this.textFieldLabel.setText("Target IP");
      this.textField.setEnabled(true);
      this.dna.setEnabled(false);
      this.sna.setEnabled(false);
      this.link.setEnabled(false);
      this.clkdiv.setEnabled(false);

      break;
    case 4:
      this.deviceCombo.setEnabled(false);
      this.baudCombo.setEnabled(false);
      this.textFieldLabel.setText("PCI device");
      this.textField.setEnabled(true);
      this.dna.setEnabled(false);
      this.sna.setEnabled(false);
      this.link.setEnabled(false);
      this.clkdiv.setEnabled(false);
      break;
    case 7:
      this.deviceCombo.setEnabled(false);
      this.baudCombo.setEnabled(false);
      this.textFieldLabel.setText("Cable");
      this.textField.setEnabled(true);
      this.dna.setEnabled(false);
      this.sna.setEnabled(false);
      this.link.setEnabled(false);
      this.clkdiv.setEnabled(false);
    }
  }

  public int targetType()
  {
    return 1;
  }

  public void save(IDialogSettings root)
  {
    IDialogSettings s = root.addNewSection("szmon");
    int type = this.interfaceCombo.getSelectionIndex();
    s.put("interface", type);
    if (type == 0)
    {
      s.put("device", this.deviceCombo.getText().trim());
      s.put("baudrate", this.baudCombo.getText().trim());
    }
    else if (type == 3) {
      s.put("ip", this.textField.getText().trim());
    } else if (type == 5)
    {
      s.put("ip", this.textField.getText().trim());
      s.put("sna", this.sna.getText().trim());
      s.put("dna", this.dna.getText().trim());
      s.put("link", this.link.getText().trim());
      s.put("clkdiv", this.clkdiv.getSelection());
    }
    else if (type == 7) {
      s.put("cable", this.textField.getText().trim());
    } else if (type == 4) {
      s.put("pcidevice", this.textField.getText().trim());
    }
    s.put("nosram", this.nosram.getSelection());
    s.put("loopback", this.loopback.getSelection());
    s.put("extra", this.extra.getText().trim());
  }

  public void restore(IDialogSettings root)
  {
    IDialogSettings settings = root.getSection("szmon");
    if (settings == null)
      return;
    int type;
    try {
      type = settings.getInt("interface");
    }
    catch (NumberFormatException localNumberFormatException1) {
      type = 0;
    }
    this.interfaceCombo.select(type);

    if (type == 0)
    {
      String str = settings.get("device");
      if (str != null) {
        this.deviceCombo.setText(str);
      }
      str = settings.get("baudrate");
      if (str != null)
        this.baudCombo.setText(str);
    }
    else if (type == 3)
    {
      String str = settings.get("ip");
      if (str != null)
        this.textField.setText(str);
    }
    else if (type == 5)
    {
      String str = settings.get("ip");
      if (str != null)
        this.textField.setText(str);
      str = settings.get("sna");
      if (str != null)
        this.sna.setText(str);
      str = settings.get("dna");
      if (str != null)
        this.dna.setText(str);
      str = settings.get("link");
      if (str != null)
        this.link.setText(str);
      str = settings.get("clkdiv");
      if (str != null)
      {
        try
        {
          this.clkdiv.setSelection(Integer.parseInt(str));
        }
        catch (NumberFormatException localNumberFormatException2) {
          this.clkdiv.setSelection(1);
        }
      }
    }
    else if (type == 7)
    {
      String str = settings.get("cable");
      if (str != null)
        this.textField.setText(str);
    }
    else if (type == 4)
    {
      String str = settings.get("pcidevice");
      if (str != null) {
        this.textField.setText(str);
      }
    }
    boolean selected = settings.getBoolean("nosram");
    this.nosram.setSelection(selected);

    selected = settings.getBoolean("loopback");
    this.loopback.setSelection(selected);

    String str = settings.get("extra");
    if (str != null) {
      this.extra.setText(str);
    }
    updateInterface();
  }

  public void restore()
  {
  }

  public void setDefaults()
  {
    this.interfaceCombo.select(0);
    this.deviceCombo.setText("");
    this.baudCombo.setText("");
    this.nosram.setSelection(false);
    this.loopback.setSelection(false);
    this.extra.setText("");
    this.textField.setText("");
    this.sna.setText("");
    this.dna.setText("");
    this.link.setText("");
    this.clkdiv.setSelection(1);
    updateInterface();
  }

  public String getCommandLineOptions()
  {
    StringBuffer cmd = new StringBuffer();

    switch (this.interfaceCombo.getSelectionIndex())
    {
    case 0:
      String str = this.deviceCombo.getText().trim();
      if (str.length() > 0)
      {
        cmd.append(" -uart ");
        cmd.append(str);
      }
      str = this.baudCombo.getText().trim();
      if (str.length() > 0)
      {
        cmd.append(" -baud ");
        cmd.append(str);
      }
      break;
    case 1:
      cmd.append(" -usb");
      break;
    case 2:
      cmd.append(" -jtag");
      break;
    case 3:
      cmd.append(" -eth");
      str = this.textField.getText().trim();
      if (str.length() > 0)
      {
        cmd.append(" -ip ");
        cmd.append(str);
      }
      break;
    case 4:
      str = this.textField.getText().trim();
      if (str.length() > 0)
      {
        cmd.append(" -pci ");
        cmd.append(str);
      }
      break;
    case 6:
      cmd.append(" -grusb");
      break;
    case 5:
      cmd.append(" -gresb");
      str = this.textField.getText().trim();
      if (str.length() > 0)
      {
        cmd.append(" -ip ");
        cmd.append(str);
      }
      str = this.sna.getText().trim();
      if (str.length() > 0)
      {
        cmd.append(" -sna ");
        cmd.append(str);
      }
      str = this.dna.getText().trim();
      if (str.length() > 0)
      {
        cmd.append(" -dna ");
        cmd.append(str);
      }
      str = this.link.getText().trim();
      if (str.length() > 0)
      {
        cmd.append(" -link ");
        cmd.append(str);
      }
      int i = this.clkdiv.getSelection();
      if (i > 1)
      {
        cmd.append(" -clkdiv ");
        cmd.append(i);
      }
      break;
    case 7:
      cmd.append(" -altjtag");
      str = this.textField.getText().trim();
      if (str.length() > 0)
      {
        cmd.append(" -altcable ");
        cmd.append(str);
      }
      break;
    default:
      return "";
    }
    if (this.nosram.getSelection())
      cmd.append(" -nosram");
    if (this.loopback.getSelection())
      cmd.append(" -u");
    String str = this.extra.getText();
    if (str.length() > 0)
    {
      cmd.append(" ");
      cmd.append(str.trim());
    }
    return cmd.toString().trim();
  }

  public String parseCommandLine(String arguments)
  {
    IDialogSettings root = new DialogSettings("root");
    root.addSection(parseArguments(arguments));
    restore(root);
    return "";
  }

  public static IDialogSettings parseArguments(String arguments)
  {
    IDialogSettings s = new DialogSettings("szmon");
    String e = null;
    String[] args = arguments.split(" ");
    for (int i = 0; i < args.length; ++i)
    {
      if (args[i].equals("-uart"))
      {
        s.put("interface", 0);
        if ((i + 1 < args.length) && (!args[(i + 1)].startsWith("-")))
          s.put("device", args[(++i)]);
      }
      else if (args[i].equals("-usb"))
      {
        s.put("interface", 1);
      }
      else if (args[i].equals("-jtag"))
      {
        s.put("interface", 2);
      }
      else if (args[i].equals("-eth"))
      {
        s.put("interface", 3);
      }
      else if (args[i].equals("-gresb"))
      {
        s.put("interface", 5);
      }
      else if (args[i].equals("-grusb"))
      {
        s.put("interface", 6);
      }
      else if (args[i].equals("-pci"))
      {
        s.put("interface", 4);
        if ((i + 1 < args.length) && (!args[(i + 1)].startsWith("-")))
          s.put("pcidevice", args[(++i)]);
      }
      else if (args[i].equals("-altjtag"))
      {
        s.put("interface", 7);
      }
      else if (args[i].equals("-baud"))
      {
        if ((i + 1 < args.length) && (!args[(i + 1)].startsWith("-")))
          s.put("baudrate", args[(++i)]);
      }
      else if (args[i].equals("-ip"))
      {
        if ((i + 1 < args.length) && (!args[(i + 1)].startsWith("-")))
          s.put("ip", args[(++i)]);
      }
      else if (args[i].equals("-altcable"))
      {
        if ((i + 1 < args.length) && (!args[(i + 1)].startsWith("-")))
          s.put("cable", args[(++i)]);
      }
      else if (args[i].equals("-sna"))
      {
        if ((i + 1 < args.length) && (!args[(i + 1)].startsWith("-")))
          s.put("sna", args[(++i)]);
      }
      else if (args[i].equals("-dna"))
      {
        if ((i + 1 < args.length) && (!args[(i + 1)].startsWith("-")))
          s.put("dna", args[(++i)]);
      }
      else if (args[i].equals("-link"))
      {
        if ((i + 1 < args.length) && (!args[(i + 1)].startsWith("-")))
          s.put("link", args[(++i)]);
      }
      else if (args[i].equals("-clkdiv"))
      {
        if ((i + 1 < args.length) && (!args[(i + 1)].startsWith("-")))
          s.put("clkdiv", args[(++i)]);
      }
      else if (args[i].equals("-nosram"))
      {
        s.put("nosram", true);
      }
      else if (args[i].equals("-u"))
      {
        s.put("loopback", true);
      }
      else if ((e != null) && (e.length() > 0)) {
        e = e + " " + args[i];
      } else {
        e = args[i];
      }
    }
    s.put("extra", e);
    return s;
  }

  private void createModuleOptions()
  {
    GridData gridData6 = new GridData();
    gridData6.horizontalAlignment = 4;
    gridData6.grabExcessHorizontalSpace = true;
    gridData6.horizontalSpan = 3;
    gridData6.verticalAlignment = 2;
    this.moduleOptions = new ModuleOptions(this.group2, 0);
    this.moduleOptions.setText("User command module");
    this.moduleOptions.setLayoutData(gridData6);
    this.moduleOptions.addModifyListener(this.listener);
  }

  private void createGroup1()
  {
    GridData gridData13 = new GridData();
    gridData13.grabExcessHorizontalSpace = true;
    gridData13.verticalAlignment = 2;
    gridData13.horizontalAlignment = 4;
    GridData gridData8 = new GridData();
    gridData8.horizontalAlignment = 4;
    gridData8.grabExcessHorizontalSpace = true;
    gridData8.grabExcessVerticalSpace = true;
    gridData8.verticalAlignment = 4;
    GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = 2;
    gridLayout.marginWidth = 5;
    gridLayout.marginHeight = 5;
    gridLayout.verticalSpacing = 5;
    gridLayout.horizontalSpacing = 5;
    this.group1 = new Group(this.composite, 0);
    this.group1.setText("Connection");
    this.group1.setLayoutData(gridData8);
    this.group1.setLayout(gridLayout);
    this.label4 = new Label(this.group1, 0);
    this.label4.setText("Interface");
    createInterfaceCombo();
    this.label5 = new Label(this.group1, 0);
    this.label5.setText("Device");
    createDeviceCombo();
    this.label = new Label(this.group1, 0);
    this.label.setText("Baudrate");
    createBaudCombo();
    GridData gridData2 = new GridData();
    gridData2.horizontalAlignment = 4;
    gridData2.grabExcessHorizontalSpace = true;
    gridData2.verticalAlignment = 2;
    this.textFieldLabel = new Label(this.group1, 0);
    this.textFieldLabel.setText("Target IP");
    this.textField = new Text(this.group1, 2048);
    this.textField.setLayoutData(gridData13);
    createGroup3();
    createComposite1();
    this.textField.setLayoutData(gridData2);
    this.textField.addModifyListener(this.listener);
  }

  private void createGroup2()
  {
    GridData gridData17 = new GridData();
    gridData17.horizontalSpan = 3;
    GridData gridData7 = new GridData();
    gridData7.horizontalAlignment = 4;
    gridData7.grabExcessHorizontalSpace = true;
    gridData7.grabExcessVerticalSpace = true;
    gridData7.verticalAlignment = 4;
    GridLayout gridLayout1 = new GridLayout();
    gridLayout1.numColumns = 3;
    this.group2 = new Group(this.composite, 0);
    this.group2.setText("Options");
    createModuleOptions();
    this.group2.setLayoutData(gridData7);
    this.group2.setLayout(gridLayout1);
    GridData gridData11 = new GridData();
    gridData11.horizontalAlignment = 4;
    gridData11.grabExcessHorizontalSpace = true;
    gridData11.horizontalSpan = 3;
    gridData11.verticalAlignment = 2;
    this.nosram = new Button(this.group2, 32);
    this.nosram.setText("No SRAM");
    this.nosram.setLayoutData(gridData11);
    this.nosram.addSelectionListener(this.listener);
    this.loopback = new Button(this.group2, 32);
    this.loopback.setText("UART Loopback");
    this.loopback.setLayoutData(gridData17);
    this.loopback.addSelectionListener(this.listener);
  }

  private void createComposite()
  {
    GridLayout gridLayout2 = new GridLayout();
    gridLayout2.numColumns = 2;
    gridLayout2.verticalSpacing = 5;
    gridLayout2.marginWidth = 0;
    gridLayout2.marginHeight = 0;
    gridLayout2.horizontalSpacing = 5;
    GridData gridData = new GridData();
    gridData.horizontalSpan = 2;
    gridData.verticalAlignment = 4;
    gridData.grabExcessHorizontalSpace = true;
    gridData.grabExcessVerticalSpace = true;
    gridData.horizontalAlignment = 4;
    this.composite = new Composite(this.group, 0);
    createGroup1();
    this.composite.setLayout(gridLayout2);
    createGroup2();
    this.composite.setLayoutData(gridData);
  }

  private void createGroup3()
  {
    GridData gridData12 = new GridData();
    gridData12.horizontalAlignment = 4;
    gridData12.grabExcessHorizontalSpace = true;
    gridData12.verticalAlignment = 2;
    GridData gridData10 = new GridData();
    gridData10.horizontalAlignment = 4;
    gridData10.grabExcessHorizontalSpace = true;
    gridData10.verticalAlignment = 2;
    GridLayout gridLayout3 = new GridLayout();
    gridLayout3.numColumns = 4;
    GridData gridData9 = new GridData();
    gridData9.horizontalSpan = 2;
    gridData9.verticalAlignment = 2;
    gridData9.grabExcessHorizontalSpace = true;
    gridData9.grabExcessVerticalSpace = false;
    gridData9.horizontalAlignment = 4;
    this.group3 = new Group(this.group1, 0);
    this.group3.setText("Node address");
    this.group3.setLayout(gridLayout3);
    this.group3.setLayoutData(gridData9);
    this.label2 = new Label(this.group3, 0);
    this.label2.setText("Source");
    this.sna = new Text(this.group3, 2048);
    this.sna.setLayoutData(gridData10);
    this.label1 = new Label(this.group3, 0);
    this.label1.setText("Dest.");
    this.dna = new Text(this.group3, 2048);
    this.dna.setLayoutData(gridData12);
  }

  private void createComposite1()
  {
    GridLayout gridLayout4 = new GridLayout();
    gridLayout4.numColumns = 4;
    GridData gridData16 = new GridData();
    gridData16.horizontalSpan = 2;
    gridData16.verticalAlignment = 4;
    gridData16.horizontalAlignment = 4;
    GridData gridData15 = new GridData();
    gridData15.grabExcessHorizontalSpace = false;
    gridData15.verticalAlignment = 2;
    gridData15.horizontalAlignment = 2;
    GridData gridData14 = new GridData();
    gridData14.grabExcessHorizontalSpace = true;
    gridData14.verticalAlignment = 2;
    gridData14.horizontalAlignment = 4;
    this.composite1 = new Composite(this.group1, 0);
    this.composite1.setLayoutData(gridData16);
    this.composite1.setLayout(gridLayout4);
    this.label6 = new Label(this.composite1, 0);
    this.label6.setText("Virtual link");
    this.link = new Text(this.composite1, 2048);
    this.link.setLayoutData(gridData14);
    this.label7 = new Label(this.composite1, 0);
    this.label7.setText("Clock div.");
    this.clkdiv = new Spinner(this.composite1, 2048);
    this.clkdiv.setMaximum(255);
    this.clkdiv.setMinimum(1);
    this.clkdiv.setLayoutData(gridData15);
  }
}
