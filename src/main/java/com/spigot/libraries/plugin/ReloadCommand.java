package com.spigot.libraries.plugin;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.spigot.libraries.commands.Command;
import com.spigot.libraries.commands.CommandFlag;
import com.spigot.libraries.exceptions.PluginNotProvidedException;

public class ReloadCommand extends Command {
	public static final String DEFAULT_MESSAGE = "Reloading plugin...";
	
	private ReloadablePlugin pl;
	private String message = DEFAULT_MESSAGE;
	
	public ReloadCommand(String label, CommandFlag... flags) {
		super(label, null, flags);
	}
	
	public ReloadCommand(String label, String permission, CommandFlag... flags) {
		this(label, permission, null, flags);
	}
	
	public ReloadCommand(String label, String permission, String permission_message, CommandFlag... flags) {
		super(label, permission, permission_message, flags);
	}
	
	public ReloadCommand message(String new_message) {
		this.message = new_message;
		return this;
	}
	
	public ReloadCommand target(ReloadablePlugin target) {
		this.pl = target;
		return this;
	}
	
	public String getMessage() {
		return message;
	}
	
	public ReloadablePlugin getTargetPlugin() {
		return pl;
	}
	
	@Override
	public Command register(JavaPlugin pl) {
		if(getTargetPlugin() == null)
			if(pl instanceof ReloadablePlugin) {
				this.pl = (ReloadablePlugin) pl;
			}else throw new PluginNotProvidedException(
					"Cannot register /reload without a targeted reloadable plugin");

		return super.register(pl);
	}

	@Override
	public boolean execute(CommandSender sender, org.bukkit.command.Command cmd, List<String> args) {
		sender.sendMessage(getMessage());
		getTargetPlugin().reload();
		return true;
	}
}
