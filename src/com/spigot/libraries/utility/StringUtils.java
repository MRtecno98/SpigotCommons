package com.spigot.libraries.utility;

import java.util.Arrays;

public class StringUtils {
	public static String join(String separator, String... elements) {
		return String.join(separator, elements);
	}
	
	public static String[] pickFromArray(String[] base, String[] pick) {
		if(pick.length > base.length) return null;
		int i = 0;
        for(String s : pick) {
            if(!base[i].equals(s)) return null;
            i++;
        }
		return Arrays.copyOfRange(base, pick.length, base.length);
	}
}
