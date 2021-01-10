package com.spigot.libraries.data;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.spigot.libraries.utility.Cloneable;

public class Column extends Cloneable<Column> {
	private String name;
	private IFieldType type;
	private Constraint[] constraints;
	
	public Column(String name, IFieldType type, Constraint... constraints) {
		this.name = name;
		this.type = type;
		this.constraints = constraints;
	}
	
	public Column(Column column) {
		this(column.getName(), column.getType(), column.getConstraints());
	}

	public String getName() {
		return name;
	}

	public IFieldType getType() {
		return type;
	}
	
	public Constraint[] getConstraints() {
		return constraints;
	}
	
	@Override
	public String toString() {
		return name + " " + type.toString() + " " + String.join(" ", Arrays.stream(constraints)
				.map(Object::toString)
				.collect(Collectors.toList()));
	}
	
	public Column clone() {
		return new Column(this);
	}
}
