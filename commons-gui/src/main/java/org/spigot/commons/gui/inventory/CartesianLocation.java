package org.spigot.commons.gui.inventory;

import lombok.Value;

@Value
public class CartesianLocation {
	public static final int DEFAULT_ROW_SIZE = 9;
	
	private int x, y;
	
	public boolean equals(int x, int y) {
		return getX() == x && getY() == y;
	}
	
	public int toSlot(int rowSize) {
		return getY()*rowSize + getX();
	}
	
	public int toSlot() {
		return toSlot(DEFAULT_ROW_SIZE);
	}
	
	public static CartesianLocation of(int x, int y) {
		return new CartesianLocation(x, y);
	}
	
	public static CartesianLocation fromSlot(int slot, int rowSize) {
		return of(slot % rowSize, slot / rowSize);
	}
	
	public static CartesianLocation fromSlot(int slot) {
		return fromSlot(DEFAULT_ROW_SIZE);
	}
}
