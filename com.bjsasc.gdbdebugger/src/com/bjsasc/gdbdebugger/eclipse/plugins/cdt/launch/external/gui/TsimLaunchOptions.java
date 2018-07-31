package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.external.gui;

import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class TsimLaunchOptions extends LaunchOptions
{
  private CacheOptions dataCacheOptions = null;
  private CacheOptions instructionCacheOptions = null;
  private Label label = null;
  private Text extra = null;
  private Composite composite = null;
  private Group group = null;
  private ModuleOptions ahbmodule = null;
  private ModuleOptions iomodule = null;

  public TsimLaunchOptions(Composite parent, int style)
  {
    super(parent, style);
    initialize();
  }

  public void dispose()
  {
    this.instructionCacheOptions.removeUpdateListener(this.listener);
    this.dataCacheOptions.removeUpdateListener(this.listener);
    this.extra.removeModifyListener(this.listener);
    this.ahbmodule.removeModifyListener(this.listener);
    this.iomodule.removeModifyListener(this.listener);
    super.dispose();
  }

  private void initialize()
  {
    setLayout(new FillLayout());
    GridLayout gridLayout = new GridLayout(2, false);
    this.group = new Group(this, 0);
    this.group.setText("TSIM startup options");
    this.group.setLayout(gridLayout);
    GridData gridData92 = new GridData();
    gridData92.horizontalAlignment = 1;
    gridData92.verticalAlignment = 2;
    GridData gridData = new GridData();
    gridData.grabExcessHorizontalSpace = true;
    gridData.verticalAlignment = 2;
    gridData.horizontalAlignment = 4;
    createComposite();
    this.label = new Label(this.group, 0);
    this.label.setText("Extra options");
    this.label.setLayoutData(gridData92);
    this.extra = new Text(this.group, 2048);
    this.extra.setLayoutData(gridData);
    this.extra.addModifyListener(this.listener);

    pack();
  }

  private void createDataCacheOptions()
  {
    GridData gridData = new GridData(4, 4, true, true);
    this.dataCacheOptions = new CacheOptions(this.composite, 0);
    this.dataCacheOptions.setText("Data cache");
    this.dataCacheOptions.setLayoutData(gridData);
    this.dataCacheOptions.addUpdateListener(this.listener);
  }

  private void createInstructionCacheOptions()
  {
    GridData gridData = new GridData(4, 4, true, true);
    this.instructionCacheOptions = new CacheOptions(this.composite, 0);
    this.instructionCacheOptions.setText("Instruction cache");
    this.instructionCacheOptions.setLayoutData(gridData);
    this.instructionCacheOptions.addUpdateListener(this.listener);
  }

  private void createComposite()
  {
    GridData gridData2 = new GridData();
    gridData2.verticalAlignment = 2;
    gridData2.grabExcessHorizontalSpace = true;
    gridData2.horizontalSpan = 2;
    gridData2.grabExcessVerticalSpace = false;
    gridData2.horizontalAlignment = 4;
    GridLayout gridLayout1 = new GridLayout();
    gridLayout1.numColumns = 2;
    gridLayout1.verticalSpacing = 5;
    gridLayout1.marginWidth = 0;
    gridLayout1.marginHeight = 0;
    gridLayout1.horizontalSpacing = 5;
    this.composite = new Composite(this.group, 0);
    this.composite.setLayout(gridLayout1);
    this.composite.setLayoutData(gridData2);
    createInstructionCacheOptions();
    createDataCacheOptions();
    createAhbmodule();
    createIomodule();
  }

  public int targetType()
  {
    return 0;
  }

  public void save(IDialogSettings root)
  {
    IDialogSettings s = root.addNewSection("tsim");

    IDialogSettings icache = s.addNewSection("icache");
    this.instructionCacheOptions.save(icache);
    IDialogSettings dcache = s.addNewSection("dcache");
    this.dataCacheOptions.save(dcache);

    String ahb = this.ahbmodule.getModulePath();
    if (ahb.length() > 0) {
      s.put("ahbmodule", ahb);
    }
    String iom = this.iomodule.getModulePath();
    if (iom.length() > 0) {
      s.put("iomodule", iom);
    }
    s.put("extra", this.extra.getText());
  }

  public void restore(IDialogSettings root)
  {
    IDialogSettings s = root.getSection("tsim");
    if (s == null) {
      return;
    }
    IDialogSettings icache = s.getSection("icache");
    if (icache != null)
      this.instructionCacheOptions.restore(icache);
    IDialogSettings dcache = s.getSection("dcache");
    if (dcache != null) {
      this.dataCacheOptions.restore(dcache);
    }
    String ahb = s.get("ahbmodule");
    if (ahb != null) {
      this.ahbmodule.setModulePath(ahb);
    }
    String iom = s.get("iomodule");
    if (iom != null) {
      this.iomodule.setModulePath(iom);
    }
    String str = s.get("extra");
    if (str != null)
      this.extra.setText(str);
  }

  public void setDefaults()
  {
    this.instructionCacheOptions.setDefaults();
    this.dataCacheOptions.setDefaults();
    this.ahbmodule.setModulePath("");
    this.iomodule.setModulePath("");
    this.extra.setText("");
  }

  public String getCommandLineOptions()
  {
    String ahb = this.ahbmodule.getModulePath();
    String iom = this.iomodule.getModulePath();
    String result = this.instructionCacheOptions.getCommandLineOptions("i") + " " + 
      this.dataCacheOptions.getCommandLineOptions("d") + (
      (ahb.length() > 0) ? " -ahbm " + ahb : "") + (
      (iom.length() > 0) ? " -iom " + iom : " ") + 
      this.extra.getText().trim();

    return result;
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
    IDialogSettings s = new DialogSettings("tsim");
    IDialogSettings icache = s.addNewSection("icache");
    arguments = CacheOptions.parseArguments(icache, arguments, "i");
    IDialogSettings dcache = s.addNewSection("dcache");
    arguments = CacheOptions.parseArguments(dcache, arguments, "d");

    String[] args = arguments.split(" ");
    String extra = "";
    for (int i = 0; i < args.length; ++i)
    {
      if (args[i].equals("-ahbm"))
      {
        ++i;
        s.put("ahbmodule", args[i]);
      }
      else if (args[i].equals("-iom"))
      {
        ++i;
        s.put("iomodule", args[i]);
      }
      else
      {
        if (extra.length() > 0)
          extra = extra + " ";
        extra = extra + args[i];
      }
    }

    s.put("extra", extra);
    return s;
  }

  private void createAhbmodule()
  {
    GridData gridData1 = new GridData();
    gridData1.horizontalAlignment = 4;
    gridData1.grabExcessHorizontalSpace = true;
    gridData1.verticalAlignment = 2;
    this.ahbmodule = new ModuleOptions(this.composite, 0);
    this.ahbmodule.setText("AHB Module");
    this.ahbmodule.setLayoutData(gridData1);
    this.ahbmodule.addModifyListener(this.listener);
  }

  private void createIomodule()
  {
    GridData gridData3 = new GridData();
    gridData3.horizontalAlignment = 4;
    gridData3.grabExcessHorizontalSpace = true;
    gridData3.verticalAlignment = 2;
    this.iomodule = new ModuleOptions(this.composite, 0);
    this.iomodule.setText("I/O Module");
    this.iomodule.setLayoutData(gridData3);
    this.iomodule.addModifyListener(this.listener);
  }
}
