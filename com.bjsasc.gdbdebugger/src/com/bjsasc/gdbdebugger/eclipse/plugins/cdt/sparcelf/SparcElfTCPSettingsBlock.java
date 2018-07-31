package com.bjsasc.gdbdebugger.eclipse.plugins.cdt.sparcelf;

import java.util.Observable;
import org.eclipse.cdt.debug.mi.internal.ui.dialogfields.DialogField;
import org.eclipse.cdt.debug.mi.internal.ui.dialogfields.IDialogFieldListener;
import org.eclipse.cdt.debug.mi.internal.ui.dialogfields.LayoutUtil;
import org.eclipse.cdt.debug.mi.internal.ui.dialogfields.StringDialogField;
import org.eclipse.cdt.utils.ui.controls.ControlFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SparcElfTCPSettingsBlock extends Observable {
	private static final String DEFAULT_HOST_NAME = "";
	private static final String DEFAULT_PORT_NUMBER = "";
	private Shell fShell;
	private StringDialogField fHostNameField;
	private StringDialogField fPortNumberField;
	private Control fControl;
	private String fErrorMessage = null;

	public SparcElfTCPSettingsBlock() {
		this.fHostNameField = createHostNameField();
		this.fPortNumberField = createPortNumberField();
	}

	public void createBlock(Composite parent) {
		this.fShell = parent.getShell();
		Composite comp = ControlFactory.createCompositeEx(parent, 2, 1808);
		((GridLayout) comp.getLayout()).makeColumnsEqualWidth = false;
		((GridLayout) comp.getLayout()).marginHeight = 0;
		((GridLayout) comp.getLayout()).marginWidth = 0;
		comp.setFont(JFaceResources.getDialogFont());

		PixelConverter converter = new PixelConverter(comp);

		this.fHostNameField.doFillIntoGrid(comp, 2);
		LayoutUtil.setWidthHint(this.fHostNameField.getTextControl(null),
				converter.convertWidthInCharsToPixels(20));
		this.fPortNumberField.doFillIntoGrid(comp, 2);
		((GridData) this.fPortNumberField.getTextControl(null).getLayoutData()).horizontalAlignment = 1;
		LayoutUtil.setWidthHint(this.fPortNumberField.getTextControl(null),
				converter.convertWidthInCharsToPixels(10));

		setControl(comp);
	}

	protected Shell getShell() {
		return this.fShell;
	}

	public void dispose() {
		deleteObservers();
	}

	public void initializeFrom(ILaunchConfiguration configuration) {
		initializeHostName(configuration);
		initializePortNumber(configuration);
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(SparcElfConstants.ATTR_TSIM_HOST, "");
		configuration.setAttribute(SparcElfConstants.ATTR_TSIM_PORT, "");
	}

	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		if (this.fHostNameField != null)
			configuration.setAttribute(SparcElfConstants.ATTR_TSIM_HOST,
					this.fHostNameField.getText().trim());
		if (this.fPortNumberField != null)
			configuration.setAttribute(SparcElfConstants.ATTR_TSIM_PORT,
					this.fPortNumberField.getText().trim());
	}

	private StringDialogField createHostNameField() {
		StringDialogField field = new StringDialogField();
		field.setLabelText(SparcElfMessage.getString("TCPSettingsBlock.0"));
//		field.setLabelText("SZMON/tsim-leon3 hostname or IP address:");
		field.setDialogFieldListener(new IDialogFieldListener() {
			public void dialogFieldChanged(DialogField f) {
				hostNameFieldChanged();
			}
		});

		return field;
	}

	private StringDialogField createPortNumberField() {
		StringDialogField field = new StringDialogField();
		field.setLabelText(SparcElfMessage.getString("TCPSettingsBlock.1"));
//		field.setLabelText("SZMON/tsim-leon3 port number:");
		field.setDialogFieldListener(new IDialogFieldListener() {
			public void dialogFieldChanged(DialogField f) {
				portNumberFieldChanged();
			}
		});

		return field;
	}

	protected void hostNameFieldChanged() {
		updateErrorMessage();
		setChanged();
		notifyObservers();
	}

	protected void portNumberFieldChanged() {
		updateErrorMessage();
		setChanged();
		notifyObservers();
	}

	private void initializeHostName(ILaunchConfiguration configuration) {
		if (this.fHostNameField == null)
			return;
		try {
			this.fHostNameField.setText(configuration.getAttribute(
					SparcElfConstants.ATTR_TSIM_HOST, ""));
		} catch (CoreException localCoreException) {
		}
	}

	private void initializePortNumber(ILaunchConfiguration configuration) {
		if (this.fPortNumberField == null)
			return;
		try {
			this.fPortNumberField.setText(configuration.getAttribute(
					SparcElfConstants.ATTR_TSIM_PORT, ""));
		} catch (CoreException localCoreException) {
		}
	}

	public Control getControl() {
		return this.fControl;
	}

	protected void setControl(Control control) {
		this.fControl = control;
	}

	public boolean isValid(ILaunchConfiguration configuration) {
		updateErrorMessage();
		return true;
	}

	private void updateErrorMessage() {
		setErrorMessage(null);
		if ((this.fHostNameField == null) || (this.fPortNumberField == null))
			return;
		if (this.fHostNameField.getText().trim().length() == 0)
			setErrorMessage(SparcElfMessage.getString("TCPSettingsBlock.2"));
		else if (!hostNameIsValid(this.fHostNameField.getText().trim()))
			setErrorMessage(SparcElfMessage.getString("TCPSettingsBlock.3"));
		else if (this.fPortNumberField.getText().trim().length() == 0)
			setErrorMessage(SparcElfMessage.getString("TCPSettingsBlock.4"));
		else if (!portNumberIsValid(this.fPortNumberField.getText().trim()))
			setErrorMessage(SparcElfMessage.getString("TCPSettingsBlock.5"));
	}

	public String getErrorMessage() {
		return this.fErrorMessage;
	}

	private void setErrorMessage(String string) {
		this.fErrorMessage = string;
	}

	private boolean hostNameIsValid(String hostName) {
		return true;
	}

	private boolean portNumberIsValid(String portNumber) {
		try {
			int port = Short.parseShort(portNumber);
			if (port < 0)
				return false;
		} catch (NumberFormatException localNumberFormatException) {
			return false;
		}
		return true;
	}
}
