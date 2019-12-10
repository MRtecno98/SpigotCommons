package com.spigot.libraries.permissions;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class DefaultPerms implements PermissionsChecker {
	@Override
	public boolean hasPermission(OfflinePlayer p, String perm) {
		Player pl = p.getPlayer();
		return pl != null ? pl.hasPermission(perm) : false;
	}

	@Override
	public int getPriority() {
		return -1;
	}

	private DefaultPerms() {}
	private static final DefaultPerms INSTANCE = new DefaultPerms();
	public static synchronized DefaultPerms getInstance() { 
		return INSTANCE;
	}	
}
