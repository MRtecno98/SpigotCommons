package com.spigot.libraries.data.expressions;

public class StringValue implements Value {
	private String value;

	public StringValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}
}
