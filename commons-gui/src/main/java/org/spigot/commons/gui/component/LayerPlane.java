package org.spigot.commons.gui.component;

import java.util.ArrayList;
import java.util.List;

import org.spigot.commons.gui.inventory.CartesianInventory;
import org.spigot.commons.gui.inventory.Vector;
import org.spigot.commons.gui.inventory.CartesianPlane;
import org.spigot.commons.util.delegator.DelegatorList;

public class LayerPlane extends DelegatorList<ComponentLayer> implements CartesianPlane<GUIComponent>, List<ComponentLayer> {
	public LayerPlane() {
		super(new ArrayList<>());
	}

	@Override
	public void setItem(int x, int y, GUIComponent item) {
		add(new ComponentLayer(Vector.of(x, y), item));
	}

	@Override
	public GUIComponent getItem(int x, int y) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearSlot(int x, int y) {
		removeIf((layer) -> layer.getLocation().equals(x, y));
	}
	
	public void draw(CartesianInventory inventory, DisplayContext context) {
		for(ComponentLayer layer : this)
			layer.draw(inventory, context);
	}
}
