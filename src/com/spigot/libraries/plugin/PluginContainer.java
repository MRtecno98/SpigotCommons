package com.spigot.libraries.plugin;

import java.util.Objects;

import org.bukkit.plugin.java.JavaPlugin;

import com.spigot.libraries.exceptions.PluginNotProvidedException;

public class PluginContainer {
	private JavaPlugin pl;
	
	public void setPluginInstance(JavaPlugin pl) {
		this.pl = pl;
	}
	
	public JavaPlugin getPlugin() {
		try {
			return Objects.requireNonNull(pl);
		} catch(NullPointerException exc) {
			throw new PluginNotProvidedException("Trying accessing plugin instance when not contained, did you forget to provide one?");
		}
	}
	
	private PluginContainer() {}
	
	private static PluginContainer instance;
	public static synchronized PluginContainer getInstance() {
		if(instance == null) instance = new PluginContainer();
		return instance;
	}
}
