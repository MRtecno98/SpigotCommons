package com.spigot.libraries.commands;

/**
 * Represents different behaviors of a {@link Command}
 * 
 * @author MRtecno98
 * @since 1.0
 * @category Commands
 */
public enum CommandFlag {
	/**
	 * Limits the execution of the command to only players.
	 * <p> Incompatible with {@link #ONLY_CONSOLE}. </p>
	 */
	ONLY_PLAYER, 
	
	/**
	 * Limits the execution of the command to only the console.
	 * <p> Incompatible with {@link #ONLY_PLAYER}. </p>
	 */
	ONLY_CONSOLE, 
	
	/**
	 * Sets the command label recognition to be case insensitive.
	 */
	CASE_SENSITIVE;
}
