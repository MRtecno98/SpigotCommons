package com.spigot.libraries.standalone;

import java.util.regex.Pattern;

import org.bukkit.plugin.java.JavaPlugin;

public class PluginClass extends JavaPlugin {
	public void onEnable() {
		String[] version = getDescription().getVersion().split(Pattern.quote("-"));
		
		getLogger().info("=================================");
		getLogger().info("SpigotCommons V." + version[1]);
		getLogger().info("   revision " + version[0]);
		getLogger().info("          by " 
				+ getDescription().getAuthors()
					.stream().findFirst().orElse("Unknown"));
		getLogger().info("=================================");
	}
}
