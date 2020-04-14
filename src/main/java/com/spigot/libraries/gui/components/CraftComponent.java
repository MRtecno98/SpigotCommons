package com.spigot.libraries.gui.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftComponent extends ComparableStack {
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
	
	public CraftComponent addLore(Collection<String> lines) {
		List<String> newlore = getLore();
			newlore.addAll(lines);
		setLore(newlore);
		return this;
	}
	
	public CraftComponent setLore(Collection<String> lines) {
		ItemMeta meta = getItemMeta();
			meta.setLore(lines.stream().collect(Collectors.toList()));
		setItemMeta(meta);
		return this;
	}
	
	public List<String> getLore() {
		return getItemMeta().getLore() != null ? getItemMeta().getLore() : new ArrayList<>();
	}
	
	public CraftComponent addLore(String... lines) { 
		addLore(Arrays.asList(lines));
		return this; 
	}
	
	public CraftComponent removeLore() {
		setLore(new ArrayList<>());
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
	public CraftComponent clone() {
		return new CraftComponent(this, getAction());
	}
	
	public boolean execute() throws Exception {
		return getAction().call().booleanValue();
	}
}
