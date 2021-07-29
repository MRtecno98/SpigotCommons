package org.spigot.commons.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.spigot.commons.gui.component.DisplayContext;
import org.spigot.commons.gui.component.LayerPlane;
import org.spigot.commons.gui.inventory.CraftCartesianInventory;

import lombok.Getter;
import lombok.Setter;

@Getter
public class InventoryGUI {
	@Setter
	private String title;
	private int size;
	
	private LayerPlane items = new LayerPlane();

	public InventoryGUI(String title, int size) {
		this.title = title;
		this.size = size;
	}
	
	public InventoryGUI(int size) {
		this("Untitled", size);
	}
	
	public Inventory build(DisplayContext context) {
		CraftCartesianInventory inv = new CraftCartesianInventory(
				Bukkit.createInventory(context.getHolder(), getSize(), getTitle()));
		getItems().draw(inv, context);
		
		return inv;
	}
	
	public InventoryView show(Player p) {
		return show(p, DisplayContext.buildDefaultFor(p));
	}
	
	public InventoryView show(Player p, DisplayContext context) {
		return p.openInventory(build(context));
	}
}
