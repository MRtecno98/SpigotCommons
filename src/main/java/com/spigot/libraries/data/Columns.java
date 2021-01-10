package com.spigot.libraries.data;

public final class Columns {
	public static final Column ID_COLUMN = integerIdentifier("id");
	
	public static Column integerIdentifier(String name) {
		return new Column(name, FieldType.INT, 
			ConstraintType.PRIMARY_KEY, ConstraintType.AUTO_INCREMENT);
	}
	
	private Columns() {}
}
