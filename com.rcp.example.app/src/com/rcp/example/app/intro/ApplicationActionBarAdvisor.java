package com.rcp.example.app.intro;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    private IWorkbenchAction introAction;
    
	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(IWorkbenchWindow window) {
		introAction = ActionFactory.INTRO.create(window);
		register(introAction);
	}

	protected void fillMenuBar(IMenuManager menuBar) {
		
		MenuManager helpMenu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);
		menuBar.add(helpMenu);

		// Help
		helpMenu.add(introAction);
	}
	
	
	protected void fillCoolBar(ICoolBarManager coolbar) {
		  ToolBarManager toolBarManager=new ToolBarManager();
		  toolBarManager.add(introAction);
		  coolbar.add(toolBarManager);
		 
	}

}
