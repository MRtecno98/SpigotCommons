package org.spigot.commons.gui.inventory;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.spigot.commons.util.delegator.DelegatorInventory;

import lombok.Getter;

@Getter
public class ClickmapInventory extends DelegatorInventory implements ClickmapHolder {
	private ClickMap clickMap = new ClickMap();
	
	public ClickmapInventory(Inventory delegate) {
		super(delegate);
	}

	@Override
	public void setItem(int index, ItemStack item) {
		super.setItem(index, item);
		
		if(item == null | item.getType() == Material.AIR)
			getClickMap().remove(index);
		else getClickMap().add(index);
	}
	
	@Override
	public void clear(int index) {
		super.clear(index);
		getClickMap().remove(index);
	}

	@Override
	public void clear() {
		super.clear();
		getClickMap().clear();
	}

	@Override
	public void setStorageContents(ItemStack[] items) throws IllegalArgumentException {
		super.setStorageContents(items);
		
		for(int i = 0; i < items.length; i++)
			if(items[i] == null | items[i].getType() == Material.AIR)
				getClickMap().remove(i);
	}

	@Override
	@Deprecated
	public void remove(int materialId) {
		// Deprecated delegate method does not communicate
		// which slot is cleared, so we can't update the ClickMap
		throw new UnsupportedOperationException();
	}
	
	@Override
	public HashMap<Integer, ItemStack> removeItem(ItemStack... items) throws IllegalArgumentException {
		// Same reason as #remove(int)
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove(Material material) throws IllegalArgumentException {
		// Same reason as #remove(int)
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove(ItemStack item) {
		// Same reason as #remove(int)
		throw new UnsupportedOperationException();
	}
}
