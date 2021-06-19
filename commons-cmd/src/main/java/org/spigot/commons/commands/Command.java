package org.spigot.commons.commands;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigot.commons.commands.annotations.Inherit;
import org.spigot.commons.commands.annotations.NoInherit;
import org.spigot.commons.util.CommonReflection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@NoInherit
@RequiredArgsConstructor
public abstract class Command implements CommandExecutor {
	private final String label;
	private Collection<Command> subcommands = new ArrayList<>();

	public abstract boolean execute(CommandSender sender, ExecutionContext context);

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command bukkitCommand, String label, String[] args) {
		List<String> arguments = Arrays.asList(args);
		
		String nextLabel = null;
		Optional<Command> nextCommand = Optional.empty();
		List<String> callArguments = new ArrayList<>();

		int i;
		for (i = 0; i < arguments.size(); i++) {
			String arg = arguments.get(i);
			nextCommand = getSubcommands().stream()
					.filter((cmd) -> cmd.checkLabel(arg)).findAny();

			if (nextCommand.isPresent()) {
				nextLabel = arg;
				break;
			} else callArguments.add(arg);
		}
		
		final int finalIndex = i;
		final String finalNextLabel = nextLabel;

		ExecutionContext context = new ExecutionContext(label, bukkitCommand, arguments.subList(0, finalIndex));
		if(execute(sender, context)) return true;

		nextCommand.ifPresent((next) -> {
			try {
				for (Field f : getClass().getDeclaredFields()) {
					boolean inherit = !getClass().isAnnotationPresent(NoInherit.class);
					inherit &= !f.isAnnotationPresent(NoInherit.class);
					inherit |= f.isAnnotationPresent(Inherit.class);
					
					if (!inherit || f.getName().contains("$"))
						continue;
					
					Field nextF;
					try {
						nextF = next.getClass().getDeclaredField(f.getName());
					} catch(NoSuchFieldException exc) {
						continue;
					}
					
					// Copy all data to inherited fields of next subcommand
					nextF.setAccessible(true);
					nextF.set(next, f.get(this));
					
					// Tried to reset values after transfer, bad idea
					// f.set(this, CommonReflection.getDefaultValue(f.getType()));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			
			// Cut away all arguments we already processed AND the subcommand label
			// Delimiters are i + 1(to cut away the label) and the list size
			// (a.k.a. end of the list), so final size will be the subtraction.
			String[] subArgs = arguments.subList(finalIndex + 1, arguments.size())
					.toArray(new String[arguments.size() - finalIndex - 1]);
			
			next.onCommand(sender, bukkitCommand, finalNextLabel, subArgs);
		});

		return true;
	}

	public void unregister(JavaPlugin pl) {
		try {
			getPluginCommand(pl).unregister((SimpleCommandMap) CommonReflection
					.getPrivateField(pl.getServer().getPluginManager(), "commandMap"));
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public boolean checkLabel(String target) {
		return getLabel().equalsIgnoreCase(target);
	}

	public void register(JavaPlugin plugin) {
		getPluginCommand(plugin).setExecutor(this);
	}

	public PluginCommand getPluginCommand(JavaPlugin pl) {
		return providePluginCommand(pl, getLabel());
	}

	public Command registerSubcommand(Command cmd) {
		getSubcommands().add(cmd);
		return this;
	}

	public static PluginCommand providePluginCommand(JavaPlugin plugin, String name) {
		PluginCommand pcommand = plugin.getCommand(name);

		if (pcommand == null)
			pcommand = CommonReflection.constructPluginCommand(name, plugin);

		return pcommand;
	}
}
