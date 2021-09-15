package org.spigot.commons.tests.commands;

import org.bukkit.command.CommandSender;
import org.spigot.commons.commands.Command;
import org.spigot.commons.commands.ExecutionContext;
import org.spigot.commons.commands.annotations.Help;

@Help(HelpCommandTests.CONTROL_DESCRIPTION)
public class HelpAnnotationTestCommand extends Command {
	public HelpAnnotationTestCommand() {
		super("test");
	}

	@Override
	public boolean execute(CommandSender sender, ExecutionContext context) {
		return false;
	}
}
