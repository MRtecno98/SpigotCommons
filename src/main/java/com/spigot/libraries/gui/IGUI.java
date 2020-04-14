package com.spigot.libraries.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.spigot.libraries.utility.Cloneable;
import com.spigot.libraries.utility.ReflectionUtils;
import com.spigot.libraries.utility.Signature;

public abstract class IGUI extends Cloneable<IGUI> implements Listener {
	protected Inventory inv;
	protected JavaPlugin pl;
	
	private static List<IGUI> registeredGUIs = new ArrayList<>();
	public static List<IGUI> getRegisteredGUIs() { return registeredGUIs; }
	
	public IGUI(JavaPlugin pl, InventoryHolder owner, int size, String name) {
		inv = Bukkit.createInventory(TrackingHolder.track(owner), size, name);
		Bukkit.getServer().getPluginManager().registerEvents(this, pl);
		this.pl = pl;
		IGUI.registeredGUIs.add(this);
	}
	
	public IGUI(JavaPlugin pl, InventoryHolder owner, InventoryType type, String name) {
		this(pl, owner, type.getDefaultSize(), name);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(event.getViewers().size() == 0) return;
		if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) { return; }
		if(event.getInventory().getViewers().size() > 0 && event.getInventory().getViewers().get(0).getName().equals(inv.getViewers().size() > 0 ? inv.getViewers().get(0).getName() : null))
			event.setCancelled(onClick(event, (Player) event.getWhoClicked(), event.getCurrentItem()));
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if(!event.getInventory().getType().equals(InventoryType.CHEST)) return;
		if(inv.getViewers().size() == 0) dealloc();
		if(event.getInventory().getViewers().get(0).getName().equals(inv.getViewers().size() > 0 ? inv.getViewers().get(0).getName() : null) || event.getInventory().hashCode() == inv.hashCode()) dealloc();
	}
	
	public void open(Player p) {
		p.openInventory(getInventory());
	}
	
	public void open() {
		InventoryHolder holder = ((TrackingHolder) getInventory().getHolder()).getWrapped();
		if(holder != null && holder instanceof Player) open((Player) holder);
	}
	
	@Override
	public IGUI clone() {
		return copy(null, null, -1, null);
	}
	
	public IGUI copy(JavaPlugin plugin, InventoryHolder holder, int size, String name) {
		if(plugin == null) plugin = getPlugin();
		if(holder == null) holder = getInventory().getHolder();
		if(size == -1) size = getInventory().getSize();
		if(name == null) name = getInventory().getName();
		
		IGUI clone = ReflectionUtils.constructorClone(this, new Signature().takes(JavaPlugin.class, 
				InventoryHolder.class, 
				int.class, 
				String.class),
			plugin, holder, size, name);
	clone.getInventory().setContents(getInventory().getContents());
	return clone;
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof IGUI)) return false;
		return ((IGUI) other).getInventory()
				.getContents()
				.equals(getInventory()
						.getContents());
	}
	
	/*
	 * \\Doesn't work
	 * 
	 * public void setInventoryName(String name) {
	 *	 try {
	 *		 ReflectionUtils.getFinalField(getInventory(), "title").set(getInventory(), name);
	 *	 } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
	 *		 e.printStackTrace();
	 *	 }
	 * }
	 * 
	 */
	
	public Inventory getInventory() { return inv; }
	protected void dealloc() { HandlerList.unregisterAll(this); }
	public JavaPlugin getPlugin() { return pl; }
  	protected abstract boolean onClick(InventoryClickEvent event, Player p, ItemStack item);
}
