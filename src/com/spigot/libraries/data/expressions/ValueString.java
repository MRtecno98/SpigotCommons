package com.spigot.libraries.data.expressions;

public class ValueString implements Value {
	private String value;

	public ValueString(String value) {
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
