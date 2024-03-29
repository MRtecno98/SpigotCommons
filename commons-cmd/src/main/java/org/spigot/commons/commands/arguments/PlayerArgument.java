package org.spigot.commons.commands.arguments;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.spigot.commons.commands.ExecutionContext;

public class PlayerArgument extends FixedArgument<Player> {
	public PlayerArgument(String name) {
		this(name, false);
	}

	public PlayerArgument(String name, boolean optional) {
		super(name, 1, optional);

		error(exc -> "Player \"" + exc.invalid() + "\" not found");
	}

	@Override
	public Player parse(ExecutionContext ctx) {
		return Bukkit.getServer().getPlayer(ctx.args().pop());
	}
}
