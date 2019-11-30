package com.spigot.libraries.utility;

public class MathUtils {
	public static long refraction(long base, long numerator, long denominator) {
		//t : T = x : b => x = (t * b) / T
		return Math.round((numerator * base) / denominator);
	}
	
	public static long percentage(long numerator, long denominator) {
		return refraction(100, numerator, denominator);
	}
	
	public static double percentual(double total, double percentage) {
		return ((total * percentage) / 100);
	}
	
	public static long toTicks(long seconds) {
		return seconds * 20;
	}
	
	public static long toSeconds(long ticks) {
		return ticks / 20;
	}
}
