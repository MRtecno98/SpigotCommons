package org.spigot.commons.commands.arguments;

import org.spigot.commons.commands.ExecutionContext;

import java.math.BigDecimal;

public class DecimalArgument extends FixedArgument<BigDecimal> {
	public DecimalArgument(String name, boolean optional) {
		super(name, optional);
	}

	public DecimalArgument(String name) {
		super(name);
	}

	@Override
	public BigDecimal parse(ExecutionContext ctx) {
		return new BigDecimal(ctx.args().pop());
	}
}
