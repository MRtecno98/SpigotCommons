package org.spigot.commons.tests.commands;

import org.bukkit.entity.Player;
import org.spigot.commons.commands.Command;
import org.spigot.commons.commands.ExecutionContext;
import org.spigot.commons.commands.arguments.PlayerArgument;
import org.spigot.commons.commands.flags.CommandFlags;

public class TestCommand extends Command {
	public TestCommand() {
		super("greet", "hello");

		flags(CommandFlags.PLAYERS_ONLY,
			  CommandFlags.permission("msg.greet")
					  .withError("&cYou do not have permission to use this command"));

		arguments().add(new PlayerArgument("target", true)
				.error(exc -> "&cCould not find player \"" + exc.invalid() + "\""));
	}

	@Override
	public void execute(ExecutionContext ctx) {
		ctx.sender().sendMessage("Hello, "
				+ ctx.<Player>getOptional("target")
						.map(Player::getName)
						.orElse(ctx.sender().getName()) + "!");
	}
}
