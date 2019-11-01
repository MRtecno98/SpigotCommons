package com.spigot.libraries.data;

public enum FieldType {
	TEXT("text"), 
	INT("int"), 
	DECIMAL("decimal"), 
	CHAR("char"), 
	DATE("date"), 
	DATETIME("DATETIME"), 
	TIMESTAMP("timestamp");
	
	private String sqlname;
	
	FieldType(String sqlname) {
		this.sqlname = sqlname;
	}
	
	public String getValue() {
		return sqlname;
	}
	
	@Override
	public String toString() {
		return getValue().toUpperCase();
	}
}
