package org.spigot.commons.util.delegator.abs;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class AbstractDelegatorHolder implements InventoryHolder {
	
	public abstract InventoryHolder getDelegate();
	
	@Override
	public Inventory getInventory() {
		return getDelegate().getInventory();
	}
}
