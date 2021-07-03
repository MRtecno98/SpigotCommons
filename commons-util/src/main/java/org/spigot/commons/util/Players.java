package org.spigot.commons.util;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class Players {
	public static UUID getOfflineUUID(String name) {
		try {
			return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static OfflinePlayer offlineFromName(String name) {
		return Bukkit.getOfflinePlayer(getOfflineUUID(name));
	}
}
