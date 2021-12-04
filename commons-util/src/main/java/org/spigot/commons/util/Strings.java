package org.spigot.commons.util;

import java.util.Arrays;

public class Strings {
	public static String[] stripEmpty(String[] arr) {
		return Arrays.asList(arr).stream()
			.filter((s) -> s.trim().equals(s) && !s.isEmpty())
			.toArray(String[]::new);
	}
}
