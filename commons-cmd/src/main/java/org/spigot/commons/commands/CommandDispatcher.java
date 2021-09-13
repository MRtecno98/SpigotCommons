package org.spigot.commons.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Dispatch commands across multiple instances coming from 
 * a single registered {@link CommandExecutor}
 * 
 * @author MRtecno98
 * @since 2.0.0
 */
@Getter
@NoArgsConstructor
public class CommandDispatcher implements CommandExecutor, TabCompleter {
	private Collection<Command> commands = new ArrayList<>();
	
	/**
	 * Adds one or multiple commands to the ones managed by this dispatcher
	 * 
	 * @param commands some commands to manage
	 * @return This instance
	 */
	public CommandDispatcher addCommands(Command... commands) {
		return addCommands(Arrays.asList(commands));
	}
	
	public CommandDispatcher addCommands(Collection<Command> commands) {
		getCommands().addAll(commands);
		return this;
	}
	
	/**
	 * Registers a single {@link CommandExecutor} managing all 
	 * of the commands added to this dispatcher
	 * 
	 * @param plugin a plugin instance to register this dispatcher to
	 */
	public void register(JavaPlugin plugin) {
		for(Command cmd : getCommands())
			cmd.getPluginCommand(plugin).setExecutor(this);
	}
	
	/**
	 * Executes the selected command from this dispatcher, if present
	 */
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		return getCommands().stream()
			.filter((cmd) -> cmd.checkLabel(label))
			.findFirst().get()
			.onCommand(sender, command, label, args);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label,
			String[] args) {
		return getCommands().stream()
				.filter((cmd) -> cmd.checkLabel(label))
				.findFirst().get()
				.onTabComplete(sender, command, label, args);
	}
}
