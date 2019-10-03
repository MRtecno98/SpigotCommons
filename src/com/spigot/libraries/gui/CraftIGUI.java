package com.spigot.libraries.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.spigot.libraries.gui.components.ComponentAction;
import com.spigot.libraries.gui.components.CraftComponent;
import com.spigot.libraries.utility.Cloneable;

public class CraftIGUI extends IGUI {
	private Map<Integer, CraftComponent> components = new HashMap<>();

	public CraftIGUI(JavaPlugin pl, InventoryHolder owner, int size, String name) {
		super(pl, owner, size, name);
	}
	
	public CraftIGUI(JavaPlugin pl, InventoryHolder owner, InventoryType type, String name) {
		this(pl, owner, type.getDefaultSize(), name);
	}
	
	public CraftIGUI(IGUI base) {
		this(base.getPlugin(), 
				base.getInventory().getHolder(), 
				base.getInventory().getSize(), 
				base.getInventory().getName());
	}
	
	public CraftIGUI(CraftIGUI base) {
		this((IGUI) base);
		for(Map.Entry<Integer, CraftComponent> entry 
				: base.getComponents().entrySet()) 
			setComponent(entry.getKey(), entry.getValue());
	}
	
	public Map<Integer, CraftComponent> addComponent(CraftComponent... cmps) {
		Map<Integer, CraftComponent> refres = new HashMap<>();
		
		for(CraftComponent cmp : cmps) {
			int slot = inv.firstEmpty();
			refres.put(slot, cmp);
			inv.setItem(slot, cmp);
		}
		
		getComponents().putAll(refres);
		return refres;
	}
	
	public void setComponent(int slot, CraftComponent component) {
		inv.setItem(slot, component);
		components.put(slot, component);
	}
	
	public Map<Integer, CraftComponent> getComponents() {
		return components;
	}
	 
	public CraftIGUI setComponents(Map<Integer, CraftComponent> components) {
		this.components = components;
		return this;
	}
	
	public CraftComponent getComponent(int i) {
		CraftComponent component = components.get(i);
		if(component != null && component.equals(inv.getItem(i))) return component;
		else throw new RuntimeException("Discrepancy between internal and external registry, did you use direct inventory set?");
	}
	
	@Override
	public CraftIGUI clone() {
		CraftIGUI clone = new CraftIGUI(this).setComponents(Cloneable.mapClone(getComponents()));
		clone.getInventory().setContents(getInventory().getContents().clone());
		return clone;
	}
	
	@Override
	public boolean onClick(InventoryClickEvent event, Player p, ItemStack item) {
		try {
			CraftComponent cmp = getComponent(event.getSlot());
			Callable<Boolean> action = cmp.getAction();
			if(action instanceof ComponentAction) ((ComponentAction) action).setIGUI(this).setPlayer(p);
			return cmp.execute();
		} catch (Exception e) {
			getPlugin().getLogger().severe("Exception during handling inventory GUI click on instance \"" + getInventory().getName() + "\"");
			e.printStackTrace();
		}
		return true;
	}

}
