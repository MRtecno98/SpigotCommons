package org.spigot.commons.commands.arguments;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.spigot.commons.commands.ExecutionContext;

public class PlayerArgument extends Argument<Player> {
	public PlayerArgument(String name) {
		this(name, false);
	}

	public PlayerArgument(String name, boolean optional) {
		super(name, 1, optional, ctx -> ctx.sender().sendMessage("Player not found"));
	}

	@Override
	public Player parse(ExecutionContext ctx) {
		return Bukkit.getServer().getPlayer(ctx.args().pop());
	}
}
