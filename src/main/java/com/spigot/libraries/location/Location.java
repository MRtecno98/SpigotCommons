package com.spigot.libraries.location;

import org.bukkit.World;

public class Location extends org.bukkit.Location implements Coordinates {
	
	public Location(World world, double x, double y, double z, float yaw, float pitch) {
		super(world, x, y, z, yaw, pitch);
	}
	
	public Location(World world, double x, double y, double z) {
		super(world, x, y, z);
	}
	
	public Location(org.bukkit.Location loc) {
		super(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}
	
	public Location setXCord(double x) {
		setX(x);
		return this;
	}
	
	public Location setYCord(double y) {
		setY(y);
		return this;
	}
	
	public Location setZCord(double z) {
		setZ(z);
		return this;
	}
	
	public Location setYawValue(float yaw) {
		setYaw(yaw);
		return this;
	}
	
	public Location setPitchValue(float pitch) {
		setPitch(pitch);
		return this;
	}
	
	public Location addX(double xadd) {
		setX(getX() + xadd);
		return this;
	}
	
	public Location addY(double yadd) {
		setY(getY() + yadd);
		return this;
	}
	
	public Location addZ(double zadd) {
		setZ(getZ() + zadd);
		return this;
	}
	
	public Location addYaw(float yawadd) {
		setYaw(getYaw() + yawadd);
		return this;
	}
	
	public Location addPitch(float pitchadd) {
		setPitch(getPitch() + pitchadd);
		return this;
	}
	
	public Location changeWorld(World w) {
		setWorld(w);
		return this;
	}
	
	public Location add2DForward(double offset) {
		addX(getCardinalDirection().getXDirection().getValue() * offset);
		addZ(getCardinalDirection().getZDirection().getValue() * offset);
		return this;
	}
	
	public Location add2DRight(double offset) {
		addX(getCardinalDirection().getRight().getXDirection().getValue() * offset);
		addZ(getCardinalDirection().getRight().getZDirection().getValue() * offset);
		return this;
	}
	
	public Location changeLocation(org.bukkit.Location loc) {
		return changeWorld(loc.getWorld())
		.setXCord(loc.getX())
		.setYCord(loc.getY())
		.setZCord(loc.getZ())
		.setYawValue(loc.getYaw())
		.setPitchValue(loc.getPitch());
	}
	
	/**
     * Get the cardinal compass direction of this location.
     * 
     * @return the {@link CardinalDirection} this location is pointing to.
     */
    public CardinalDirection getCardinalDirection() {
        return CardinalDirection.fromLocation(this);
    }
    
    @Override
	public Location clone() {
		return new Location(this);
	}

	@Override
	public Location getLocation() {
		return this;
	}
	
	@Override
	public boolean equals(Coordinates other) {
		return super.equals(other.getLocation());
	}

}
