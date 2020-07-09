package com.spigot.libraries.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DynamicDatabaseService implements SQLService {
	private List<Database> databases = new ArrayList<>();
	
	public DynamicDatabaseService register(Database... tables) {
		List<Database> databases = Arrays.asList(tables);
		databases.forEach((database) -> {
			database.getTables().forEach((table) -> table.onRegistration(this, database));
			database.onRegistration(this);
		});
		getDatabases().addAll(databases);
		return this;
	}
	
	public boolean init() {
		boolean check = false;
		try {
			List<String> databases = getRemoteDatabases();
			
			for(Database db : getDatabases()) {
				if(!databases.contains(db.getName())) {
					check = true;
					PreparedStatement st = getConnection().prepareStatement("CREATE DATABASE " + db.getName());
					st.executeUpdate();
					db.onCreation(getConnection());
				}
				db.onInitialization(getConnection());
			}
			
			for(Database db : getDatabases()) {
				List<String> tables = getRemoteTables(db.getName());
				
				for(Table table : db.getTables()) {
					if(!tables.contains(table.getName())) {
						check = false;
						String sql = "CREATE TABLE " + table.getName() + " (";
						for(Column col : table.getColumns()) sql += col.toString() + ", ";
						sql = sql.substring(0, sql.length()-2);
						sql += ");";
						
						PreparedStatement st = getConnection().prepareStatement(sql);
						st.executeUpdate();
						
						table.onCreation(getConnection());
					}
					
					table.onInitialization(getConnection());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return !check;
	}
	
	public boolean checkStructure() {
		try {
			load();
			ResultSet resultSet = getConnection().getMetaData().getCatalogs();
			List<String> databases = new ArrayList<>();
			while (resultSet.next()) databases.add(resultSet.getString(1));
			for(Database db : getDatabases()) if(!databases.contains(db.getName())) return false;
			
			for(Database db : getDatabases()) {
				load(true, db.getName());
				ResultSet rs = getConnection().getMetaData().getTables(null, null, "%", null);
				List<String> tables = new ArrayList<>();
				while(rs.next()) tables.add(rs.getString(3));
				
				for(Table t : db.getTables()) if(!tables.contains(t.getName())) return false;
			}
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Database> getDatabases() { return databases; }
	
	@SuppressWarnings("unchecked")
	public <T extends Database> T getDatabase(String name) throws ClassCastException {
		for(Database db : getDatabases()) if(db.getName().equals(name)) return (T) db;
		return null;
	}
}
