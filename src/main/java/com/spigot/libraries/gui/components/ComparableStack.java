package com.spigot.libraries.gui.components;

import org.bukkit.inventory.ItemStack;

public class ComparableStack extends ItemStack {
	public ComparableStack(ItemStack it) {
		super(it);
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof ItemStack)) return false;
		ItemStack othercmp = (ItemStack) other;
		
		return getType().equals(othercmp.getType()) && 
				getItemMeta().serialize().equals(othercmp.getItemMeta().serialize()) && 
				getAmount() == othercmp.getAmount() && 
				getDurability() == othercmp.getDurability();
	}
	
	public boolean equalsIgnoreAmount(Object other) {
		if(!(other instanceof ItemStack)) return false;
		ItemStack othercmp = (ItemStack) other;
		
		return getType().equals(othercmp.getType()) && 
				getItemMeta().serialize().equals(othercmp.getItemMeta().serialize()) && 
				// getAmount() == othercmp.getAmount() && 
				getDurability() == othercmp.getDurability();
	}
}
