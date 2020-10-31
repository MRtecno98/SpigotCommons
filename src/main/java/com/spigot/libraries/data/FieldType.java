package com.spigot.libraries.data;

import java.sql.Types;

import com.spigot.libraries.data.expressions.Value;

public enum FieldType implements IFieldType {
	TEXT("text", Types.VARCHAR), 
	INT("int", Types.INTEGER), 
	DECIMAL("decimal", Types.DECIMAL), 
	FLOAT("float", Types.FLOAT),
	DOUBLE("double", Types.DOUBLE),
	REAL("real", Types.REAL),
	CHAR("char", Types.CHAR), 
	DATE("date", Types.DATE),
	TIMESTAMP("timestamp", Types.TIMESTAMP);
	
	private String sqlname;
	private int sqltype;
	
	FieldType(String sqlname, int sqltype) {
		this.sqlname = sqlname;
		this.sqltype = sqltype;
	}
	
	public String getValue() {
		return sqlname;
	}
	
	public int getSQLType() {
		return sqltype;
	}
	
	public IFieldType arguments(Value... arguments) {
		return new ArgumentedFieldType(this, arguments);
	}
	
	@Override
	public String toString() {
		return getValue().toUpperCase();
	}
}
