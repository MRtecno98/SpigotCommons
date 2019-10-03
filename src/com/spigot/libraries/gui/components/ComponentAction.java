package com.spigot.libraries.gui.components;

import java.util.concurrent.Callable;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.spigot.libraries.gui.CraftIGUI;

public abstract class ComponentAction implements Callable<Boolean> {
	private Player p;
	private CraftIGUI gui;
	
	public ComponentAction setPlayer(Player p) {
		this.p = p;
		return this;
	}
	
	public ComponentAction setIGUI(CraftIGUI gui) {
		this.gui = gui;
		return this;
	}
	
	protected Player getPlayer() {
		return p;
	}
	
	protected JavaPlugin getPlugin() {
		return gui.getPlugin();
	}
	
	protected CraftIGUI getGUI() {
		return gui;
	}
	
	
	public static final ComponentAction NOTHING = new ComponentAction() {
		@Override
		public Boolean call() throws Exception {
			return true;
		}
	};
}
