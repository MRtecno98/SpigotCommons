package org.spigot.commons.gui.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.spigot.commons.gui.inventory.CartesianInventory;
import org.spigot.commons.gui.inventory.CartesianPlane;
import org.spigot.commons.gui.inventory.Vector;
import org.spigot.commons.util.delegator.DelegatorList;

public class LayerPlane extends DelegatorList<ComponentLayer> implements CartesianPlane<GUIComponent>, List<ComponentLayer>, GUIComponent {
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
	
	@Override
	public void draw(CartesianInventory inventory, Vector loc, DisplayContext context) {
		for(ComponentLayer layer : this) {
			// Translate by zero. In the default LayerPane
			// all layers are drawn relative to the plane's origin
			layer.draw(inventory, loc.add(Vector.ZERO), context);
			inventory.getClickMap().finalizeLayer();
		}
	}

	@Override
	public boolean callback(ComponentInteraction inter, DisplayContext context) {
		final int slot = inter.getLocation().toSlot();
		List<Set<Integer>> clickLayers = inter.getInventory().getClickMap().getMapLayers();
		
		if(clickLayers.size() != size())
			throw new IllegalStateException("Tried to callback on uncompliant inventory");
		
		for(int i = clickLayers.size() - 1; i >= 0; i--)
			if(clickLayers.get(i).contains(slot))
				// If we had translated the LayerPane here we should
				// undo that translation with a vector subtraction
				return get(i).callback(inter, context);
			
		return false;
	}
}
