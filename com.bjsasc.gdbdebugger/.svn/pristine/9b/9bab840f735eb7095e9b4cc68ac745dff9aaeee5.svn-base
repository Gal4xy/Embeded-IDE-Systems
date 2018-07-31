package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.launch.external.gui;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

public class CacheOptions extends LaunchOptions
{
  public static final int RND = 0;
  public static final int LRU = 1;
  public static final int LRR = 2;
  private static final int SETS_DEFAULT_INDEX = 0;
  private static final int SETSIZE_DEFAULT_INDEX = 2;
  private static final int LINESIZE_DEFAULT_INDEX = 1;
  private static final int REPLACEMENT_DEFAULT_INDEX = 0;
  private Combo setsizeCombo = null;
  private Button linelock = null;
  private Combo linesizeCombo = null;
  private Combo setsCombo = null;
  private Combo replacement = null;
  private Label label2;
  private Label label3;
  private Label label4;
  private Label label5;
  private Group group = null;

  public CacheOptions(Composite parent, int style)
  {
    super(parent, style);
    initialize();
    setDefaults();
  }

  public void dispose()
  {
    this.setsizeCombo.removeModifyListener(this.listener);
    this.linelock.removeSelectionListener(this.listener);
    this.linesizeCombo.removeModifyListener(this.listener);
    this.setsCombo.removeModifyListener(this.listener);
    this.replacement.removeModifyListener(this.listener);
    super.dispose();
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
    GridData gridData2 = new GridData();
    gridData2.horizontalAlignment = 4;
    gridData2.verticalAlignment = 2;
    GridData gridData = new GridData();
    gridData.horizontalAlignment = 4;
    gridData.verticalAlignment = 2;
    GridLayout gridLayout1 = new GridLayout();
    gridLayout1.numColumns = 2;
    setLayout(new FillLayout());
    this.group = new Group(this, 0);
    this.group.setLayout(gridLayout1);
    GridData gridData5 = new GridData();
    gridData5.horizontalAlignment = 4;
    gridData5.verticalAlignment = 2;
    GridData gridData4 = new GridData();
    gridData4.horizontalAlignment = 4;
    gridData4.verticalAlignment = 2;
    GridData gridData1 = new GridData();
    gridData1.horizontalSpan = 2;
    gridData1.verticalAlignment = 2;
    gridData1.horizontalAlignment = 4;
    this.label4 = new Label(this.group, 0);
    this.label4.setText("Sets");
    this.label4.setLayoutData(gridData);
    createSetsCombo();
    this.label2 = new Label(this.group, 0);
    this.label2.setText("Set size");
    this.label2.setLayoutData(gridData4);
    createSetsizeCombo();
    this.label3 = new Label(this.group, 0);
    this.label3.setText("Line size");
    this.label3.setLayoutData(gridData5);
    createLinesizeCombo();
    this.label5 = new Label(this.group, 0);
    this.label5.setText("Replacement");
    this.label5.setLayoutData(gridData2);
    createReplacement();
    this.linelock = new Button(this.group, 32);
    this.linelock.setText("Enable line locking");
    this.linelock.setLayoutData(gridData1);
    this.linelock.addSelectionListener(this.listener);
  }

  private void createSetsizeCombo()
  {
    GridData gridData6 = new GridData();
    gridData6.horizontalAlignment = 4;
    gridData6.grabExcessHorizontalSpace = true;
    gridData6.verticalAlignment = 2;
    this.setsizeCombo = new Combo(this.group, 8);
    this.setsizeCombo.setLayoutData(gridData6);
    this.setsizeCombo.add("1");
    this.setsizeCombo.add("2");
    this.setsizeCombo.add("4");
    this.setsizeCombo.add("8");
    this.setsizeCombo.add("16");
    this.setsizeCombo.add("32");
    this.setsizeCombo.add("64");
    this.setsizeCombo.addModifyListener(this.listener);
  }

  private void createLinesizeCombo()
  {
    GridData gridData7 = new GridData();
    gridData7.horizontalAlignment = 4;
    gridData7.grabExcessHorizontalSpace = true;
    gridData7.verticalAlignment = 2;
    this.linesizeCombo = new Combo(this.group, 8);
    this.linesizeCombo.setLayoutData(gridData7);
    this.linesizeCombo.add("8");
    this.linesizeCombo.add("16");
    this.linesizeCombo.add("32");
    this.linesizeCombo.addModifyListener(this.listener);
  }

  private void createSetsCombo()
  {
    GridData gridData3 = new GridData();
    gridData3.horizontalAlignment = 4;
    gridData3.grabExcessHorizontalSpace = true;
    gridData3.verticalAlignment = 2;
    this.setsCombo = new Combo(this.group, 8);
    this.setsCombo.setLayoutData(gridData3);
    this.setsCombo.add("1");
    this.setsCombo.add("2");
    this.setsCombo.add("3");
    this.setsCombo.add("4");
    this.setsCombo.addModifyListener(this.listener);
  }

  private void createReplacement()
  {
    GridData gridData8 = new GridData();
    gridData8.horizontalAlignment = 4;
    gridData8.grabExcessHorizontalSpace = true;
    gridData8.verticalAlignment = 2;
    this.replacement = new Combo(this.group, 8);
    this.replacement.setLayoutData(gridData8);
    this.replacement.add("Random", 0);
    this.replacement.add("Least Recently Used", 1);
    this.replacement.add("Least Recently Replaced", 2);
    this.replacement.addModifyListener(this.listener);
  }

  public void setDefaults()
  {
    this.setsCombo.select(0);
    this.setsizeCombo.select(2);
    this.linesizeCombo.select(1);
    this.replacement.select(0);
    this.linelock.setSelection(false);
  }

  public void restore(IDialogSettings settings)
  {
    String str = settings.get("sets");
    int i = (str == null) ? -1 : this.setsCombo.indexOf(str);
    if (i != -1)
      this.setsCombo.select(i);
    else {
      this.setsCombo.select(0);
    }
    str = settings.get("setSize");
    i = (str == null) ? -1 : this.setsizeCombo.indexOf(str);
    if (i != -1)
      this.setsizeCombo.select(i);
    else {
      this.setsizeCombo.select(2);
    }
    str = settings.get("lineSize");
    i = (str == null) ? -1 : this.linesizeCombo.indexOf(str);
    if (i != -1)
      this.linesizeCombo.select(i);
    else
      this.linesizeCombo.select(1);
    try
    {
      i = settings.getInt("replacement");
      this.replacement.select(i);
    }
    catch (NumberFormatException localNumberFormatException) {
      this.replacement.select(0);
    }

    this.linelock.setSelection(settings.getBoolean("linelock"));
  }

  public void save(IDialogSettings s)
  {
	  String[] temp = null;
    s.put("sets", (this.setsCombo.getSelectionIndex() == 0) ? null : this.setsCombo.getText());
    s.put("sets", (this.setsCombo.getSelectionIndex() == 0) ? null : this.setsCombo.getText());
    s.put("setSize", (this.setsizeCombo.getSelectionIndex() == 2) ? null : this.setsizeCombo.getText());
    s.put("lineSize", (this.linesizeCombo.getSelectionIndex() == 1) ? null : this.linesizeCombo.getText());

    if (this.replacement.getSelectionIndex() != 0)
      s.put("replacement", this.replacement.getSelectionIndex());
    else
      s.put("replacement", temp);
    if (this.linelock.getSelection())
      s.put("linelock", this.linelock.getSelection());
    else
      s.put("linelock", temp);
  }

  public String getCommandLineOptions()
  {
    return null;
  }

  public String getCommandLineOptions(String p)
  {
    StringBuffer o = new StringBuffer();
    String prefix = " -" + p;

    if (this.setsizeCombo.getSelectionIndex() != 2)
    {
      String str = this.setsizeCombo.getText().trim();
      if (str.length() > 0)
      {
        o.append(prefix);
        o.append("csize ");
        o.append(str);
      }
    }

    if (this.linelock.getSelection())
    {
      o.append(prefix);
      o.append("lock");
    }

    if (this.linesizeCombo.getSelectionIndex() != 1)
    {
      String str = this.linesizeCombo.getText().trim();
      if (str.length() > 0)
      {
        o.append(prefix);
        o.append("lsize ");
        o.append(str);
      }
    }

    if (this.setsCombo.getSelectionIndex() != 0)
    {
      String str = this.setsCombo.getText().trim();
      if (str.length() > 0)
      {
        o.append(prefix);
        o.append("sets ");
        o.append(str);
      }

    }

    int repl = this.replacement.getSelectionIndex();
    switch (repl)
    {
    case 0:
      break;
    case 1:
      o.append(prefix).append("repl ").append("lru");
      break;
    case 2:
      o.append(prefix).append("repl ").append("lrr");
    }

    return o.toString().trim();
  }

  public String parseCommandLine(String arguments)
  {
    return arguments;
  }

  public static String parseArguments(IDialogSettings s, String arguments, String prefix)
  {
    StringBuffer buf = new StringBuffer();
    prefix = "-" + prefix;
    String[] args = arguments.split(" ");
    for (int i = 0; i < args.length; ++i)
    {
      if (args[i].startsWith(prefix))
      {
        String arg = args[i].substring(2);
        if (arg.equals("lock"))
        {
          s.put("linelock", true);
        }
        else if ((i + 1 < args.length) && (!args[(i + 1)].startsWith("-")))
        {
          String p = args[(++i)];

          if (arg.equals("csize"))
            s.put("setSize", p);
          else if (arg.equals("lsize"))
            s.put("lineSize", p);
          else if (arg.equals("sets"))
            s.put("sets", p);
          else if (arg.equals("repl"))
          {
            if (p.equals("rnd"))
              s.put("replacement", 0);
            else if (p.equals("lru"))
              s.put("replacement", 1);
            else if (p.equals("lrr"))
              s.put("replacement", 2);
          }
          else
            buf.append(" ").append(args[i]);
        }
        else {
          buf.append(" ").append(args[i]);
        }
      } else {
        buf.append(" ").append(args[i]);
      }
    }
    return buf.toString().trim();
  }
}
