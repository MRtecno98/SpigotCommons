package com.spigot.libraries.data;

public enum FieldType {
	TEXT("text"), INT("int");
	
	private String sqlname;
	
	FieldType(String sqlname) {
		this.sqlname = sqlname;
	}
	
	@Override
	public String toString() {
		return this.sqlname.toLowerCase();
	}
}
