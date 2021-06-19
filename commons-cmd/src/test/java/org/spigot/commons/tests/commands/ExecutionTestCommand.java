package org.spigot.commons.tests.commands;

import org.bukkit.command.CommandSender;
import org.spigot.commons.commands.Command;
import org.spigot.commons.commands.ExecutionContext;

public class ExecutionTestCommand extends Command {
	public boolean executed = false;
	
	public ExecutionTestCommand() {
		super("test");
	}

	@Override
	public boolean execute(CommandSender sender, ExecutionContext context) {
		executed = true;
		return false;
	}
}
