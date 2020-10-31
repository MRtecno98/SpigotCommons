package com.spigot.libraries.data.expressions;

public class NullValue implements Value {
	@Override
	public String toString() {
		return "NULL";
	}
	
	private NullValue() {}
	
	public static final NullValue NULL = new NullValue();
}
