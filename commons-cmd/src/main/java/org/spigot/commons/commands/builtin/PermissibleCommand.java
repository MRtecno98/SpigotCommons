package org.spigot.commons.commands.builtin;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spigot.commons.commands.Command;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

@Getter
public abstract class PermissibleCommand extends Command {

	public static final String DEFAULT_MESSAGE = ChatColor.RED + "You don't have the permission to do that";

	private String permission;
	private String permissionMessage;

	public PermissibleCommand(String label, String permission, String permissionMessage) {
		this(label, 0, permission, permissionMessage);
	}

	public PermissibleCommand(String label, String permission) {
		this(label, permission, DEFAULT_MESSAGE);
	}

	public PermissibleCommand(String label, int minimumArguments, String permission, String permissionMessage) {
		super(label, minimumArguments);
		this.permission = permission;
		this.permissionMessage = permissionMessage;
	}

	public PermissibleCommand(String label, int minimumArguments, String permission) {
		this(label, minimumArguments, permission, DEFAULT_MESSAGE);
	}

	public boolean onUnsatisfiedPermission(CommandSender sender) {
		sender.sendMessage(getPermissionMessage());
		return true;
	}

	@Override
	public synchronized boolean onCommand(CommandSender sender, org.bukkit.command.Command bukkitCommand, String label,
			String[] args) {

		if (!sender.hasPermission(getPermission()))
			return onUnsatisfiedPermission(sender);

		return super.onCommand(sender, bukkitCommand, label, args);
	}
}
