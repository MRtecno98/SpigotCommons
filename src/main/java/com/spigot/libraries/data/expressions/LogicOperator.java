package com.spigot.libraries.data.expressions;

public enum LogicOperator {
	AND("AND"),
	OR("OR"),
	NOT("NOT");
	
	private String value;
	
	LogicOperator(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
