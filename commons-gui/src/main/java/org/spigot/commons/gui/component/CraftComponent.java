package org.spigot.commons.gui.component;

import org.bukkit.inventory.ItemStack;
import org.spigot.commons.gui.inventory.CartesianInventory;
import org.spigot.commons.gui.inventory.Vector;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class CraftComponent implements GUIComponent {
	private ItemStack item;
	
	@Override
	public void draw(CartesianInventory inv, Vector loc, DisplayContext context) {
		inv.setItem(loc.getX(), loc.getY(), getItem());
	}
}
