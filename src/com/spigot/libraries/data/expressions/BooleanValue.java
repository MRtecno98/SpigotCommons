package com.spigot.libraries.data.expressions;

public class BooleanValue implements Value {
	private boolean value;

	public BooleanValue(boolean value) {
		this.value = value;
	}

	public boolean isValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
