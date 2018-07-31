package com.bjsasc.utils;

public class ArrayUtils {
	public static Object[] concatenate(Object[] a1, Object[] a2) {
		//Begin:added by lichengfei at 2012-08-22 еп©у╪Л╡Б
		if(a1==null||a2==null)
			return null;
		//End:added by lichengfei at 2012-08-22
		int a1Len = a1.length;
		int a2Len = a2.length;
		Object[] res = new Object[a1Len + a2Len];
		System.arraycopy(a1, 0, res, 0, a1Len);
		System.arraycopy(a2, 0, res, a1Len, a2Len);
		return res;
	}
}
