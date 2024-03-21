package org.spigot.commons.commands;

import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.Nullable;
import org.spigot.commons.commands.arguments.Argument;
import org.spigot.commons.commands.flags.FlaggedNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Getter
public abstract class Command extends FlaggedNode implements CommandExecutor, TabCompleter {
	private final String name;
	private final Collection<String> aliases;

	private final List<Argument<?>> arguments = new ArrayList<>();

	public Command(String name, String... aliases) {
		this.name = name;
		this.aliases = List.of(aliases);
	}

	@Override
	public boolean precondition(ExecutionContext context) {
		if(super.precondition(context) && checkLabel(context.args().peek())) {
			context.args().pop();
			return true;
		} else return false;
	}

	public boolean checkLabel(@Nullable String label) {
		return name().equals(label) || aliases().contains(label);
	}

	public int size() {
		return arguments().stream().map(Argument::minSize).reduce(0, Integer::sum);
	}

	@Override
	public Stream<Node> stream(ExecutionContext context) {
		Stream<Node> args = arguments().stream()
				.filter(a -> a.precondition(context))
				.flatMap(a -> a.stream(context));

		return Stream.concat(args, super.stream(context));
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		if(!checkLabel(label)) return false;

		ExecutionContext ctx = ExecutionContext.of(sender, cmd, label, args);
		traverse(ctx, NodeVisitor.EXECUTE);

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		if(!checkLabel(label)) return null;

		ExecutionContext ctx = ExecutionContext.of(sender, cmd, label, args);

		return traverse(ctx, NodeVisitor.TAB_COMPLETE).orElse(null);
	}
}
