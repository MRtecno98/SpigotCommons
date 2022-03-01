package org.spigot.commons.gui.component;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.spigot.commons.gui.component.ContextData.EmptyData;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

@Wither
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
@SuppressWarnings("deprecation")
@FieldDefaults(makeFinal = false)
//@With doesnt work but @Wither does wtf lombok
public class DisplayContext {
	private final Player player;
	private final InventoryHolder holder;
	private ContextData contextData;

	public static DisplayContext buildDefaultFor(Player p) {
		return buildDefaultFor(p, new EmptyData(p.getUniqueId()));
	}

	public static DisplayContext buildDefaultFor(Player p, ContextData data) {
		return new DisplayContext(p, p, data);
	}
	
	public static final DisplayContext NULL_CONTEXT = new DisplayContext(null, null, null);
}
