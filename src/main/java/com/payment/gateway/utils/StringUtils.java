package com.payment.gateway.utils;

import org.springframework.lang.Nullable;

public class StringUtils {

	public static boolean isEmpty(String stringvalue) {
		boolean retVal = false;
		if ((stringvalue == null) || (stringvalue.trim().equalsIgnoreCase("NULL")) || stringvalue.trim().equals("")) {
			retVal = true;
		}
		return retVal;
	}

	public static boolean isEmpty(Object obj) {
		boolean retVal = false;
		if ((obj == null)) {
			retVal = true;
		}
		if (obj instanceof String) {
			String stringvalue = (String) obj;
			if ((stringvalue == null) || (stringvalue.trim().equalsIgnoreCase("NULL")) || stringvalue.trim().equals("")) {
				retVal = true;
			}
		}
		return retVal;
	}

	public static boolean isNotEmpty(String stringvalue) {
		return !isEmpty(stringvalue);
	}

	public static String getStringFromLong(Long val) {
		String retVal = "";
		try {
			retVal = String.valueOf(val);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retVal;
	}

	public static double getLongFromString(String val) {
		double retVal = 0;
		try {
			retVal = Long.parseLong(val);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retVal;
	}

	public static boolean hasText(@Nullable String str) {
		return (str != null && !str.isEmpty() && containsText(str));
	}

	private static boolean containsText(CharSequence str) {
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	
	public static String trim(String stringvalue) {
		String retVal = null;
		if ((stringvalue == null)) {
			retVal = stringvalue;
		}
		else {
			retVal = stringvalue.trim();
		}
		return retVal;
	}
}
