package org.spigot.commons.commands.arguments;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.spigot.commons.commands.ExecutionContext;
import org.spigot.commons.commands.Node;
import org.spigot.commons.commands.exceptions.ParseException;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
public abstract class Argument<T> extends Node {
	private final String name;
	private final int minSize;
	private final boolean optional;

	private @Setter BiConsumer<ExecutionContext, ParseException> error =
			(ctx, ex) -> ctx.sender().sendMessage("Could not parse \"" + ex.invalid() + "\"");
	private @Setter Consumer<ExecutionContext> missing
			= ctx -> ctx.sender().sendMessage("Missing argument \"" + name() + "\"");

	public Argument(String name, int minSize, boolean optional) {
		this.name = name;
		this.minSize = minSize;
		this.optional = optional;
	}

	public Argument(String name, int minSize) {
		this(name, minSize, false);
	}


	public Argument(String name) {
		this(name, 1);
	}

	@Tolerate
	public Argument<T> error(Function<ParseException, String> message) {
		error(Replies.senderMessage(message));
		return this;
	}

	public abstract T parse(ExecutionContext ctx);

	@Override
	public void execute(ExecutionContext ctx) {
		try {
			if(ctx.args().size() < minSize()) {
				ctx.set(name(), null);
				if(!optional()) {
					missing.accept(ctx);
					ctx.cancel();
				}
			} else ctx.set(name(), Objects.requireNonNull(parse(ctx)));
		} catch (ParseException exc) {
			error.accept(ctx, exc);
			ctx.cancel();
		} catch(Exception e) {
			ctx.cancel();
			throw e;
		}
	}

	@Override
	public List<String> tabComplete(ExecutionContext ctx) {
		return List.of(name());
	}
}
