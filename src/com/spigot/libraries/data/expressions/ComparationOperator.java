package com.spigot.libraries.data.expressions;

public enum ComparationOperator {
	GREATER(">"),
	LOWER("<"),
	EGREATER(">="),
	ELOWER("<="),
	EQUAL("="),
	NEQUAL("<>"),
	NGREATER("!>"),
	NLOWER("!<");
	
	private String value;
	
	ComparationOperator(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
