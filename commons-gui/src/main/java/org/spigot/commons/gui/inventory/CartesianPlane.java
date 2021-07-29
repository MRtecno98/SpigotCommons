package org.spigot.commons.gui.inventory;

public interface CartesianPlane<T> {
	public void setItem(int x, int y, T item);
	
	public T getItem(int x, int y);
	
	public void clearSlot(int x, int y);
	
	public default void setItem(CartesianLocation loc, T item) {
		setItem(loc.getX(), loc.getY(), item);
	}
	
	public default T getItem(CartesianLocation loc) {
		return getItem(loc.getX(), loc.getY());
	}
	
	public default void clearSlot(CartesianLocation loc) {
		clearSlot(loc.getX(), loc.getY());
	}
}
