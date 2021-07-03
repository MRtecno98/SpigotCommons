package org.spigot.commons.tests.commands;

import static org.junit.Assert.assertTrue;

import org.bukkit.command.CommandSender;
import org.spigot.commons.commands.Command;
import org.spigot.commons.commands.ExecutionContext;

public class DataTestSubcommand extends Command {
	public byte data1, data2, data3;
	
	public DataTestSubcommand() {
		super("datasub");
	}

	@Override
	public boolean execute(CommandSender sender, ExecutionContext context) {
		assertTrue(context.isLastCommand());
		
		return false;
	}
}
