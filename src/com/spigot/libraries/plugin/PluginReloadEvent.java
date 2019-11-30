package com.spigot.libraries.plugin;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PluginReloadEvent extends Event {
	private final ReloadablePlugin pl;
	
	public PluginReloadEvent(ReloadablePlugin pl) {
		this.pl = pl;
	}
	
	public ReloadablePlugin getPlugin() {
		return pl;
	}
    
    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
