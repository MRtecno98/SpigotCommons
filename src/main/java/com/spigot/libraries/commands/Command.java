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

/**
 * Represents a command. When registered creates a command executor, offers registering and unregistering without plugin.yml declaration.
 * 
 * @author MRtecno98
 * @since 1.0
 *
 */
public abstract class Command extends com.spigot.libraries.utility.Cloneable<Command> implements CommandExecutor {
	/**
	 * Sets the default message printed to the sender in case the permissions check fails and {@link #permission_message} is null.
	 * <p>
	 * It's equivalent to {@code "&cYou don't have the permission to do that".replace('&', ChatColor.COLOR_CHAR)}
	 * </p>
	 * 
	 * @see org.bukkit.ChatColor
	 */
	public static final String DEFAULT_PERM_MESSAGE = "&cYou don't have the permission to do that".replace('&', ChatColor.COLOR_CHAR);
	
	private String label;
	private Set<CommandFlag> flags;
	private String permission, permission_message;
	private IgnoreCaseList aliases;
	
	private Set<Command> subcommands;
	private boolean isSubcommand;
	
	/**
	 * Constructs a command and registers it.
	 * <p>
	 * Delegates to {@link #Command(JavaPlugin, String, String, String, CommandFlag...)}, defaults null for unused properties
	 * </p>
	 * 
	 * @param pl {@link org.bukkit.plugin.java.JavaPlugin} instance to link the command with at registration
	 * @param pl {@link org.bukkit.plugin.java.JavaPlugin} instance to link the command with at registration
	 * @param label Command label, being used for recognizing the command
	 * @param flags Instances of {@link com.spigot.libraries.commands.CommandFlag} for manipulating command behavior
	 * 
	 * @see #Command(JavaPlugin, String, String, String, CommandFlag...)
	 * 
	 * @deprecated Registering within constructor is unsupported, use {@link #Command(String, String, CommandFlag...)}
	 */
	@Deprecated
	public Command(JavaPlugin pl, String label, CommandFlag... flags) {
		this(pl, label, null, null, flags);
	}
	
	/**
	 * Constructs a command and registers it.
	 * <p>
	 * Delegates to {@link #Command(JavaPlugin, String, String, String, CommandFlag...)}, defaults null for unused properties
	 * </p>
	 * 
	 * @param pl {@link org.bukkit.plugin.java.JavaPlugin} instance to link the command with at registration
	 * @param pl {@link org.bukkit.plugin.java.JavaPlugin} instance to link the command with at registration
	 * @param label Command label, being used for recognizing the command
	 * @param permission Permission node needed for executing this command, 
	 * 		permissions are superperms defined by {@link org.bukkit.permissions.Permissible#hasPermission(String)}.
	 * 		If null, the permissions are not checked.
	 * @param flags Instances of {@link com.spigot.libraries.commands.CommandFlag} for manipulating command behavior
	 * 
	 * @see #Command(JavaPlugin, String, String, String, CommandFlag...)
	 * 
	 * @deprecated Registering within constructor is unsupported, use {@link #Command(String, String, CommandFlag...)}
	 */
	@Deprecated
	public Command(JavaPlugin pl, String label, String permission, CommandFlag... flags) {
		this(pl, label, permission, null, flags);
	}
	
	/**
	 * Constructs a command and registers it.
	 * <p>
	 * Other than the plugin instance, has the same arguments as {@link #Command(String, String, String, CommandFlag...)}
	 * </p>
	 * 
	 * @param pl {@link org.bukkit.plugin.java.JavaPlugin} instance to link the command with at registration
	 * @param label Command label, being used for recognizing the command
	 * @param permission Permission node needed for executing this command, 
	 * 		permissions are superperms defined by {@link org.bukkit.permissions.Permissible#hasPermission(String)}.
	 * 		If null, the permissions are not checked.
	 * @param permission_message Message printed to the sender if the permissions check fails, if null, defaults to {@link #DEFAULT_PERM_MESSAGE}
	 * @param flags Instances of {@link com.spigot.libraries.commands.CommandFlag} for manipulating command behavior
	 * 
	 * @see #Command(String, String, String, CommandFlag...)
	 * 
	 * @deprecated Registering within constructor is unsupported, use {@link #Command(String, String, String, CommandFlag...)}
	 */
	@Deprecated
	public Command(JavaPlugin pl, String label, String permission, String permission_message, CommandFlag... flags) {
		this(label, permission, permission_message, flags);
		register(pl);
	}
	
	/**
	 * Constructs a command without registering it
	 * <p>
	 * Delegates to {@link #Command(String, String, String, CommandFlag...)}, defaults null for unused properties
	 * </p>
	 * 
	 * @param label Command label, being used for recognizing the command
	 * @param flags Instances of {@link com.spigot.libraries.commands.CommandFlag} for manipulating command behavior
	 * 
	 * @see #Command(String, String, String, CommandFlag...)
	 */
	public Command(String label, CommandFlag... flags) {
		this(label, null, null, flags);
	}
	
	/**
	 * Constructs a command without registering it<br>
	 * Delegates to {@link #Command(String, String, String, CommandFlag...)}, defaults null for unused properties
	 * 
	 * @param label Command label, being used for recognizing the command
	 * @param permission Permission node needed for executing this command, 
	 * 		permissions are superperms defined by {@link org.bukkit.permissions.Permissible#hasPermission(String)}.
	 * 		If null, the permissions are not checked.
	 * @param flags Instances of {@link com.spigot.libraries.commands.CommandFlag} for manipulating command behavior
	 * 
	 * @see #Command(String, String, String, CommandFlag...)
	 */
	public Command(String label, String permission, CommandFlag... flags) {
		this(label, permission, null, flags);
	}
	
	/**
	 * Constructs a command without registering it
	 * 
	 * @param label Command label, being used for recognizing the command
	 * @param permission Permission node needed for executing this command, 
	 * 		permissions are superperms defined by {@link org.bukkit.permissions.Permissible#hasPermission(String)}.
	 * 		If null, the permissions are not checked.
	 * @param permission_message Message printed to the sender if the permissions check fails, if null, defaults to {@link #DEFAULT_PERM_MESSAGE}
	 * @param flags Instances of {@link com.spigot.libraries.commands.CommandFlag} for manipulating command behavior
	 * 
	 */
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
	
	/**
	 * This method is called by the Spigot server for command handling and executes all of the logic necessary before delegating to {@link #execute}
	 * <p>
	 * This should NOT be called manually, unless you know what you're doing.
	 * </p>
	 */
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
	
	/**
	 * Executes the actions specific of the command. Called by 
	 * 		{@link #onCommand(CommandSender, org.bukkit.command.Command, String, String[])}
	 * 
	 * @param sender See {@code sender} in {@link #onCommand}.
	 * @param cmd See {@code cmd} in {@link #onCommand}.
	 * @param args Arguments passed to the command encapsulated in a {@link List} instance.
	 * @return Same as {@link #onCommand}
	 */
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
	
	/**
	 * Tries to obtain a {@link PluginCommand} instance for this command based on the {@link JavaPlugin} passed.
	 * <p>
	 * If the process fails, constructs a new {@link PluginCommand} using the {@link ReflectionUtils#constructPluginCommand} method.
	 * </p>
	 * 
	 * @param pl The {@link JavaPlugin} instance used to try to resolve the command registration.
	 * @return The {@link PluginCommand} obtained.
	 */
	public PluginCommand getPluginCommand(JavaPlugin pl) {
		PluginCommand plcmd = pl.getCommand(getLabel());
		return plcmd != null ? plcmd : ReflectionUtils.constructPluginCommand(getLabel(), pl);
	}
	
	/**
	 * Sets aliases to this command stored in an {@link IgnoreCaseList}
	 * <p>This will erase all previous stored aliases</p>
	 * 
	 * @param aliases The aliases to store
	 * @return The object this method is called on
	 */
	public Command aliases(String... aliases) {
		this.aliases = new IgnoreCaseList(aliases);
		return this;
	}
	
	/**
	 * Provides aliases stored by {@link #aliases}
	 * 
	 * @return Stored aliases contained in a {@link IgnoreCaseList} encapsulated by {@link Collections#unmodifiableList}
	 */
	public List<String> getAliases() {
		return Collections.unmodifiableList(this.aliases);
	}
	
	/**
	 * @return The reference to the Subcommands {@link Set}, used by subclasses for direct access.
	 */
	protected Set<Command> getSubcommandsRef() {
		return subcommands;
	}
	
	/**
	 * @return A copy of the Subcommands {@link Set}.
	 */
	public Set<Command> getSubcommands() {
		return new HashSet<Command>(getSubcommandsRef());
	}
	
	/**
	 * Checks if this command is declared as subcommand
	 * 
	 * @return {@code true} if subcommand, {@code false} otherwise
	 */
	public boolean isSubcommand() {
		return isSubcommand;
	}
	
	/**
	 * Sets this command as subcommand, used by subclasses for behavior manipulation.<br>
	 * This can break many things, use it only if you know what you're doing.
	 * 
	 * @param isSubcommand If {@code true} the command is set to subcommand state, if {@code false} it's reverted to a normal command.
	 */
	protected void isSubcommand(boolean isSubcommand) {
		this.isSubcommand = isSubcommand;
	}
	
	/**
	 * Registers a command as subcommand of this command.
	 * <p>
	 * Also, sets the state of the linked command as subcommand.
	 * </p>
	 * 
	 * @param cmd The command to link as subcommand
	 * @return The Command instance on wich this method is called on
	 */
	public Command registerSubcommand(Command cmd) {
		getSubcommandsRef().add(cmd);
		cmd.isSubcommand(true);
		return this;
	}
	
	/**
	 * Registers the {@link PluginCommand} instance provided by PluginCommand to the Spigot API.
	 * <p>
	 * Also, if the PluginCommand is being generated artificially using {@link ReflectionUtils}, 
	 * registers the PluginCommand to the global command map using {@link ReflectionUtils#registerToCommandMap}
	 * </p>
	 * 
	 * @param pl The Plugin instance used to try to get the PluginCommand
	 * @return The Command instance on wich this method is called on
	 * 
	 * @see #getPluginCommand(JavaPlugin)
	 * @see ReflectionUtils#registerToCommandMap(JavaPlugin, org.bukkit.command.Command)
	 */
	public Command register(JavaPlugin pl) {
		pl.getLogger().log(Level.INFO, "Registering command /" + getLabel());
		PluginCommand plcmd = getPluginCommand(pl);
		plcmd.setExecutor(this);
		if(pl.getCommand(getLabel()) == null) ReflectionUtils.registerToCommandMap(pl, plcmd);
		return this;
	}
	
	/**
	 * Unregisters this command's {@link PluginCommand} from the command map
	 * 
	 * @param pl {@link JavaPlugin} instance used for obtaining the PluginCommand
	 */
	public void unregister(JavaPlugin pl) {
		try {
			getPluginCommand(pl).unregister((SimpleCommandMap) ReflectionUtils.getPrivateField(pl.getServer().getPluginManager(), "commandMap"));
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
