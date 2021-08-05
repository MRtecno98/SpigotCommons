package org.spigot.commons.gui.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface CartesianInventory extends CartesianPlane<ItemStack>, ClickmapHolder {
	public static final ItemStack AIR = new ItemStack(Material.AIR);
	
	@Override
	public default void clearSlot(int x, int y) {
		setItem(x, y, AIR);
	}
}
