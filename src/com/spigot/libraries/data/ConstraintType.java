package com.spigot.libraries.data;

public enum ConstraintType implements Constraint {
	NOT_NULL("NOT NULL"),
	PRIMARY_KEY("PRIMARY KEY"),
	AUTO_INCREMENT("AUTO_INCREMENT"),
	UNIQUE("UNIQUE"),
	DEFAULT("DEFAULT"),
	CHECK("CHECK");
	
	private String value;
	
	ConstraintType(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
