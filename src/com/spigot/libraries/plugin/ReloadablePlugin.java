package com.spigot.libraries.plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.spigot.libraries.commands.Command;
import com.spigot.libraries.localization.Localizer;

public class ReloadablePlugin extends JavaPlugin {
	private List<Listener> listeners = new ArrayList<>();
	private List<BukkitTask> tasks = new ArrayList<>();
	private List<Command> commands = new ArrayList<>();
	private List<Localizer> localizers = new ArrayList<>();
	
	private boolean firstload = true;
	
	protected Command trackCommand(Command cmd) {
		commands.add(cmd);
		return cmd;
	}
	
	protected Collection<? extends Command> trackCommands(Collection<? extends Command> cmds) {
		cmds.forEach((cmd) -> trackCommand(cmd));
		return cmds;
	}
	
	protected BukkitTask trackTask(BukkitTask task) {
		tasks.add(task);
		return task;
	}
	
	protected Listener trackListener(Listener ls) {
		listeners.add(ls);
		return ls;
	}
	
	protected Localizer trackLocalizer(Localizer lc) {
		localizers.add(lc);
		return lc;
	}
	
	public void reload() {
		unloadListeners();
		unloadTasks();
		unloadCommands();
		
		onUnload();
		
		listeners.clear();
		tasks.clear();
		commands.clear();
		localizers.clear();
		firstload = false;
		onEnable();
	}
	
	protected void onUnload() {}
	
	protected void unloadListeners() {
		for(Listener ls : listeners) HandlerList.unregisterAll(ls);
	}
	
	protected void unloadTasks() {
		for(BukkitTask task : tasks) task.cancel();
	}
	
	protected void unloadCommands() {
		for(Command cmd : commands) cmd.unregister(this);
	}
	
	public boolean isFirstLoad() {
		return firstload;
	}
}
