package com.bjsasc.utils.mvc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Model {
	private List<IView> fViews = new ArrayList<IView>();

	public void addView(IView view) {
		if (!fViews.contains(view)) {
			fViews.add(view);
		}
	}

	public void removeView(IView view) {
		fViews.remove(view);
	}

	/**
	 * 通过使用反射的方法逐一调用视图类中的一个指定方法，当前的实现限制被调函数的返回值为void类型。
	 * 
	 * @param methodName
	 *           视图类的方法名称
	 * @param args
	 *           变长参数，由于Java变长参数的实现机制，参数类型不能为数组类型
	 */
	protected void callViewsMethod(String methodName, Object... args) {
		for (IView view : fViews) {
			Method method = null;
			Class<?>[] types = null;
			if (args != null) {
				types = new Class<?>[args.length];

				for (int i = 0; i < types.length; i++) {
					types[i] = args[i].getClass();
				}
			}

			try {
				if (types == null) {
					method = view.getClass().getDeclaredMethod(methodName);
				} else {
					method = view.getClass().getDeclaredMethod(methodName, types);
				}

				method.invoke(view, args);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void removeAllViews() {
		fViews.removeAll(fViews);
	}
	
}
