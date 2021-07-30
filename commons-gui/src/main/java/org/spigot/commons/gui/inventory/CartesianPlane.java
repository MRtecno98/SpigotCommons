package org.spigot.commons.gui.inventory;

public interface CartesianPlane<T> {
	public void setItem(int x, int y, T item);
	
	public T getItem(int x, int y);
	
	public void clearSlot(int x, int y);
	
	public default void setItem(Vector loc, T item) {
		setItem(loc.getX(), loc.getY(), item);
	}
	
	public default T getItem(Vector loc) {
		return getItem(loc.getX(), loc.getY());
	}
	
	public default void clearSlot(Vector loc) {
		clearSlot(loc.getX(), loc.getY());
	}
}
