package org.spigot.commons.gui.component;

import org.spigot.commons.gui.inventory.CartesianInventory;
import org.spigot.commons.gui.inventory.Vector;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ComponentLayer implements GUIComponent {
	private final Vector location;
	private final GUIComponent component;

	@Override
	public void draw(CartesianInventory inv, Vector loc, DisplayContext context) {
		getComponent().draw(inv, getLocation().add(loc), context);
	}

	@Override
	public boolean callback(ComponentInteraction inter, DisplayContext context) {
		return getComponent().callback(inter.withLocation(inter.getLocation().sub(getLocation())), context);
	}
}
