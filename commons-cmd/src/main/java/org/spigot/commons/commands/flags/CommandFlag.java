package org.spigot.commons.commands.flags;

import org.spigot.commons.commands.ExecutionContext;

import java.util.function.Consumer;
import java.util.function.Predicate;

public record CommandFlag(
		Predicate<ExecutionContext> test,
		Consumer<ExecutionContext> error
) {
	public CommandFlag(Predicate<ExecutionContext> test) {
		this(test, ctx -> {});
	}

	public CommandFlag(Predicate<ExecutionContext> test, String error) {
		this(test, ctx -> ctx.sender().sendMessage(error));
	}

	public CommandFlag withError(String error) {
		return withError(ctx -> ctx.sender().sendMessage(error));
	}

	public CommandFlag withError(Consumer<ExecutionContext> error) {
		return new CommandFlag(this.test, error);
	}
}
