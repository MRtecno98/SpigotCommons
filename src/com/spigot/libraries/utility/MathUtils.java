package com.spigot.libraries.utility;

public class MathUtils {
	public static long refraction(long base, long numerator, long denominator) {
		//t : T = x : b => x = (t * b) / T
		return Math.round((numerator * base) / denominator);
	}
	
	public static long percentage(long numerator, long denominator) {
		return refraction(100, numerator, denominator);
	}
}
