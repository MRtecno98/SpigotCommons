package com.spigot.libraries.utility;

import java.util.HashMap;
import java.util.Map;

public class Cloneable<T> implements java.lang.Cloneable {
	@SuppressWarnings("unchecked")
	public T clone() {
		try {
			return (T) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <K, V> Map<K, V> mapClone(Map<K, V> original) {
		Map<K, V> clone = new HashMap<>();
		for(Map.Entry<K, V> entry : original.entrySet())
			clone.put(entry.getKey() instanceof java.lang.Cloneable ? ReflectionUtils.reflectionClone(entry.getKey()) : entry.getKey(), 
					entry.getValue() instanceof java.lang.Cloneable ? ReflectionUtils.reflectionClone(entry.getValue()) : entry.getValue());
		return clone;
	}
}
