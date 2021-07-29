package org.spigot.commons.gui.component;

import org.spigot.commons.gui.inventory.CartesianInventory;
import org.spigot.commons.gui.inventory.CartesianLocation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ComponentLayer {
	private final CartesianLocation location;
	private final GUIComponent component;
	
	public void draw(CartesianInventory inventory, DisplayContext context) {
		getComponent().draw(inventory, getLocation(), context);
	}
}
