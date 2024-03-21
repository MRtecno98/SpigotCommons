package org.spigot.commons.commands;

import java.util.List;

public interface CommandExecutable {
	void execute(ExecutionContext ctx);

	default List<String> tabComplete(ExecutionContext ctx) {
		return List.of();
	};
}
