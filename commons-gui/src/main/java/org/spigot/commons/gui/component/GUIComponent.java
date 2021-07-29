package org.spigot.commons.gui.component;

import org.spigot.commons.gui.inventory.CartesianInventory;
import org.spigot.commons.gui.inventory.CartesianLocation;

public interface GUIComponent {
	public void draw(CartesianInventory inv, CartesianLocation loc, DisplayContext context);
	public boolean callback(ComponentInteraction inter, DisplayContext context);
}
