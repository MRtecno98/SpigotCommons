package com.spigot.libraries.tasking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.spigot.libraries.exceptions.LinkedTaskException;

public abstract class PlayerLinkedTask extends BukkitRunnable {
	private Player p;
	private long identifier;
	
	public PlayerLinkedTask(Player p) {
		this.p = p;
		this.identifier = System.nanoTime();
	}
	
	public Player getPlayer() {
		return p;
	}
	
	protected long getIdentifier() {
		return identifier;
	}
	
	@Override
	public BukkitTask runTask(Plugin plugin) {
		startTask(this);
		return super.runTask(plugin);
	}
	
	@Override
	public BukkitTask runTaskAsynchronously(Plugin plugin) {
		startTask(this);
		return super.runTaskAsynchronously(plugin);
	}
	
	@Override
	public BukkitTask runTaskLater(Plugin plugin, long delay) {
		startTask(this);
		return super.runTaskLater(plugin, delay);
	}
	
	@Override
	public BukkitTask runTaskLaterAsynchronously(Plugin plugin, long delay) {
		startTask(this);
		return super.runTaskLaterAsynchronously(plugin, delay);
	}
	
	@Override
	public BukkitTask runTaskTimer(Plugin plugin, long delay, long period) {
		startTask(this);
		return super.runTaskTimer(plugin, delay, period);
	}
	
	@Override
	public BukkitTask runTaskTimerAsynchronously(Plugin plugin, long delay, long period) {
		startTask(this);
		return super.runTaskTimerAsynchronously(plugin, delay, period);
	}
	
	@Override
	public void run() {
		try {
			if(!isTaskStarted(this)) startTask(this);
			start();
		} finally {
			endTask(this);
		}
	}
	
	protected abstract void start();
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PlayerLinkedTask)) return false;
		return ((PlayerLinkedTask) other).getIdentifier() == getIdentifier();
	}
	
	//BEGIN STATIC MANAGER
	
	private static final Map<Player, List<PlayerLinkedTask>> taskmap = new HashMap<>();
	
	private static void startTask(PlayerLinkedTask task) {
		if(!taskmap.containsKey(task.getPlayer())) {
			List<PlayerLinkedTask> list = new ArrayList<>();
			list.add(task);
			taskmap.put(task.getPlayer(), list);
		}else taskmap.get(task.getPlayer()).add(task);
	}
	
	public static void endTask(PlayerLinkedTask task) {
		if(!taskmap.containsKey(task.getPlayer()) || 
				!taskmap.get(task.getPlayer()).contains(task)) throw new LinkedTaskException("Cannot end a non-started task");
		else taskmap.get(task.getPlayer()).remove(task);
	}
	
	public static boolean isTaskStarted(PlayerLinkedTask task) {
		if(!taskmap.containsKey(task.getPlayer())) return false;
		return taskmap.get(task.getPlayer()).contains(task);
	}
	
	public static Collection<PlayerLinkedTask> getPlayerLinkedTasks(Player p) {
		Collection<PlayerLinkedTask> tasks = taskmap.get(p);
		return tasks == null ? Collections.emptyList() : tasks;
	}
}
