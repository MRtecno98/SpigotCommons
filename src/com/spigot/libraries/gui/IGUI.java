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

public abstract class IGUI implements Listener {
	protected Inventory inv;
	protected JavaPlugin pl;
	
	private static List<IGUI> registeredGUIs = new ArrayList<>();
	public static List<IGUI> getRegisteredGUIs() { return registeredGUIs; }
	
	public IGUI(JavaPlugin pl, InventoryHolder owner, int size, String name) {
		this.pl = pl;
		inv = Bukkit.createInventory(owner, size, name);
		pl.getServer().getPluginManager().registerEvents(this, this.pl);
		IGUI.registeredGUIs.add(this);
	}
	
	public IGUI(JavaPlugin pl, InventoryHolder owner, InventoryType type, String name) {
		this(pl, owner, type.getDefaultSize(), name);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) { event.setCancelled(true); return; }
		if(inv.getViewers().get(0).getName().equals(this.inv.getViewers().get(0).getName()))
			event.setCancelled(onClick(event, (Player) event.getWhoClicked(), event.getCurrentItem()));
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if(inv.getViewers().get(0).getName().equals(this.inv.getViewers().get(0).getName())) dealloc();
	}
	
	public Inventory getInventory() { return inv; }
	protected void dealloc() { HandlerList.unregisterAll(this); }
  	protected abstract boolean onClick(InventoryClickEvent event, Player p, ItemStack item);
}
