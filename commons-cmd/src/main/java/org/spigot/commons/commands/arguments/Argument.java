package org.spigot.commons.commands.arguments;

import lombok.Getter;
import org.spigot.commons.commands.ExecutionContext;
import org.spigot.commons.commands.Node;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Getter
public abstract class Argument<T> extends Node {
	public static final Consumer<ExecutionContext> DEFAULT_ERROR =
			ctx -> ctx.sender().sendMessage("Could not parse argument");

	private final String name;
	private final int minSize;
	private final boolean optional;
	private final Consumer<ExecutionContext> error;

	public Argument(String name, int minSize, boolean optional, Consumer<ExecutionContext> error) {
		this.name = name;
		this.minSize = minSize;
		this.optional = optional;
		this.error = error;
	}

	public Argument(String name, int minSize, Consumer<ExecutionContext> error) {
		this(name, minSize, false, error);
	}

	public Argument(String name, int minSize) {
		this(name, minSize, DEFAULT_ERROR);
	}

	public Argument(String name, int minSize, boolean optional) {
		this(name, minSize, optional, DEFAULT_ERROR);
	}

	public Argument(String name, Consumer<ExecutionContext> error) {
		this(name, 1, error);
	}

	public Argument(String name) {
		this(name, 1, DEFAULT_ERROR);
	}

	public Argument(String name, boolean optional) {
		this(name, 1, optional, DEFAULT_ERROR);
	}

	public abstract T parse(ExecutionContext ctx);

	@Override
	public boolean precondition(ExecutionContext ctx) {
		try {
			if(ctx.args().size() < minSize()) {
				ctx.set(name(), null);
				return optional();
			}

			ctx.set(name(), Objects.requireNonNull(parse(ctx)));
		} catch (Exception ignored) {}

		return true;
	}

	@Override
	public void execute(ExecutionContext ctx) {
		if(!ctx.data().containsKey(name())) {
			error.accept(ctx);
			ctx.cancel();
		}
	}

	@Override
	public List<String> tabComplete(ExecutionContext ctx) {
		return List.of(name());
	}
}
