package com.spigot.libraries.permissions;

import org.bukkit.OfflinePlayer;

public interface PermissionsChecker {
	public boolean hasPermission(OfflinePlayer p, String perm);
	public int getPriority();
}
