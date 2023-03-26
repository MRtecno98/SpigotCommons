package org.spigot.commons.data.drivers;

import lombok.Getter;
import org.spigot.commons.data.DataDriver;
import org.spigot.commons.data.query.Query;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@Getter
public class SqlDriver extends DataDriver<PreparedStatement, PreparedStatement, ResultSet> {
	private final DataSource source;
	private transient Connection connection;

	public SqlDriver(DataSource source, Class<? extends Query> enumClass) {
		super(enumClass);
		this.source = source;
	}

	@Override
	public CompletableFuture<Void> init() {
		return super.init().thenRun(() -> {
			try {
				connection = source.getConnection();
			} catch(Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public CompletableFuture<PreparedStatement> prepare(String query) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return connection().prepareStatement(query);
			} catch(SQLException e) {
				throw new RuntimeException("Failed to prepare statement", e);
			}
		});
	}

	@Override
	public CompletableFuture<ResultSet> query(PreparedStatement st) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return st.executeQuery();
			} catch(SQLException e) {
				throw new RuntimeException("Failed to execute query", e);
			}
		});
	}

	@Override
	public CompletableFuture<Integer> update(PreparedStatement st) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return st.executeUpdate();
			} catch(SQLException e) {
				throw new RuntimeException("Failed to execute update", e);
			}
		});
	}

	@Override
	public CompletableFuture<PreparedStatement> fill(PreparedStatement st, Object... args) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				for(int i = 0; i < args.length; i++)
					st.setObject(i + 1, args[i]);
				return st;
			} catch(SQLException e) {
				throw new RuntimeException("Failed to fill statement variables", e);
			}
		});
	}

	@Override
	public void close() throws Exception {
		connection().close();
	}
}
