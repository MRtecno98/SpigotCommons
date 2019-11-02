package com.spigot.libraries.utility;

import java.util.HashMap;
import java.util.Map;

public class FormattingUtils {
	public static final Map<String, Long> multipliers = new HashMap<>();
	
	static {
		multipliers.put("d", (long) 10);
		multipliers.put("h", (long) 100);
		multipliers.put("k", (long) 1000);
		multipliers.put("m", (long) 1000000);
		multipliers.put("g", (long) 1000000000);
		multipliers.put("t", ((long) 1000000000) * 1000);
		multipliers.put("p", ((long) 1000000000) * 1000000);
	}
	
	public static long parseShortenedNumber(String shtnumber) {
		String snumber = "", smultiplier = "";
		for(int i = shtnumber.length()-1; i >= 0; i--) if(Character.isDigit(shtnumber.charAt(i))) {
			snumber = shtnumber.substring(0, i+1);
			smultiplier = shtnumber.substring(i+1, shtnumber.length()).toLowerCase();
			break;
		}
		
		long number = Long.parseLong(snumber);
		Map<String, Long> freqMap = getFrequencyCharMap(smultiplier);
		
		long multiplier = 1;
		for(String letter : freqMap.keySet()) 
			multiplier*=Math.pow(multipliers.getOrDefault(letter, (long) 1), 
								 freqMap.get(letter));
		
		return number * multiplier;
		
	}
	
	public static Map<String, Long> getFrequencyCharMap(String str) {
		Map<String, Long> result = new HashMap<>();
		for(char c : str.toCharArray()) {
			String s = String.valueOf(c);
			result.put(s, (result.containsKey(s) ? result.get(s) : 0) + 1);
		}
		
		return result;
	}
}
