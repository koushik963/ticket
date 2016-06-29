package com.koushik.util;

import java.util.Collection;

public class Validator {

	public static boolean isCollectionNotEmpty(Collection<?> collection) {
		return !isCollectionEmpty(collection);
	}

	public static boolean isCollectionEmpty(Collection<?> collection) {
		if (collection == null || collection.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(String string) {
		return !isNull(string);
	}

	public static boolean isNull(String string) {
		if (string == null || string.trim().isEmpty()) {
			return true;
		}
		return false;
	}
}
