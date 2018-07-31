package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.sparcelf;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import org.eclipse.cdt.debug.mi.internal.ui.MIUIMessages;
import org.eclipse.cdt.debug.mi.internal.ui.dialogfields.DialogField;
import org.eclipse.cdt.debug.mi.internal.ui.dialogfields.IListAdapter;
import org.eclipse.cdt.debug.mi.ui.IMILaunchConfigurationComponent;
import org.eclipse.cdt.debug.mi.ui.MIUIUtils;
import org.eclipse.cdt.utils.ui.controls.ControlFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class SparcElfGDBDebuggerPage extends AbstractLaunchConfigurationTab
		implements Observer {
	protected TabFolder fTabFolder;
	protected Text fGDBCommandText;
	protected Text fGDBInitText;
	private IMILaunchConfigurationComponent fSolibBlock;
	private SparcElfTCPSettingsBlock fTCPBlock;

	private Combo fTargetCombo;

	public SparcElfGDBDebuggerPage() {
		this.fTCPBlock = new SparcElfTCPSettingsBlock();
		this.fTCPBlock.addObserver(this);
	}

	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, 0);
		comp.setLayout(new GridLayout());
		comp.setLayoutData(new GridData(1808));
		this.fTabFolder = new TabFolder(comp, 0);
		this.fTabFolder.setLayoutData(new GridData(1808));
		createTabs(this.fTabFolder);
		this.fTabFolder.setSelection(0);
		setControl(parent);
	}

	public boolean isValid(ILaunchConfiguration launchConfig) {
		boolean valid = this.fGDBCommandText.getText().length() != 0;
		if (valid) {
			setErrorMessage(null);
			setMessage(null);
		} else {
			setErrorMessage(MIUIMessages.getString("GDBDebuggerPage.0"));
			setMessage(null);
		}
		return valid;
	}

	public void initializeFrom(ILaunchConfiguration configuration) {
		String gdbCommand = "sparc-elf-gdb";
		String debuggerID=SparcElfGDBDebugger.DEBUGGER_ID; 
		String gdbInit = "";
		try {
			
			debuggerID=configuration.getAttribute(
					"org.eclipse.cdt.launch.DEBUGGER_ID", SparcElfGDBDebugger.DEBUGGER_ID);
			gdbCommand = configuration.getAttribute(
					SparcElfConstants.ATTR_DEBUG_NAME, "sparc-elf-gdb");
			/*	if(debuggerID.equals(SparcElfGDBDebugger.DEBUGGER_ID))
				gdbCommand = SparcElfGDBDebugger.DEBUGGER_CMD;
			else if(debuggerID.equals(SparcElfGDBDebugger2.DEBUGGER_ID))
				gdbCommand = SparcElfGDBDebugger2.DEBUGGER_CMD;
			else if(debuggerID.equals(SparcElfGDBDebugger3.DEBUGGER_ID))
				gdbCommand = SparcElfGDBDebugger3.DEBUGGER_CMD;
			else if(debuggerID.equals(SparcElfGDBDebugger4.DEBUGGER_ID))
				gdbCommand = SparcElfGDBDebugger4.DEBUGGER_CMD;*/
			gdbInit = configuration.getAttribute(
					SparcElfConstants.ATTR_GDB_INIT, "");
		} catch (CoreException localCoreException) {
		}
		this.fTCPBlock.initializeFrom(configuration);
		if (this.fSolibBlock != null)
			this.fSolibBlock.initializeFrom(configuration);
		this.fGDBCommandText.setText(gdbCommand);
		this.fGDBInitText.setText(gdbInit);
	}

	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		String gdbStr = this.fGDBCommandText.getText();
		gdbStr = gdbStr.trim();//modified by lichengfei from gdbStr.trim(); to gdbStr = gdbStr.trim();
		configuration.setAttribute(SparcElfConstants.ATTR_DEBUG_NAME, gdbStr);
		gdbStr = this.fGDBInitText.getText();
		gdbStr = gdbStr.trim();//modified by lichengfei from gdbStr.trim(); to gdbStr = gdbStr.trim();
		configuration.setAttribute(SparcElfConstants.ATTR_GDB_INIT, gdbStr);
		this.fTCPBlock.performApply(configuration);
		if (this.fSolibBlock != null)
			this.fSolibBlock.performApply(configuration);
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(SparcElfConstants.ATTR_DEBUG_NAME,
				"sparc-elf-gdb");
		configuration.setAttribute(SparcElfConstants.ATTR_GDB_INIT, "");
		configuration.setAttribute(SparcElfConstants.ATTR_DEBUGGER_AUTO_SOLIB, true);
		configuration.setAttribute("org.eclipse.cdt.debug.mi.core.ATTR_LAUNCH_GDB_host", "localhost");
		configuration.setAttribute("org.eclipse.cdt.debug.mi.core.ATTR_LAUNCH_GDB_port", "2222");
		configuration.setAttribute("org.eclipse.cdt.debug.mi.core.STOP_ON_SOLIB_EVENTS", false);
		if (this.fSolibBlock != null)
			this.fSolibBlock.setDefaults(configuration);
		this.fTCPBlock.setDefaults(configuration);
	}

	public String getName() {
		return MIUIMessages.getString("GDBDebuggerPage.1");
	}

	protected Shell getShell() {
		return super.getShell();
	}

	protected void updateLaunchConfigurationDialog() {
		super.updateLaunchConfigurationDialog();
	}

	public void update(Observable o, Object arg) {
		updateLaunchConfigurationDialog();
	}

	public IMILaunchConfigurationComponent createSolibBlock(Composite parent) {
		IMILaunchConfigurationComponent block = null;
		try {
			block = MIUIUtils.createGDBSolibBlock(true, true);
		} catch (NoSuchMethodError error) {
			try {
				Class[] params = { Class
						.forName("org.eclipse.cdt.debug.mi.ui.IPathProvider") };

				IListAdapter args = new IListAdapter() {

					public void customButtonPressed(DialogField field, int index) {
						updateLaunchConfigurationDialog();
					}

					public void selectionChanged(DialogField field) {
						updateLaunchConfigurationDialog();
					}

				};
				IMILaunchConfigurationComponent searchPaths = MIUIUtils
						.createSolibSearchPathBlock(null, args);

				block = MIUIUtils.createGDBSolibBlock(searchPaths, true, true);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

		}

		if (block != null)
			block.createControl(parent);
		return block;
	}

	public void createTabs(TabFolder tabFolder) {
		createMainTab(tabFolder);
		createSolibTab(tabFolder);
	}

	public void createMainTab(TabFolder tabFolder) {
		TabItem tabItem = new TabItem(tabFolder, 0);
		tabItem.setText(MIUIMessages.getString("GDBDebuggerPage.2"));
		Composite comp = ControlFactory.createCompositeEx(this.fTabFolder, 1,
				1808);
		((GridLayout) comp.getLayout()).makeColumnsEqualWidth = false;
		tabItem.setControl(comp);
		Composite subComp = ControlFactory.createCompositeEx(comp, 3, 768);
		((GridLayout) subComp.getLayout()).makeColumnsEqualWidth = false;
		Label label = ControlFactory.createLabel(subComp, MIUIMessages
				.getString("GDBDebuggerPage.3"));
		GridData gd = new GridData();
		label.setLayoutData(gd);
		this.fGDBCommandText = ControlFactory.createTextField(subComp, 2052);
		this.fGDBCommandText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent evt) {
				updateLaunchConfigurationDialog();
			}
		});

		Button button = createPushButton(subComp, MIUIMessages
				.getString("GDBDebuggerPage.4"), null);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				handleGDBButtonSelected();
				updateLaunchConfigurationDialog();
			}

			private void handleGDBButtonSelected() {
				FileDialog dialog = new FileDialog(getShell(), 0);
				dialog.setText(MIUIMessages.getString("GDBDebuggerPage.5"));
				String gdbCommand = fGDBCommandText.getText().trim();
				int lastSeparatorIndex = gdbCommand.lastIndexOf(File.separator);
				if (lastSeparatorIndex != -1)
					dialog.setFilterPath(gdbCommand.substring(0,
							lastSeparatorIndex));
				String res = dialog.open();
				if (res == null) {
					return;
				}

				fGDBCommandText.setText(res);
			}
		});

		label = ControlFactory.createLabel(subComp, MIUIMessages
				.getString("GDBDebuggerPage.6"));
		gd = new GridData();
		label.setLayoutData(gd);
		this.fGDBInitText = ControlFactory.createTextField(subComp, 2052);
		gd = new GridData(768);
		this.fGDBInitText.setLayoutData(gd);
		this.fGDBInitText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent evt) {
				updateLaunchConfigurationDialog();
			}
		});

		button = createPushButton(subComp, MIUIMessages
				.getString("GDBDebuggerPage.7"), null);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				handleGDBInitButtonSelected();
				updateLaunchConfigurationDialog();
			}

			private void handleGDBInitButtonSelected() {
				FileDialog dialog = new FileDialog(getShell(), 0);
				dialog.setText(MIUIMessages.getString("GDBDebuggerPage.8"));
				String gdbCommand = fGDBInitText.getText().trim();
				int lastSeparatorIndex = gdbCommand.lastIndexOf(File.separator);
				if (lastSeparatorIndex != -1)
					dialog.setFilterPath(gdbCommand.substring(0,
							lastSeparatorIndex));
				String res = dialog.open();
				if (res == null) {
					return;
				}

				fGDBInitText.setText(res);
			}
		});

		label = ControlFactory.createLabel(comp, SparcElfMessage
				.getString("GDBDebuggerPage.9"), 200, -1, 64);
		// label = ControlFactory.createLabel(comp,
		// MIUIMessages.getString("GDBDebuggerPage.9"), 200, -1, 64);
		gd = new GridData(768);
		gd.horizontalSpan = 1;
		gd.widthHint = 200;
		label.setLayoutData(gd);

		gd = new GridData(768);
		gd.horizontalSpan = 1;
		gd.widthHint = 200;
		label.setLayoutData(gd);
		this.fTCPBlock.createBlock(comp);
	}

	public void createSolibTab(TabFolder tabFolder) {
		TabItem tabItem = new TabItem(tabFolder, 0);
		tabItem.setText(MIUIMessages.getString("GDBDebuggerPage.10"));
		Composite comp = ControlFactory.createCompositeEx(this.fTabFolder, 1,
				1808);
		tabItem.setControl(comp);
		this.fSolibBlock = createSolibBlock(comp);
		if (this.fSolibBlock instanceof Observable)
			((Observable) this.fSolibBlock).addObserver(this);
	}

	public void dispose() {
		if (this.fSolibBlock != null) {
			if (this.fSolibBlock instanceof Observable)
				((Observable) this.fSolibBlock).deleteObserver(this);
			this.fSolibBlock.dispose();
		}
		super.dispose();
	}
}
