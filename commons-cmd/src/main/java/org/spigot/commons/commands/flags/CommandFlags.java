package org.spigot.commons.commands.flags;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class CommandFlags {
	public static final CommandFlag PLAYERS_ONLY = new CommandFlag(
			ctx -> ctx.sender() instanceof Player,
			"Only players can execute this command");

	public static final CommandFlag CONSOLE_ONLY = new CommandFlag(
			ctx -> ctx.sender() instanceof ConsoleCommandSender,
			"Only console can execute this command");

	public static CommandFlag permission(String permission) {
		return new CommandFlag(
				ctx -> ctx.sender().hasPermission(permission),
				"Insufficient permission");
	}
}
