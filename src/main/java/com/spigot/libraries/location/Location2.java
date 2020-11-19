package com.spigot.libraries.location;

import org.bukkit.World;

import lombok.Value;

@Value
public class Location2 implements Coordinates {
	private World world;
	private double x, z;
	
	public int getBlockX() {
		return Location.locToBlock(getX());
	}
	
	public int getBlockZ() {
		return Location.locToBlock(getZ());
	}
	
	@Override
	public Location getLocation() {
		return new Location(getWorld(), x, 0, z);
	}
	
	public Location getLocation(double y) {
		return new Location(getWorld(), x, y, z);
	}
}
