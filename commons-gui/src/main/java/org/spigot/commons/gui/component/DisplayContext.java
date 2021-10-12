package org.spigot.commons.gui.component;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
@SuppressWarnings("deprecation")
//@With doesnt work but @Wither does wtf lombok
public class DisplayContext {
	private Player player;
	private InventoryHolder holder;
	
	public static DisplayContext buildDefaultFor(Player p) {
		return new DisplayContext(p, p);
	}
	
	public static final DisplayContext NULL_CONTEXT = new DisplayContext(null, null);
}
