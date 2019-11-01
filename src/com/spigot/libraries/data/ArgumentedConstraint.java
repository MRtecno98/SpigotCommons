package com.spigot.libraries.data;

public class ArgumentedConstraint implements Constraint {
	private ConstraintType type;
	private ConstraintArgument argument;
	
	public ArgumentedConstraint(ConstraintType type, ConstraintArgument argument) {
		this.type = type;
		this.argument = argument;
	}
	
	public ArgumentedConstraint(ConstraintType type) {
		this.type = type;
		this.argument = null;
	}

	public ConstraintType getType() {
		return type;
	}

	public ConstraintArgument getArgument() {
		return argument;
	}
	
	@Override
	public String toString() {
		return type.toString() + (argument != null ? " (" + argument.toString() + ")" : "");
	}
}
