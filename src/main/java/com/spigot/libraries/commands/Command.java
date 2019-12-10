package com.spigot.libraries.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.spigot.libraries.exceptions.ConflictingFlagsException;
import com.spigot.libraries.utility.IgnoreCaseList;
import com.spigot.libraries.utility.ReflectionUtils;
import com.spigot.libraries.utility.StringUtils;

public abstract class Command extends com.spigot.libraries.utility.Cloneable<Command> implements CommandExecutor {
	public static final String DEFAULT_PERM_MESSAGE = "&cYou don't have the permission to do that".replace('&', ChatColor.COLOR_CHAR);
	
	private String label;
	private Set<CommandFlag> flags;
	private String permission, permission_message;
	private IgnoreCaseList aliases;
	
	private Set<Command> subcommands;
	private boolean isSubcommand;
	
	@Deprecated
	public Command(JavaPlugin pl, String label, CommandFlag... flags) {
		this(pl, label, null, null, flags);
	}
	
	@Deprecated
	public Command(JavaPlugin pl, String label, String permission, CommandFlag... flags) {
		this(pl, label, permission, null, flags);
	}
	
	@Deprecated
	public Command(JavaPlugin pl, String label, String permission, String permission_message, CommandFlag... flags) {
		this(label, permission, permission_message, flags);
		register(pl);
	}
	
	public Command(String label, CommandFlag... flags) {
		this(label, null, null, flags);
	}
	
	public Command(String label, String permission, CommandFlag... flags) {
		this(label, permission, null, flags);
	}
	
	public Command(String label, String permission, String permission_message, CommandFlag... flags) {
		this.label = label;
		this.permission = permission;
		this.aliases = new IgnoreCaseList();
		this.subcommands = new HashSet<>();
		this.permission_message = permission_message != null ? permission_message : DEFAULT_PERM_MESSAGE;
		this.flags = flags != null ? new HashSet<CommandFlag>(Arrays.asList(flags)) : new HashSet<>();
		if(this.flags.contains(CommandFlag.ONLY_CONSOLE) && this.flags.contains(CommandFlag.ONLY_PLAYER)) 
			throw new ConflictingFlagsException("The Flags ONLY_CONSOLE and ONLY_PLAYER are incompatible");
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		if(getPermission() != null) if(!sender.hasPermission(getPermission())) {
			sender.sendMessage(getPermissionMessage());
			return true;
		}
		if(flags.contains(CommandFlag.CASE_SENSITIVE) && !(aliases.contains(label) || label.equals(getLabel()))) return false;
		if(!(flags.contains(CommandFlag.CASE_SENSITIVE)) && !(aliases.containsIgnoreCase(label) || label.equalsIgnoreCase(getLabel()))) return false;
		if(flags.contains(CommandFlag.ONLY_PLAYER) && !(sender instanceof Player)) return false;
		if(flags.contains(CommandFlag.ONLY_CONSOLE) && sender instanceof Player) return false;
		
		for(Command subcmd : getSubcommands()) {
			List<String[]> sublabels = new ArrayList<>();
			List<String> sublabelsraw = new ArrayList<String>(subcmd.getAliases());
			sublabelsraw.add(subcmd.getLabel());
			sublabels = sublabelsraw
					.stream()
					.map((arg) -> arg.split(" "))
					.collect(Collectors.toList());
			
			String[] rem;
			for(String[] sublabel : sublabels) 
				if((rem = StringUtils.pickFromArray(args, sublabel)) != null) 
					return subcmd.onCommand(sender, cmd, StringUtils.join(" ", sublabel), rem);
		}
		
		return execute(sender, cmd, Arrays.asList(args));
	}
	
	public abstract boolean execute(CommandSender sender, org.bukkit.command.Command cmd, List<String> args);
	
	public String getLabel() {
		return label;
	}
	
	public Command label(String label) {
		this.label = label;
		return this;
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
	
	public PluginCommand getPluginCommand(JavaPlugin pl) {
		PluginCommand plcmd = pl.getCommand(getLabel());
		return plcmd != null ? plcmd : ReflectionUtils.constructPluginCommand(getLabel(), pl);
	}
	
	public Command aliases(String... aliases) {
		this.aliases = new IgnoreCaseList(aliases);
		return this;
	}
	
	public List<String> getAliases() {
		return Collections.unmodifiableList(this.aliases);
	}
	
	protected Set<Command> getSubcommandsRef() {
		return subcommands;
	}
	
	public Set<Command> getSubcommands() {
		return new HashSet<Command>(getSubcommandsRef());
	}
	
	public boolean isSubcommand() {
		return isSubcommand;
	}
	
	protected void isSubcommand(boolean isSubcommand) {
		this.isSubcommand = isSubcommand;
	}
	
	public Command registerSubcommand(Command cmd) {
		getSubcommandsRef().add(cmd);
		cmd.isSubcommand(true);
		return this;
	}
	
	public Command register(JavaPlugin pl) {
		pl.getLogger().log(Level.INFO, "Registering command /" + getLabel());
		PluginCommand plcmd = getPluginCommand(pl);
		plcmd.setExecutor(this);
		if(pl.getCommand(getLabel()) == null) ReflectionUtils.registerToCommandMap(pl, plcmd);
		return this;
	}
	
	public void unregister(JavaPlugin pl) {
		try {
			getPluginCommand(pl).unregister((SimpleCommandMap) ReflectionUtils.getPrivateField(pl.getServer().getPluginManager(), "commandMap"));
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
