package org.spigot.commons.util;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class Players {
	/**
	 * Computes the UUID for an {@link OfflinePlayer} even when they are not connected
	 * 
	 * @param name the name of the player
	 * @return The UUID of the player
	 */
	public static UUID getOfflineUUID(String name) {
		try {
			return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Resolves an {@link OfflinePlayer} from its name by computing its UUID first
	 * 
	 * @param name the name of the player
	 * @return An instance of {@link OfflinePlayer}
	 * @see #getOfflineUUID(String)
	 */
	public static OfflinePlayer offlineFromName(String name) {
		return Bukkit.getOfflinePlayer(getOfflineUUID(name));
	}
}
