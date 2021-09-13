package org.spigot.commons.tests.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.spigot.commons.commands.Command;
import org.spigot.commons.commands.ExecutionContext;

public class TabCompleteSubcommand extends Command {
	public static final List<String> CONTROL_DATA = Arrays.asList("subsugg1", "subsugg2");

	public TabCompleteSubcommand() {
		super("sub", 1);
	}

	@Override
	public boolean execute(CommandSender sender, ExecutionContext context) {
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, ExecutionContext context) {
		return CONTROL_DATA;
	}
}
