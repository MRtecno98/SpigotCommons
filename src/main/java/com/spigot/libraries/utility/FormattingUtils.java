package com.spigot.libraries.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.text.StrSubstitutor;

public class FormattingUtils {
	
	public static class Multipliers {
		public static final Map<String, Long> DEC_MULTIPLIERS = new HashMap<>();
		public static final Map<String, Long> SEC_MULTIPLIERS = new HashMap<>();
		
		static {
			DEC_MULTIPLIERS.put("d", (long) 10);
			DEC_MULTIPLIERS.put("h", (long) 100);
			DEC_MULTIPLIERS.put("k", (long) 1000);
			DEC_MULTIPLIERS.put("m", (long) 1000000);
			DEC_MULTIPLIERS.put("g", (long) 1000000000);
			DEC_MULTIPLIERS.put("t", ((long) 1000000000) * 1000);
			DEC_MULTIPLIERS.put("p", ((long) 1000000000) * 1000000);
			
			SEC_MULTIPLIERS.put("s", (long) 1);
			SEC_MULTIPLIERS.put("m", (long) 60);
			SEC_MULTIPLIERS.put("h", (long) 60*60);
			SEC_MULTIPLIERS.put("d", (long) 60*60*24);
            SEC_MULTIPLIERS.put("p", (long) 60*60*24*30); //Month -> Period, because 'm' was taken by Minute
            SEC_MULTIPLIERS.put("y", (long) 60*60*24*30*12);
		}
		
		@SuppressWarnings("unchecked")
		public static <T> Map<String, T> decimalMultipliers() {
			return DEC_MULTIPLIERS.keySet().stream()
					.collect(Collectors.toMap((key) -> key, 
											  (key) -> (T) DEC_MULTIPLIERS.get(key)));
		}
		
		@SuppressWarnings("unchecked")
		public static <T> Map<String, T> secondsMultipliers() {
			return SEC_MULTIPLIERS.keySet().stream()
					.collect(Collectors.toMap((key) -> key, 
											  (key) -> (T) SEC_MULTIPLIERS.get(key)));
		}
		
		public static <T> Map<String, T> defaultMultipliers() {
			return decimalMultipliers();
		}
	}
	
	public static long parseShortenedExpression(String exp) { return parseShortenedExpression(exp, Multipliers.defaultMultipliers()); }
	public static long parseShortenedExpression(String exp, Map<String, Long> multipliers) {
		List<String> numbers = new ArrayList<>();
		
		String temp = "";
		for(int i = 0; i < exp.length(); i++) {
			char current = exp.charAt(i);
			Character precedent = i != 0 ? exp.charAt(i-1) : null;
			
			if(Character.isDigit(current) && 
					(precedent != null ? !Character.isDigit(precedent) : true)
					&& !temp.isEmpty()) {
				numbers.add(temp);
				temp = "";
			}
			
			temp+=current;
		}
		numbers.add(temp);
		
		return numbers
				.stream()
				.filter((el) -> el != "")
				.mapToLong((raw) -> parseShortenedNumber(raw, multipliers))
				.sum();
	}
	
	public static long parseShortenedNumber(String shtnumber) { return parseShortenedNumber(shtnumber, Multipliers.defaultMultipliers()); }
	public static long parseShortenedNumber(String shtnumber, Map<String, Long> multipliers) {
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
			multiplier*=Math.pow(multipliers.getOrDefault(letter, (long) 1).longValue(), 
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
	
	public static PlaceholderParser newParser() {
		return new PlaceholderParser();
	}
	
	public static class PlaceholderParser {
		private Map<String, String> placeholders = new HashMap<>();
		
		public PlaceholderParser replace(String key, String value) {
			placeholders.put(key, value);
			return this;
		}
		
		public String parse(String base) {
			return StrSubstitutor.replace(base, placeholders);
		}
	}
}
