package org.spigot.commons.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.spigot.commons.data.query.Query;
import org.spigot.commons.data.query.Target;
import org.spigot.commons.data.query.builtin.TargetedQuery;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@RequiredArgsConstructor
public abstract class DataDriver<PreparedStatement, BoundStatement, Result> implements AutoCloseable {
	private final Query[] queries;

	private transient final Map<Integer, PreparedStatement> prepared = new ConcurrentHashMap<>();

	public DataDriver(Class<? extends Query> enumClass) {
		this(enumClass.getEnumConstants());

		if(!enumClass.isEnum())
			throw new IllegalArgumentException("Queries class must be an enum");
	}

	public CompletableFuture<Void> init() {
		return prepare();
	}

	public CompletableFuture<Void> prepare() {
		return CompletableFuture.allOf(Arrays.stream(queries)
				.filter(q -> !(StringUtils.countMatches(q.format(), "%s") > 1))
				.map(q -> prepare(fill(q, q.availableTargets())))
				.toArray(CompletableFuture[]::new));
	}

	public String fill(Query q, Target[] targets) {
		return String.format(q.format(), (Object[]) targets);
	}

	public CompletableFuture<BoundStatement> fill(Query q, Target[] targets, Object... args) {
		final int key = TargetedQuery.hashCode(q, targets);
		if(!prepared().containsKey(key))
			return prepare(fill(q, targets)).thenCompose(st -> {
				addPrepared(key, st);
				return fill(st, args);
			});
		else return CompletableFuture.completedFuture(prepared().get(key)).thenCompose(st -> fill(st, args));
	}

	public CompletableFuture<Result> query(Query q, Target[] targets, Object... args) {
		return fill(q, targets, args).thenCompose(this::query);
	}

	public CompletableFuture<Integer> update(Query q, Target[] targets, Object... args) {
		return fill(q, targets, args).thenCompose(this::update);
	}

	protected void addPrepared(Integer hashCode, PreparedStatement statement) {
		prepared.put(hashCode, statement);
	}

	protected void addPrepared(Query q, PreparedStatement statement, Target... targets) {
		addPrepared(TargetedQuery.hashCode(q, targets), statement);
	}
	public abstract CompletableFuture<PreparedStatement> prepare(String query);
	public abstract CompletableFuture<Result> query(BoundStatement st);
	public abstract CompletableFuture<Integer> update(BoundStatement st);
	public abstract CompletableFuture<BoundStatement> fill(PreparedStatement st, Object... args);

	public CompletableFuture<Result> query(TargetedQuery q, Object... args) {
		return query(q, q.targets(), args);
	}

	public CompletableFuture<Result> query(Query q, Target target, Object... args) {
		return query(q, new Target[] { target }, args);
	}

	public CompletableFuture<Result> query(Query q, Target t1, Target t2, Object... args) {
		return query(q, new Target[] { t1, t2 }, args);
	}

	public CompletableFuture<Result> update(TargetedQuery q, Object... args) {
		return update(q, q.targets(), args);
	}

	public CompletableFuture<Integer> update(Query q, Target target, Object... args) {
		return update(q, new Target[] { target }, args);
	}

	public CompletableFuture<Integer> update(Query q, Target t1, Target t2, Object... args) {
		return update(q, new Target[] { t1, t2 }, args);
	}
}
