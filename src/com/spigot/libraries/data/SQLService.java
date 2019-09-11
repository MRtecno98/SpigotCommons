package com.spigot.libraries.data;

import java.sql.Connection;
import java.util.List;

public interface SQLService {
	public boolean load(boolean overwrite, String dbp);
	public Connection getConnection();
	
	public List<String> getRemoteDatabases();
	public boolean remoteContainsDatabase(String remoteDatabase);
	
	public List<String> getRemoteTables(String remoteDatabase);
	
	public default void load(String db) { load(false, db); }
	public default void load(boolean overwrite) { load(overwrite, null); }
	public default void load() { load(false, null); }
}
