package com.spigot.libraries.data;

import java.sql.Connection;

import com.spigot.libraries.utility.Cloneable;;

public class Table extends Cloneable<Table> {
	private String name;
	private Column[] columns;
	
	public Table(String name, Column... fields) {
		this.name = name;
		this.columns = fields;
	}
	
	public Table(Table table) {
		this(table.getName(), table.getColumns());
	}

	public String getName() {
		return name;
	}

	public Column[] getColumns() {
		return columns;
	}
	
	public void onInitialization(Connection conn) {};
	public void onCreation(Connection conn) {};
	
	public void onRegistration(DynamicDatabaseService service, Database db) {};
}
