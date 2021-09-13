package org.spigot.commons.tests.commands;

import static org.junit.Assert.fail;

import org.bukkit.command.CommandSender;
import org.spigot.commons.commands.Command;
import org.spigot.commons.commands.ExecutionContext;

public class MinArgTestCommand extends Command {

	public MinArgTestCommand() {
		super("test", 2);
		
		registerSubcommand(new Command("sub") {
			@Override
			public boolean execute(CommandSender sender, ExecutionContext context) {
				fail("Did not respect minimum argument requirement");
				
				return true;
			}
		});
	}

	@Override
	public boolean execute(CommandSender sender, ExecutionContext context) {
		return false;
	}
}
