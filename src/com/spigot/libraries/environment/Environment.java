package com.spigot.libraries.environment;

import java.util.regex.Pattern;

public class Environment {
	public static JavaVersion getVersion() {
		String strversion = System.getProperty("java.version");
		String[] sepbuild = strversion.split(Pattern.quote("_"));
		String[] rest = sepbuild[0].split(Pattern.quote("."));
		
		return new JavaVersion(Integer.parseInt(rest[0]), 
				Integer.parseInt(rest[1]), 
				Integer.parseInt(rest[2]), 
				Integer.parseInt(sepbuild[1]));
	}
}
