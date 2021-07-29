package org.spigot.commons.gui.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.spigot.commons.util.delegator.DelegatorInventory;

public class CraftCartesianInventory extends DelegatorInventory implements CartesianInventory {
	public static final ItemStack AIR = new ItemStack(Material.AIR);
	
	public CraftCartesianInventory(Inventory delegate) {
		super(delegate);
	}
	
	public void setItem(int x, int y, ItemStack item) {
		setItem(slotFromCartesian(x, y), item);
	}
	
	public ItemStack getItem(int x, int y) {
		return getItem(slotFromCartesian(x, y));
	}
	
	public void clearSlot(int x, int y) {
		setItem(x, y, AIR);
	}
	
	public static int slotFromCartesian(int x, int y) {
		return y*9 + x;
	}
}
