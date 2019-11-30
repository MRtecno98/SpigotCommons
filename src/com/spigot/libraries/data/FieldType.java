package com.spigot.libraries.data;

import com.spigot.libraries.data.expressions.Value;

public enum FieldType implements IFieldType {
	TEXT("text"), 
	INT("int"), 
	DECIMAL("decimal"), 
	FLOAT("float"),
	DOUBLE("double"),
	REAL("real"),
	CHAR("char"), 
	DATE("date"), 
	DATETIME("datetime"), 
	TIMESTAMP("timestamp");
	
	private String sqlname;
	
	FieldType(String sqlname) {
		this.sqlname = sqlname;
	}
	
	public String getValue() {
		return sqlname;
	}
	
	public IFieldType arguments(Value... arguments) {
		return new ArgumentedFieldType(this, arguments);
	}
	
	@Override
	public String toString() {
		return getValue().toUpperCase();
	}
}
