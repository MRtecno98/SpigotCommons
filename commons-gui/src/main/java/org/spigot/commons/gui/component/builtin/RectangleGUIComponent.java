package org.spigot.commons.gui.component.builtin;

import org.bukkit.inventory.ItemStack;
import org.spigot.commons.gui.component.ComponentInteraction;
import org.spigot.commons.gui.component.CraftComponent;
import org.spigot.commons.gui.component.DisplayContext;
import org.spigot.commons.gui.inventory.CartesianInventory;
import org.spigot.commons.gui.inventory.Vector;

import lombok.Getter;

@Getter
public class RectangleGUIComponent extends CraftComponent {

	private Vector dimension;

	public RectangleGUIComponent(Vector dimension, ItemStack item) {
		super(item);
		this.dimension = dimension;
	}

	@Override
	public void draw(CartesianInventory inv, Vector loc, DisplayContext context) {

		Vector vertex = loc.add(getDimension());

		for (int col = loc.getX(); col < vertex.getX(); col++)
			for (int row = loc.getY(); row < vertex.getY(); row++)
				if (col == loc.getX() || col == vertex.getX() - 1 || row == loc.getY() || row == vertex.getY() - 1)
					super.draw(inv, Vector.of(col, row), context);

	}

	@Override
	public boolean callback(ComponentInteraction inter, DisplayContext context) {
		return true;
	}

}
