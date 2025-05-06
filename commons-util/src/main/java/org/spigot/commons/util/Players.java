package org.spigot.commons.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.google.gson.JsonParser;

public class Players {
	public static final String CHECK_ENDPOINT_FORMAT = "https://api.mojang.com/users/profiles/minecraft/%s";
	public static final String NAMES_MATCH = "^[a-zA-Z0-9_]{3,16}$";
	
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
	
	/**
	 * <p>Checks if a name is linked to a premium(paid) account of Minecraft</p>
	 * 
	 * <p><b>DO NOT USE THIS TO CHECK IF AN ONLINE PLAYER CURRENTLY ON THE SERVER IS CRACKED OR NOT</b><br>
	 * 
	 * as with a cracked client you can choose the name you want without entering 
	 * any password, and if that name corresponds to a paid account it WILL result as
	 * premium according to this method.</p>
	 * 
	 * <p>To check this, use instead the other methods 
	 * of the Mojang <a href="https://wiki.vg/Authentication">Authentication API</a></p>
	 * 
	 * @param name the username to check for
	 * @return <code>true</code> if premium, <code>false</code> otherwise
	 */
	public static boolean isPremium(String name) {
		try {
			URL url = new URL(String.format(CHECK_ENDPOINT_FORMAT, name));
	
			return Requests.getStatusCode(url) == 200;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Optional<UUID> getOnlineUUID(String name) {
		try {
			if(!name.matches(NAMES_MATCH)) return Optional.empty();
			
			URL url = new URL(String.format(CHECK_ENDPOINT_FORMAT, name));
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			
			if(connection.getResponseCode() != 200)
				return Optional.empty();
			
			try(Reader r = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
				
				return Optional.of(UUIDs.fromGenericFormat(
							new JsonParser().parse(r).getAsJsonObject().get("id").getAsString()
						));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
