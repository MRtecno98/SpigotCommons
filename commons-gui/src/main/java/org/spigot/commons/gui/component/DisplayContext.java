package org.spigot.commons.gui.component;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

import lombok.Value;

@Value
public class DisplayContext {
	private Player player;
	private InventoryHolder holder;
	
	public static DisplayContext buildDefaultFor(Player p) {
		return new DisplayContext(p, null);
	}
	
	public static final DisplayContext NULL_CONTEXT = new DisplayContext(null, null);
}
