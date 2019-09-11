package com.spigot.libraries.data;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.spigot.libraries.utility.Cloneable;

public abstract class Database extends Cloneable<Table> {
	private String name;
	private List<Table> tables;
	
	public Database(String name, Table... tables) {
		this.name = name;
		this.tables = Collections.unmodifiableList(Arrays.asList(tables));
	}

	public String getName() {
		return name;
	}

	public List<Table> getTables() {
		return tables;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getTable(String name) throws ClassCastException {
		for(Table t : getTables()) if(t.getName().equals(name)) return (T) t;
		return null;
	}
	
	public abstract void onInitialization(Connection conn);
	public abstract void onCreation(Connection conn);
	
	public abstract void onRegistration(DynamicDatabaseService service);
}
