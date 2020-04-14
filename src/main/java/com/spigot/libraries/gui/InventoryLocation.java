package com.spigot.libraries.gui;

import lombok.Value;

@Value
public class InventoryLocation {
	private int x, y;
	
	public int getInventorySlot() {
		return toInventorySlot(getX(), getY());
	}
	
	public static int toInventorySlot(int x, int y) {
		return (9 * y) + x;
	}
	
	public static InventoryLocation fromInventorySlot(int slot) {
		return new InventoryLocation(slot % 9, slot / 9);
	}
}
