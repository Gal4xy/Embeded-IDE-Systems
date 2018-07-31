package com.bjsasc.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class PrivateAccessor {

	public static Object getPrivateField(Class<?> className,Object o, String fieldName) {
		/* Check we have valid arguments */
		/* Go and find the private field... */
		Field fields[] = o.getClass().getDeclaredFields();
		if(className!=null)
			fields = className.getDeclaredFields();
		for (int i = 0; i < fields.length; ++i) {
			if (fieldName.equals(fields[i].getName())) {
				try {
					fields[i].setAccessible(true);
					return fields[i].get(o);
				} catch (IllegalAccessException ex) {
					System.err.println("IllegalAccessException accessing "
									+ fieldName);
				}
			}
		}

		System.err.println("Field '" + fieldName + "' not found");
		return null;
	}

	public static void setPrivateField(Class<?> className,Object o, String fieldName, Object value) {
		/* Check we have valid arguments */
		/* Go and find the private field... */
		
		Field fields[] = o.getClass().getDeclaredFields();
		if(className!=null)
			fields = className.getDeclaredFields();
		for (int i = 0; i < fields.length; ++i) {
			if (fieldName.equals(fields[i].getName())) {
				try {
					fields[i].setAccessible(true);
					fields[i].set(o, value);
					return;
				} catch (IllegalAccessException ex) {
					System.err.println("IllegalAccessException accessing "
									+ fieldName);
				}
			}
		}

		System.err.println("Field '" + fieldName + "' not found");
	}
	public static void setPrivateField(Class<?> className,Object o, String fieldName, Object value,Class<?> type) {
		/* Check we have valid arguments */
		/* Go and find the private field... */
		
		Field fields[] = o.getClass().getDeclaredFields();
		if(className!=null)
			fields = className.getDeclaredFields();
		for (int i = 0; i < fields.length; ++i) {
			if (fieldName.equals(fields[i].getName())) {
				try {
					fields[i].setAccessible(true);

					if(type == int.class ){
						int intValue =((Integer)value).intValue();
						fields[i].setInt(o, intValue);
					}else if(type ==char.class){
						fields[i].setChar(o, ((Character)value).charValue());
					}else if(type ==long.class){
						fields[i].setLong(o, ((Long)value).longValue());
					}else if(type ==byte.class){
						fields[i].setByte(o, ((Byte)value).byteValue());
					}else if(type ==double.class){
						fields[i].setDouble(o, ((Double)value).doubleValue());
					}else if(type ==float.class){
						fields[i].setFloat(o, ((Float)value).floatValue());
					}else if(type ==short.class){
						fields[i].setShort(o, ((Short)value).shortValue());
					}else
						fields[i].set(o, value);
					return;
				} catch (IllegalAccessException ex) {
					System.err.println("IllegalAccessException accessing "
									+ fieldName);
				}
			}
		}

		System.err.println("Field '" + fieldName + "' not found");
	}

	public static Object callPrivateMethod(Object o, String methodName, Class<?>[] types, Object... args) {
		Class<?> cls = o.getClass();
		if(o instanceof Class<?>)
			cls = (Class<?>) o;
		
		Method method = null;
		Object result = -1;
		try {
			method = cls.getDeclaredMethod(methodName, types);
			method.setAccessible(true);
			result = (Object) method.invoke(o, args);// cal.sub(1,2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Object callParentPrivateMethod(Object o, String methodName, Class<?>[] types, Object... args) {
		Class<?> cls = o.getClass().getSuperclass();
		if(o instanceof Class<?>)
			cls = (Class<?>) o;
		
		Method method = null;
		Object result = -1;
		try {
			method = cls.getDeclaredMethod(methodName, types);
			method.setAccessible(true);
			result = (Object) method.invoke(o, args);// cal.sub(1,2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static Object callPrivateStaticMethod( Class<?> cls,String methodName, Class<?>[] types, Object... args) {

		Method method = null;
		Object result = -1;
		try {
			method = cls.getDeclaredMethod(methodName, types);
			method.setAccessible(true);
			result = (Object) method.invoke(cls, args);// cal.sub(1,2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static Object callPrivateStaticMethod( Class<?> cls,String methodName,  Object... args) {

		Method method = null;
		Object result = -1;
		Class<?>[] types = new Class<?>[args.length];
		boolean intToObj = false;
		for (int i = 0; i < types.length; i++) {
			if(!intToObj) {
				if(Integer.class==args[i].getClass()) {
					types[i]=int.class;
				} else {
					types[i]=args[i].getClass();
				}
			}
		}
		try {
			method = cls.getDeclaredMethod(methodName, types);
			method.setAccessible(true);
			result = (Object) method.invoke(cls, args);// cal.sub(1,2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static Object callPrivateMethod(Class<?> className, Object o, String methodName, Class<?>[] types, Object... args) {
		Class<?> cls = o.getClass();
		if (className != null) {
			cls = className;
		}
		
		Method method = null;
		Object result = -1;
		try {
			method = cls.getDeclaredMethod(methodName, types);
			method.setAccessible(true);
			result = (Object) method.invoke(o, args);// cal.sub(1,2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public static Object callPrivateMethod(Object o, String methodName, Object... args) {
		Class<?> cls = o.getClass();
		Method method = null;
		Object result = -1;
		Class<?>[] types = new Class<?>[args.length];
		boolean intToObj = false;
		for (int i = 0; i < types.length; i++) {
			if(!intToObj) {
				if(Integer.class==args[i].getClass()) {
					types[i]=int.class;
				} else {
					types[i]=args[i].getClass();
				}
			}
		}
		
		try {
			method = cls.getDeclaredMethod(methodName, types);
			method.setAccessible(true);
			result = (Object) method.invoke(o, args);// cal.sub(1,2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static Object callPrivateMethod(Class<?> className,Object o, String methodName, Object... args) {
		Class<?> cls = o.getClass();
		if(className!=null){
			cls=className;
		}

		Method method = null;
		Object result = -1;
		Class<?>[] types = new Class<?>[args.length];
		boolean intToObj = false;
		for (int i = 0; i < types.length; i++) {
			if(!intToObj) {
				if(Integer.class==args[i].getClass()) {
					types[i]=int.class;
				} else {
					types[i]=args[i].getClass();
				}
			}
		}
		try {
			method = cls.getDeclaredMethod(methodName, types);
			method.setAccessible(true);
			result = (Object) method.invoke(o, args);// cal.sub(1,2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static Object callPrivateMethod(Object o,Class<?> oclass, String methodName,
			Object... args) {
		Class<?> cls = o.getClass();
		if(oclass!=null){
			cls=oclass;
		}
		Method method = null;
		Object result = -1;
		Class<?>[] types=null;
		if(args!=null)
		{
			types = new Class<?>[args.length];
		
			boolean intToObj = false;
			for (int i = 0; i < types.length; i++) {
				if(!intToObj)
				{
					if(Integer.class==args[i].getClass())
						types[i]=int.class;
					else
						types[i]=args[i].getClass();
				}
			}
		}
		try {
			if(types==null){
				method = cls.getDeclaredMethod(methodName);
			}
			else
				method = cls.getDeclaredMethod(methodName, types);
			method.setAccessible(true);
			result = (Object) method.invoke(o, args);// cal.sub(1,2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
