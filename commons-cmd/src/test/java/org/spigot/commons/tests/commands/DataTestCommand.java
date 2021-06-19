package org.spigot.commons.tests.commands;

import org.bukkit.command.CommandSender;
import org.spigot.commons.commands.Command;
import org.spigot.commons.commands.ExecutionContext;
import org.spigot.commons.commands.annotations.Inherit;
import org.spigot.commons.commands.annotations.NoInherit;

@Inherit
public class DataTestCommand extends Command {
	public @NoInherit byte data1;
	public @Inherit byte data2;
	public byte data3;
	
	public DataTestSubcommand subcommand;
	
	public DataTestCommand(byte data) {
		super("test");
		
		this.data1 = data;
		registerSubcommand(subcommand = new DataTestSubcommand());
	}

	@Override
	public boolean execute(CommandSender sender, ExecutionContext context) {
		data2 = data1;
		data3 = data1;
		
		return false;
	}
}
