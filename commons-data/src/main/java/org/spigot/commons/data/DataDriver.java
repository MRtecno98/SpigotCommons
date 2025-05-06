package org.spigot.commons.data;

import org.spigot.commons.util.Strings;
import org.spigot.commons.util.ThrowingConsumer;
import org.spigot.commons.util.ThrowingFunction;

import javax.sql.DataSource;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;
import java.util.function.Function;

import lombok.SneakyThrows;

public record DataDriver(DataSource source,
						 ClassLoader scriptLoader,
						 Function<DataDriver, CompletableFuture<Void>> initializer) implements Closeable {
	public DataDriver(DataSource source, ClassLoader scriptLoader) {
		this(source, scriptLoader, _ -> CompletableFuture.completedFuture(null));
	}

	public DataDriver(DataSource source) {
		this(source, DataDriver.class.getClassLoader());
	}

	public DataDriver(DataSource source, Function<DataDriver, CompletableFuture<Void>> initializer) {
		this(source, DataDriver.class.getClassLoader(), initializer);
	}

	public DataDriver(DataSource source, ClassLoader scriptLoader, InputStream script, Object... args) {
		this(source, scriptLoader, driver ->
				driver.prepareScript(script, args).executeUpdate().thenAccept(_ -> {}));
	}

	public DataDriver(DataSource source, ClassLoader scriptLoader, String script, Object... args) {
		this(source, scriptLoader, scriptLoader.getResourceAsStream(script), args);
	}

	public DataDriver(DataSource source, InputStream script, Object... args) {
		this(source, DataDriver.class.getClassLoader(), script, args);
	}

	public DataDriver(DataSource source, String script, Object... args) {
		this(source, DataDriver.class.getClassLoader(), script, args);
	}

	public CompletableFuture<Void> init() {
		return initializer().apply(this);
	}

	public Connection getConnection() {
		try {
			return source.getConnection();
		} catch (Exception e) {
			Logger.getAnonymousLogger()
					.throwing("DataDriver", "getConnection", e);
			throw new RuntimeException("Failed to obtain connection", e);
		}
	}

	public StatementFuture prepareStatement(String sql) {
		return new StatementFuture(CompletableFuture.supplyAsync(() -> {
			try {
				return getConnection().prepareStatement(sql);
			} catch (Exception e) {
				throw new RuntimeException("Failed to prepare statement", e);
			}
		}));
	}

	@SneakyThrows
	public StatementFuture prepareScript(InputStream script, Object... args) {
		return prepareStatement(Strings.readAll(script).formatted(args));
	}

	public StatementFuture prepareScript(String script, Object... args) {
		return prepareScript(Objects.requireNonNull(scriptLoader().getResourceAsStream(script)), args);
	}

	public int executeUpdate(PreparedStatement statement) {
		try {
			return statement.executeUpdate();
		} catch (SQLException e) {
			Logger.getAnonymousLogger()
					.throwing("DataDriver", "executeUpdate", e);
			throw new RuntimeException("Failed to execute update", e);
		} finally {
			try {
				statement.getConnection().close();
			} catch (SQLException e) {
				Logger.getAnonymousLogger()
						.throwing("DataDriver", "executeUpdate", e);
			}
		}
	}

	public ResultSet executeQuery(PreparedStatement statement) {
		try {
			return statement.executeQuery();
		} catch (SQLException e) {
			Logger.getAnonymousLogger()
					.throwing("DataDriver", "executeQuery", e);
			throw new RuntimeException("Failed to execute query", e);
		}
	}

	@Override
	public void close() throws IOException {
		if (source instanceof Closeable closeable)
			closeable.close();
	}

	// Just a wrapper around CompletableFuture<PreparedStatement> that
	// adds some utility methods for convenience's sake
	public class StatementFuture extends CompletableFuture<PreparedStatement> {
		public StatementFuture(CompletionStage<PreparedStatement> delegate) {
			delegate.whenComplete((t, throwable) -> {
				if (throwable != null)
					completeExceptionally(throwable);
				else
					complete(t);
			});

			whenComplete((t, throwable) -> {
				if (throwable != null)
					Logger.getAnonymousLogger()
							.throwing("DataDriver.StatementFuture", "whenComplete", throwable);
			});
		}

		public CompletableFuture<Integer> executeUpdate() {
			return thenApply(DataDriver.this::executeUpdate);
		}

		public ResultFuture executeQuery() {
			return new ResultFuture(thenApply(DataDriver.this::executeQuery));
		}

		public StatementFuture apply(ThrowingConsumer<PreparedStatement, SQLException> applier) {
			return new StatementFuture(thenApply(t -> {
				applier.accept(t);
				return t;
			}));
		}

		public static class ResultFuture extends CompletableFuture<ResultSet> {
			ResultFuture(CompletionStage<ResultSet> delegate) {
				delegate.whenComplete((t, throwable) -> {
					if (throwable != null)
						completeExceptionally(throwable);
					else
						complete(t);
				});

				whenComplete((t, throwable) -> {
					if (throwable != null)
						Logger.getAnonymousLogger()
								.throwing("DataDriver.StatementFuture.ResultFuture",
										"whenComplete", throwable);
				});
			}

			public <T> CompletableFuture<T> apply(ThrowingFunction<ResultSet, T, SQLException> applier) {
				return thenApply(r -> {
					try {
						return applier.apply(r);
					} finally {
						try {
							r.getStatement().getConnection().close();
						} catch (SQLException e) {
							Logger.getAnonymousLogger()
									.throwing("DataDriver.StatementFuture.ResultFuture",
											"apply", e);
						}
					}
				});
			}
		}
	}
}
