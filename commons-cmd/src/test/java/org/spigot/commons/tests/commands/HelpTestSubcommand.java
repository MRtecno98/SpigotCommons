package org.spigot.commons.tests.commands;

import org.bukkit.command.CommandSender;
import org.spigot.commons.commands.Command;
import org.spigot.commons.commands.ExecutionContext;

public class HelpTestSubcommand extends Command {
	public HelpTestSubcommand() {
		super("sub");
	}

	@Override
	public boolean execute(CommandSender sender, ExecutionContext context) {
		return false;
	}
}
