package org.spigot.commons.commands.arguments;

import org.spigot.commons.commands.ExecutionContext;

public class StringArgument extends FixedArgument<String> {
	public StringArgument(String name) {
		this(name, false);
	}

	public StringArgument(String name, boolean optional) {
		super(name, 1, optional);
	}

	@Override
	public String parse(ExecutionContext ctx) {
		return ctx.args().pop();
	}
}
