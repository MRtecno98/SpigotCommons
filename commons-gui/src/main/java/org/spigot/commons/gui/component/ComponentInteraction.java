package org.spigot.commons.gui.component;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.spigot.commons.gui.inventory.CartesianInventory;
import org.spigot.commons.gui.inventory.Vector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Wither;

// @With appears not to be working
@Wither
@Getter
@AllArgsConstructor
@SuppressWarnings("deprecation")
public class ComponentInteraction {
	private CartesianInventory inventory;
	private InventoryView view;
	private ClickType clickType;
	private Vector location;
	private ItemStack item;
}
