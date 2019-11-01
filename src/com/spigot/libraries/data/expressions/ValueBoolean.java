package com.spigot.libraries.data.expressions;

public class ValueBoolean implements Value {
	private boolean value;

	public ValueBoolean(boolean value) {
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
