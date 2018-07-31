/*******************************************************************************
 * Copyright (c) 2004, 2008 QNX Software Systems and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * QNX Software Systems - Initial API and implementation
 * Pawel Piech (WindRiver) - https://bugs.eclipse.org/bugs/show_bug.cgi?id=228063
 *******************************************************************************/
package com.bjsasc.debug.core;

import org.eclipse.cdt.debug.core.model.ISteppingModeTarget;
import org.eclipse.cdt.debug.core.model.ITargetProperties;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.actions.ActionDelegate;

import com.bjsasc.cdt.debug.core.model.SzDebugPlugin;

/**
 * Turns instruction step mode on/off for selected target.
 */
public class ToggleSymFiltersActionDelegate extends ActionDelegate implements IViewActionDelegate, IPropertyChangeListener {
/*
	private ISteppingModeTarget fTarget = null;*/
	
	private IAction fAction = null;

	/*private boolean fInitialized = !com.bjsasc.cdt.debug.core.model.DebugPlugin.isUseSYMFilters();*/
	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.Preferences.IPropertyChangeListener#propertyChange(org.eclipse.core.runtime.Preferences.PropertyChangeEvent)
	 */
	public ToggleSymFiltersActionDelegate() {
	
	}
	
	public void propertyChange( PropertyChangeEvent event ) {
		IAction action = getAction();
		if (event.getProperty().equals(SYMFilterManager.PREF_USE_SYM_FILTERS)) {
			boolean checked = SzDebugPlugin.isUseSYMFilters();
			action.setChecked(checked);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IViewActionDelegate#init(org.eclipse.ui.IViewPart)
	 */
	public void init( IViewPart view ) {
		SzDebugPlugin.setUseSYMFilters(false);
		initState();
		getAction().setChecked(false);
	}
	
	 protected void initState() {
	    	SzDebugPlugin.getDefault().getPluginPreferences().addPropertyChangeListener(this);
	 }

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate2#dispose()
	 */
	@Override
	public void dispose() {
		SzDebugPlugin.getDefault().getPluginPreferences().removePropertyChangeListener(this);
		
       /* ISteppingModeTarget target = getTarget();
        if ( target != null && target instanceof ITargetProperties ) {
            ((ITargetProperties)target).removePropertyChangeListener( this );
        }
        setTarget( null );*/
        setAction( null );
        
    }

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate2#init(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void init( IAction action ) {
		setAction( action );
		SzDebugPlugin.setUseSYMFilters(false);
		action.setChecked( false );
		action.setEnabled( false );
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run( IAction action ) {
		
        
        /*if (fInitialized) {*/
    		System.out.print(SzDebugPlugin.isUseSYMFilters()+" --> ");
    		SzDebugPlugin.setUseSYMFilters(!SzDebugPlugin.isUseSYMFilters());
    		getAction().setChecked(SzDebugPlugin.isUseSYMFilters());
    		System.out.println(SzDebugPlugin.isUseSYMFilters());
    	/*} else {
    		fInitialized = true;
    	}*/
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate2#runWithEvent(org.eclipse.jface.action.IAction, org.eclipse.swt.widgets.Event)
	 */
	@Override
	public void runWithEvent( IAction action, Event event ) {
		run( action );
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged( IAction action, ISelection selection ) {

	}

/*	private ISteppingModeTarget getTarget() {
		return this.fTarget;
	}

	private void setTarget( ISteppingModeTarget target ) {
		this.fTarget = target;
	}*/

	private IAction getAction() {
		return this.fAction;
	}

	private void setAction( IAction action ) {
		this.fAction = action;
	}
}
