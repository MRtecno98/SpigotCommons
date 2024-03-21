package org.spigot.commons.commands.flags;

import lombok.Getter;
import org.spigot.commons.commands.ExecutionContext;
import org.spigot.commons.commands.Node;

@Getter
public abstract class FlaggedNode extends Node {
	private CommandFlag[] flags;

	@Override
	public boolean precondition(ExecutionContext context) {
		for (CommandFlag flag : flags) {
			if (!flag.test().test(context)) {
				flag.error().accept(context);
				return false;
			}
		}

		return true;
	}

	public FlaggedNode flags(CommandFlag... flags) {
		this.flags = flags;
		return this;
	}
}
