package org.spigot.commons.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommandDispatcher implements CommandExecutor {
	private Collection<Command> commands = new ArrayList<>();
	
	public CommandDispatcher addCommands(Command... commands) {
		return addCommands(Arrays.asList(commands));
	}
	
	public CommandDispatcher addCommands(Collection<Command> commands) {
		getCommands().addAll(commands);
		return this;
	}
	
	public void register(JavaPlugin plugin) {
		for(Command cmd : getCommands())
			cmd.getPluginCommand(plugin).setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		return getCommands().stream()
			.filter((cmd) -> cmd.checkLabel(label))
			.findFirst().get()
			.onCommand(sender, command, label, args);
	}
}
