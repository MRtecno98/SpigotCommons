package com.spigot.libraries.data;

import com.spigot.libraries.utility.Cloneable;

public class Column extends Cloneable<Column> {
	private String name;
	private FieldType type;
	
	public Column(String name, FieldType type) {
		this.name = name;
		this.type = type;
	}
	
	public Column(Column column) {
		this(column.getName(), column.getType());
	}

	public String getName() {
		return name;
	}

	public FieldType getType() {
		return type;
	}
	
	public Column clone() {
		return new Column(this);
	}
}
