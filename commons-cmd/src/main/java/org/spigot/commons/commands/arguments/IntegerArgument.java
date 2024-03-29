package org.spigot.commons.commands.arguments;

import org.spigot.commons.commands.ExecutionContext;

public class IntegerArgument extends FixedArgument<Integer> {
	public IntegerArgument(String name) {
		this(name, false);
	}

	public IntegerArgument(String name, boolean optional) {
		super(name, optional);

		error(exc -> "Could not parse \"" + exc.invalid() + "\"");
	}

	@Override
	public Integer parse(ExecutionContext ctx) {
		return Integer.parseInt(ctx.args().pop());
	}
}
