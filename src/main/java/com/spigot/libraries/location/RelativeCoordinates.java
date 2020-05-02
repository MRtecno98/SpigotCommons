package com.spigot.libraries.location;

public class RelativeCoordinates implements Coordinates {
	private Location offset, center;
	
	public RelativeCoordinates(Location offset, Location center) {
		this.offset = offset;
		this.center = center;
	}
	
	public RelativeCoordinates(Location offset, int center_x, int center_y, int center_z) {
		this(offset, new Location(offset.getWorld(), center_x, center_y, center_z));
	}
	
	public RelativeCoordinates(int offset_x, int offset_y, int offset_z, Location center) {
		this(new Location(center.getWorld(), offset_x, offset_y, offset_z), center);
	}
	
	public Location getOffset() {
		return offset;
	}

	public Location getCenter() {
		return center;
	}

	@Override
	public Location getLocation() {
		return new Location(getCenter().add(getOffset()));
	}
}
