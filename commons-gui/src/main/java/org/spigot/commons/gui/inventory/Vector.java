package org.spigot.commons.gui.inventory;

import lombok.Value;

@Value
public class Vector {
	public static final int DEFAULT_ROW_SIZE = 9;
	
	public static final Vector ONE = Vector.of(1, 1);
	public static final Vector ZERO = Vector.of(0, 0);
	
	public static final Vector UNIT_X = Vector.of(1, 0);
	public static final Vector UNIT_Y = Vector.of(0, 1);
	
	private final int x, y;
	
	public boolean equals(int x, int y) {
		return getX() == x && getY() == y;
	}
	
	public int toSlot(int rowSize) {
		return getY()*rowSize + getX();
	}
	
	public int toSlot() {
		return toSlot(DEFAULT_ROW_SIZE);
	}
	
	public Vector add(Vector other) {
		return Vector.of(getX() + other.getX(), getY() + other.getY());
	}
	
	public Vector mul(Vector other) {
		return Vector.of(getX() * other.getX(), getY() * other.getY());
	}
	
	public Vector div(Vector other) {
		return Vector.of(getX() / other.getX(), getY() / other.getY());
	}
	
	public Vector mul(int v) {
		return Vector.of(getX() * v, getY() * v);
	}
	
	public Vector neg() {
		return mul(-1);
	}
	
	public Vector sub(Vector other) {
		return add(other.neg());
	}
	
	public static Vector of(int x, int y) {
		return new Vector(x, y);
	}
	
	public static Vector fromSlot(int slot, int rowSize) {
		return of(slot % rowSize, slot / rowSize);
	}
	
	public static Vector fromSlot(int slot) {
		return fromSlot(DEFAULT_ROW_SIZE);
	}
}
