package org.spigot.commons.commands;

import java.util.List;
import java.util.Optional;

import lombok.Value;

/**
 * Data class containing all information necessary to the handling of a command execution
 * 
 * @author MRtecno98
 * @since 2.0.0
 */
@Value
public class ExecutionContext {
	/**
	 * The label actually used to call this command, could be different 
	 * from the main label of the command if an alias was used
	 */
	private String aliasLabel;
	
	/**
	 * The Bukkit {@link org.bukkit.command.Command} object, useful 
	 * for getting more information about this command
	 */
	private org.bukkit.command.Command bukkitCommand;
	
	/**
	 * The arguments this command was called with, 
	 * this excludes subcommands
	 */
	private List<String> callArguments;
	
	/**
	 * The next command in the chain, if present
	 */
	private Optional<Command> nextCommand;
	
	public boolean isLastCommand() {
		return !getNextCommand().isPresent();
	}
}
