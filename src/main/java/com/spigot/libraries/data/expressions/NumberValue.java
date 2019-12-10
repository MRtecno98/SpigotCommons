package com.spigot.libraries.data.expressions;

public class NumberValue extends Number implements Value {
	private static final long serialVersionUID = -8141065409848009971L;
	
	private Number value;

	public NumberValue(Number value) {
		this.value = value;
	}

	public Number getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public double doubleValue() {
		return value.doubleValue();
	}

	@Override
	public float floatValue() {
		return value.floatValue();
	}

	@Override
	public int intValue() {
		return value.intValue();
	}

	@Override
	public long longValue() {
		return value.longValue();
	}
}
