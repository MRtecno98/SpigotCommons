package com.spigot.libraries.data.expressions;

public class Operation implements Expression {
	private Expression right, left;
	private ComparationOperator operator;
	
	public Operation(Expression left, ComparationOperator operator, Expression right) {
		this.right = right;
		this.left = left;
		this.operator = operator;
	}
	
	public Operation(ComparationOperator operator, Expression right) {
		this.right = right;
		this.left = null;
		this.operator = operator;
	}

	public Expression getRight() {
		return right;
	}

	public Expression getLeft() {
		return left;
	}

	public ComparationOperator getOperator() {
		return operator;
	}
	
	@Override
	public String toString() {
		return "(" + (getLeft() != null ? getLeft().toString() : "") 
				+ " " + operator.toString() + " "
				+ (getRight() != null ? getRight().toString() : "") + ")";
	}
}
