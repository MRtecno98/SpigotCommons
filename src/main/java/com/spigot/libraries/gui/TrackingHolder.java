package com.spigot.libraries.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class TrackingHolder implements InventoryHolder {
	private InventoryHolder wrapped;
	
	private TrackingHolder(InventoryHolder toWrap) {
		this.wrapped = toWrap;
	}
	
	@Override
	public Inventory getInventory() {
		return getWrapped().getInventory();
	}
	
	public InventoryHolder getWrapped() {
		return wrapped;
	}
	
	public static TrackingHolder track(InventoryHolder holder) {
		return new TrackingHolder(holder);
	}
	
	public static boolean isTracked(InventoryHolder holder) {
		return holder instanceof TrackingHolder;
	}
}
