package com.spigot.libraries.config;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

public class DatabaseInfoSection extends MappedAbstractionSection {
	public static final String DATABASE_KEY = "database";
	public static final String USERNAME_KEY = "user";
	public static final String PASSWORD_KEY = "pass";
	public static final String TABLES_KEY = "tables";
	public static final String HOST_KEY = "host";
	public static final String PORT_KEY = "port";
	
	private ConnectionInfoBuilder conninfo;

	public DatabaseInfoSection(ConfigurationSection sect) {
		super(sect);
	}
	
	@Override
	public void preCloning(Object... data) {
		super.preCloning(data);
		conninfo = new ConnectionInfoBuilder();
	}
	
	@Override
	public void registerKeys(Object... data) {
		setKeys(
			DATABASE_KEY,
			USERNAME_KEY,
			PASSWORD_KEY,
			TABLES_KEY,
			HOST_KEY,
			PORT_KEY
		);
	}

	@Override
	public void onKeywordSet(String key, Object value) {
		String valuestr = String.valueOf(value);
		
		switch(key) {
		case HOST_KEY:
			getBuilder().setHost(valuestr);
			break;
			
		case USERNAME_KEY :
			getBuilder().setUsername(valuestr);
			break;
			
		case PASSWORD_KEY :
			getBuilder().setPassword(valuestr);
			break;
			
		case DATABASE_KEY :
			getBuilder().setDatabase(valuestr);
			break;
			
		case PORT_KEY :
			if(!valuestr.chars().allMatch( Character::isDigit )) 
				throw new RuntimeException("Cannot parse number \"" + valuestr + "\"");
			getBuilder().setPort(Integer.parseInt(valuestr));
			break;
			
		case TABLES_KEY :
			getBuilder().setTables(new AbstractionSection((ConfigurationSection) value) {
				Map<String, String> map;
				@Override
				public void preCloning(Object... data) {
					map = new HashMap<>();
				}
				
				@Override
				public void onSetting(String key, Object value) {
					map.put(key, String.valueOf(value));
				}
			}.map);
			break;
		}
		
	}
	
	protected ConnectionInfoBuilder getBuilder() {
		return conninfo;
	}
	
	public StructureInfo getConnectionInfo() {
		return getBuilder().build();
	}
	
	public class StructureInfo {
		private String host, username, password, database;
		private int port;
		private Map<String, String> tables;
		
		public StructureInfo(String host, String username, String password, String database, int port,
				Map<String, String> tables) {
			this.host = host;
			this.username = username;
			this.password = password;
			this.database = database;
			this.port = port;
			this.tables = tables;
		}
		
		public String getHost() {
			return host;
		}
		
		public String getUsername() {
			return username;
		}
		
		public String getPassword() {
			return password;
		}
		
		public String getDatabase() {
			return database;
		}
		
		public int getPort() {
			return port;
		}
		
		public Map<String, String> getTables() {
			return tables;
		}
	}
	
	class ConnectionInfoBuilder {
		private String host, username, password, database;
		private int port;
		private Map<String, String> tables;
		
		public ConnectionInfoBuilder setHost(String host) {
			this.host = host;
			return this;
		}
		
		public ConnectionInfoBuilder setUsername(String username) {
			this.username = username;
			return this;
		}
		
		public ConnectionInfoBuilder setPassword(String password) {
			this.password = password;
			return this;
		}
		
		public ConnectionInfoBuilder setDatabase(String database) {
			this.database = database;
			return this;
		}
		
		public ConnectionInfoBuilder setPort(int port) {
			this.port = port;
			return this;
		}
		
		public ConnectionInfoBuilder setTables(Map<String, String> tables) {
			this.tables = tables;
			return this;
		}
		
		public StructureInfo build() {
			return new StructureInfo(host, username, password, database, port, tables);
		}
	}

}
