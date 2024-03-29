package org.spigot.commons.commands.arguments;

import lombok.Getter;
import org.spigot.commons.commands.ExecutionContext;
import org.spigot.commons.commands.exceptions.ParseException;

@Getter
public abstract class FixedArgument<T> extends Argument<T> {
	private final int size;

	public FixedArgument(String name, int size, boolean optional) {
		super(name, size, optional);
		this.size = size;
	}

	public FixedArgument(String name, boolean optional) {
		this(name, 1, optional);
	}

	public FixedArgument(String name) {
		this(name, false);
	}

	@Override
	public void execute(ExecutionContext ctx) {
		int argSize = ctx.args().size();
		final String arg = String.join(" ",
				ctx.args().subList(0, Math.min(size(), argSize)));

		try {
			super.execute(ctx);

			while(argSize - ctx.args().size() < size() && !ctx.args().isEmpty())
				ctx.args().pop();
		} catch (Exception e) {
			error().accept(ctx, new ParseException(arg, e));
			ctx.cancel();
		}
	}
}
