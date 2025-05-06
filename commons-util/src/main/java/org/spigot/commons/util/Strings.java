package org.spigot.commons.util;

import java.io.InputStream;
import java.util.Arrays;

public class Strings {
	public static String[] stripEmpty(String[] arr) {
		return Arrays.asList(arr).stream()
			.filter((s) -> s.trim().equals(s) && !s.isEmpty())
			.toArray(String[]::new);
	}

	public static String readAll(InputStream in) {
		try {
			String s = new String(in.readAllBytes());
			in.close();

			return s;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
