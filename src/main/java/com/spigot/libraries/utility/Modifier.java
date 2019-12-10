package com.spigot.libraries.utility;

public enum Modifier {
	ABSTRACT(java.lang.reflect.Modifier.ABSTRACT),
	FINAL(java.lang.reflect.Modifier.FINAL),
	INTERFACE(java.lang.reflect.Modifier.INTERFACE),
	NATIVE(java.lang.reflect.Modifier.NATIVE),
	PRIVATE(java.lang.reflect.Modifier.PRIVATE),
	PROTECTED(java.lang.reflect.Modifier.PROTECTED),
	PUBLIC(java.lang.reflect.Modifier.PUBLIC),
	STATIC(java.lang.reflect.Modifier.STATIC),
	STRICT(java.lang.reflect.Modifier.STRICT),
	SYNCHRONIZED(java.lang.reflect.Modifier.SYNCHRONIZED),
	TRANSIENT(java.lang.reflect.Modifier.TRANSIENT),
	VOLATILE(java.lang.reflect.Modifier.VOLATILE);
	
	private int value;
	
	Modifier(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
