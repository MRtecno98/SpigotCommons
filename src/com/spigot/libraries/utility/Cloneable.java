package com.spigot.libraries.utility;

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
}
