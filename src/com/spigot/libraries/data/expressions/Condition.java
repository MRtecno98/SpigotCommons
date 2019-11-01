package com.spigot.libraries.data.expressions;

public class Condition implements Expression {
	private Value right, left;
	private ComparationOperator operator;
	
	public Condition(Value left, ComparationOperator operator, Value right) {
		this.right = right;
		this.left = left;
		this.operator = operator;
	}

	public Value getRight() {
		return right;
	}
	
	public Value getLeft() {
		return left;
	}
	
	public ComparationOperator getOperator() {
		return operator;
	}
	
	@Override
	public String toString() {
		return "(" + left.toString() + " " + operator.toString() + " " + right.toString() + ")";
	}
}
