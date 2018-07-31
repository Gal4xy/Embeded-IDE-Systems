package com.bjsasc.cdt.debug.core.model;

import com.bjsasc.debug.core.SYMFilterManager;

/**
 * @author  Administrator
 */
public class SzDebugPlugin extends org.eclipse.debug.core.DebugPlugin {
	public static final String IMG_DLCL_TOGGLE_SYM_FILTERS = "IMG_DLCL_TOGGLE_SYM_FILTERS"; //$NON-NLS-1$
	public static final String IMG_ELCL_TOGGLE_SYM_FILTERS = "IMG_ELCL_TOGGLE_SYM_FILTERS"; //$NON-NLS-1$
	/**
	 * @uml.property  name="fSYMFilterManager"
	 * @uml.associationEnd  
	 */
	private static SYMFilterManager fSYMFilterManager =null;
	public static boolean isUseSYMFilters() {
		return getSYMFilterManager().isUseSYMFilters();
	}	

	public static void setUseSYMFilters(boolean useSYMFilters) {
		getSYMFilterManager().setUseSYMFilters(useSYMFilters);
	}
	
	public static synchronized SYMFilterManager getSYMFilterManager() {
		if (fSYMFilterManager == null) {
			fSYMFilterManager = new SYMFilterManager();
		}
		return fSYMFilterManager;
	}    
}
