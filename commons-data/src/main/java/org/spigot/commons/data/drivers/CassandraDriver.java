package org.spigot.commons.data.drivers;

import com.datastax.driver.core.*;
import lombok.Getter;
import org.spigot.commons.data.DataDriver;
import org.spigot.commons.data.query.Query;

import java.util.concurrent.CompletableFuture;

@Getter
public class CassandraDriver extends DataDriver<PreparedStatement, Statement, ResultSet> {
	private final Cluster cluster;
	private transient Session session;

	public CassandraDriver(Cluster cluster, Class<? extends Query> enumClass) {
		super(enumClass);
		this.cluster = cluster;
	}

	@Override
	public CompletableFuture<Void> init() {
		return super.init().thenRun(() -> session = cluster.connect());
	}

	@Override
	public CompletableFuture<PreparedStatement> prepare(String query) {
		return CompletableFuture.supplyAsync(() -> session.prepare(query));
	}

	@Override
	public CompletableFuture<ResultSet> query(Statement st) {
		return CompletableFuture.supplyAsync(() -> session.execute(st));
	}

	@Override
	public CompletableFuture<Integer> update(Statement st) {
		return CompletableFuture.supplyAsync(() -> session.execute(st).wasApplied() ? 1 : 0);
	}

	@Override
	public CompletableFuture<Statement> fill(PreparedStatement st, Object... args) {
		return CompletableFuture.supplyAsync(() -> st.bind(args));
	}

	@Override
	public void close() {
		if(session != null)
			session.close();
		cluster.close();
	}
}
