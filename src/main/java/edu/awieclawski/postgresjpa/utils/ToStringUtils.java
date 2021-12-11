package edu.awieclawski.postgresjpa.utils;

import java.lang.reflect.Field;
import java.util.Collection;

public class ToStringUtils {

	public static String toString(Object thisClass) {
		Field[] fields = thisClass.getClass().getDeclaredFields();
		String resultTxt = "";
		for (int x = 0; x < fields.length; x++) {
			try {
				Object obj = fields[x].get(thisClass);
				resultTxt += (confirmExclusions(obj, thisClass)) ? fields[x].getName() + "=" + (obj.toString()) + ", "
						: "";
			} catch (Exception ex) {
				continue;
			}
		}
		return resultTxt;
	}

	private static boolean confirmExclusions(Object obj, Object thisClass) {
		Boolean result = (obj) != null && !(obj.getClass().isInstance(thisClass));
		if (obj instanceof Collection<?>)
			result = !checkIfCollectionObjects(obj, thisClass);
		return result;
	}

	private static boolean checkIfCollectionObjects(Object obj, Object thisClass) {
		Boolean result = false;
		try {
			result = ((Collection<?>) obj).stream().filter(thisClass.getClass()::isInstance).findAny() != null;
		} catch (Exception ex) {
			;
		}
		return result;
	}

}
