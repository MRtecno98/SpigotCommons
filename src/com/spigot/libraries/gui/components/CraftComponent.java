package com.spigot.libraries.gui.components;

import java.util.List;
import java.util.concurrent.Callable;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftComponent extends ItemStack {
	private Callable<Boolean> action;

	public CraftComponent(ItemStack stack, Callable<Boolean> action) throws IllegalArgumentException {
		super(stack);
		this.action = action;
	}

	public CraftComponent(Material type, int amount, short damage, Callable<Boolean> action) {
		this(new ItemStack(type, amount, damage), action);
	}

	public CraftComponent(Material type, int amount, Callable<Boolean> action) {
		this(new ItemStack(type, amount), action);
	}

	public CraftComponent(Material type, Callable<Boolean> action) {
		this(new ItemStack(type), action);
	}

	public CraftComponent setName(String name) { 
		ItemMeta meta = getItemMeta();
			meta.setDisplayName(name);
		setItemMeta(meta);
		return this; 
	}
	
	public CraftComponent addFlags(ItemFlag... flags) {
		ItemMeta meta = getItemMeta();
			meta.addItemFlags(flags);
		setItemMeta(meta);
		return this;
	}
	
	public CraftComponent addLore(String... lines) { 
		ItemMeta meta = getItemMeta();
			List<String> lore = meta.getLore();
				for(String line : lines) lore.add(line);
			meta.setLore(lore);
		setItemMeta(meta);
		return this; 
	}
	
	public CraftComponent setMaterial(Material mt) { 
		setType(mt);
		return this; 
	}
	
	public CraftComponent setAction(Callable<Boolean> action) {
		this.action = action;
		return this;
	}
	
	public CraftComponent setMeta(short meta) { 
		setDurability(meta);
		return this; 
	}
	
	public CraftComponent setQuantity(int quantity) {
		setAmount(quantity);
		return this;
	}
	
	public Callable<Boolean> getAction() {
		return this.action;
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
	
	public boolean execute() throws Exception {
		return getAction().call().booleanValue();
	}
}
