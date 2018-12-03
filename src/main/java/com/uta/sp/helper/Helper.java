package com.uta.sp.helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class Helper {

	public static Object getJavaObject(HttpServletRequest request, Class c)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Enumeration<String> parameterNames = request.getParameterNames();
		Object rObj = c.newInstance();
		Method[] methods = c.getMethods();
		while (parameterNames.hasMoreElements()) {
			String string = (String) parameterNames.nextElement();
			for (Method method : methods) {
				if (method.getName().toLowerCase().contains("set" + string.toLowerCase())) {
					Class<?>[] parameterTypes = method.getParameterTypes();
					
					if (parameterTypes[0].equals(int.class)) {
						method.invoke(rObj, Integer.parseInt(request.getParameter(string)));
					}else {
						method.invoke(rObj, request.getParameter(string));

					}
				}
			}

		}
		return rObj;

	}

}
