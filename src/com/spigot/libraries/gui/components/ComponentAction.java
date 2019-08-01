package com.spigot.libraries.gui.components;

import java.util.concurrent.Callable;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ComponentAction<T> implements Callable<T> {
	private Player p;
	private JavaPlugin pl;
	
	public ComponentAction<T> setPlayer(Player p) {
		this.p = p;
		return this;
	}
	
	public ComponentAction<T> setPlugin(JavaPlugin pl) {
		this.pl = pl;
		return this;
	}
	
	protected Player getPlayer() {
		return p;
	}
	
	protected JavaPlugin getPlugin() {
		return pl;
	}
	
	
	public static final ComponentAction<Boolean> NOTHING = new ComponentAction<Boolean>() {
		@Override
		public Boolean call() throws Exception {
			return true;
		}
	};
}
