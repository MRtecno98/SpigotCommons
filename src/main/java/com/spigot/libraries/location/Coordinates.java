package com.spigot.libraries.location;

public interface Coordinates {
	public Location getLocation();
	
	public default boolean equals(Coordinates other) {
		return other.getLocation().equals(getLocation());
	}
}
