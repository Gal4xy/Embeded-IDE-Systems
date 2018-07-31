package com.bjsasc.debug.core;


/**
 * @author  Administrator
 */
public class LaunchManager {
	public static boolean buildResult =true;  

	public static Object lock = new Object();
	
	/**
	 * @uml.property  name="firstBuild"
	 */
	private static boolean firstBuild =true;  
	/**
	 * @return
	 * @uml.property  name="firstBuild"
	 */
	public static boolean isFirstBuild() {
		return firstBuild;
	}

	/**
	 * @param firstBuild
	 * @uml.property  name="firstBuild"
	 */
	public static void setFirstBuild(boolean firstBuild) {
		LaunchManager.firstBuild = firstBuild;
	}
	
}
