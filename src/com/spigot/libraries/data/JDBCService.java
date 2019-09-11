package com.spigot.libraries.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCService extends DynamicDatabaseService {
	private String host,user,pass;
	private int port;
	
	private Connection conn;
	
	public JDBCService(String host, int port, String user, String pass) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.pass = pass;
	}
	
	@Override
	public boolean load(boolean overwrite, String dbp) {
		try{
			if(!overwrite) if(conn != null) {
				if(!conn.isClosed()) return true;
			}
			
			if(overwrite && conn != null && !conn.isClosed()) conn.close();
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + (dbp != "" && dbp != null ? ("/" + dbp) : ""), user, pass);
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public List<String> getRemoteDatabases() {
		try {
			load();
			ResultSet resultSet = getConnection().getMetaData().getCatalogs();
			List<String> databases = new ArrayList<>();
			while (resultSet.next()) databases.add(resultSet.getString(1));
			return databases;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean remoteContainsDatabase(String remoteDatabase) { 
		return getRemoteDatabases().contains(remoteDatabase); 
	}
	
	public List<String> getRemoteTables(String remoteDatabase) {
		try {
			load(true, remoteDatabase);
			ResultSet rs = conn.getMetaData().getTables(null, null, "%", null);
			List<String> tables = new ArrayList<>();
			while(rs.next()) tables.add(rs.getString(3));
			return tables;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public Connection getConnection() { return conn; }
	
	public boolean isConnected() { 
		try {
			return conn != null && !conn.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
