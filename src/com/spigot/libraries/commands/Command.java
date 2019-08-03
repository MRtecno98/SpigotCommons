package com.spigot.libraries.commands;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.spigot.libraries.exceptions.ConflictingFlagsException;
import com.spigot.libraries.utility.ReflectionUtils;

public abstract class Command implements CommandExecutor {
	public static final String DEFAULT_PERM_MESSAGE = "&cYou don't have the permission to do that".replace('&', ChatColor.COLOR_CHAR);
	
	private String label;
	private Set<CommandFlag> flags;
	private JavaPlugin pl;
	private String permission, permission_message;
	
	public Command(JavaPlugin pl, String label, CommandFlag... flags) {
		this(pl, label, null, null, flags);
	}
	
	public Command(JavaPlugin pl, String label, String permission, CommandFlag... flags) {
		this(pl, label, permission, null, flags);
	}
	
	public Command(JavaPlugin pl, String label, String permission, String permission_message, CommandFlag... flags) {
		this.pl = pl;
		this.label = label;
		this.permission = permission;
		this.permission_message = permission_message != null ? permission_message : DEFAULT_PERM_MESSAGE;
		this.flags = new HashSet<CommandFlag>(Arrays.asList(flags));
		if(this.flags.contains(CommandFlag.ONLY_CONSOLE) && this.flags.contains(CommandFlag.ONLY_PLAYER)) 
			throw new ConflictingFlagsException("The Flags ONLY_CONSOLE and ONLY_PLAYER are incompatible");
		pl.getLogger().log(Level.INFO, "Registering command /" + label);
		getPluginCommand().setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		if(getPermission() != null) if(!sender.hasPermission(getPermission())) {
			sender.sendMessage(getPermissionMessage());
			return true;
		}
		if(flags.contains(CommandFlag.CASE_SENSITIVE) && !(label.equals(this.label))) return false;
		if(!(flags.contains(CommandFlag.CASE_SENSITIVE)) && !(label.equalsIgnoreCase(this.label))) return false;
		if(flags.contains(CommandFlag.ONLY_PLAYER) && !(sender instanceof Player)) return false;
		if(flags.contains(CommandFlag.ONLY_CONSOLE) && sender instanceof Player) return false;
		return execute(sender, cmd, Arrays.asList(args));
	}
	
	public abstract boolean execute(CommandSender sender, org.bukkit.command.Command cmd, List<String> args);
	
	protected JavaPlugin getPlugin() {
		return pl;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public String getPermissionMessage() {
		return permission_message;
	}
	
	public Collection<CommandFlag> getFlags() {
		return flags;
	}
	
	public PluginCommand getPluginCommand() {
		return pl.getCommand(label);
	}
	
	public void unregister() {
		try {
			getPluginCommand().unregister((SimpleCommandMap) ReflectionUtils.getPrivateField(pl.getServer().getPluginManager(), "commandMap"));
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
