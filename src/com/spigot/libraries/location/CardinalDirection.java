package com.spigot.libraries.location;

public enum CardinalDirection {
	NORTH("EAST", "WEST", "SOUTH", Direction.STATIC, Direction.RETROGRADE),
	NORTHEAST("NORTHWEST", "SOUTHEAST", "SOUTHWEST", Direction.PROGRADE, Direction.RETROGRADE),
	EAST("SOUTH", "NORTH", "WEST", Direction.PROGRADE, Direction.STATIC),
	SOUTHEAST("NORTHEAST", "SOUTHWEST", "NORTHWEST", Direction.PROGRADE, Direction.PROGRADE),
	SOUTH("WEST", "EAST", "NORTH", Direction.STATIC, Direction.PROGRADE),
	SOUTHWEST("SOUTHEAST", "NORTHWEST", "NORTHEAST", Direction.RETROGRADE, Direction.PROGRADE),
	WEST("NORTH", "SOUTH", "EAST", Direction.RETROGRADE, Direction.STATIC),
	NORTHWEST("SOUTHWEST", "NORTHEAST", "SOUTHEAST", Direction.RETROGRADE, Direction.RETROGRADE);
	
	String right,left,opposite;
	Direction xdir, zdir;
	
	CardinalDirection(String right, String left, String opposite, Direction xdir, Direction zdir) {
		this.right = right;
		this.left = left;
		this.opposite = opposite;
		this.xdir = xdir;
		this.zdir = zdir;
	}
	
	public CardinalDirection getRight() {
		return CardinalDirection.valueOf(right);
	}

	public CardinalDirection getLeft() {
		return CardinalDirection.valueOf(left);
	}

	public CardinalDirection getOpposite() {
		return CardinalDirection.valueOf(opposite);
	}

	public static CardinalDirection fromLocation(Location loc) {
		double rot = (loc.getYaw() - 90) % 360;
        if (rot < 0) {
            rot += 360.0;
        }
        return CardinalDirection.fromRotation(rot);
	}
	
	public Direction getXDirection() {
		return xdir;
	}

	public Direction getZDirection() {
		return zdir;
	}
	
	/**
     * Converts a rotation to a cardinal direction name.
     * 
     * @param rot
     * @return
     */
    private static CardinalDirection fromRotation(double rot) {
        if (0 <= rot && rot < 22.5) {
            return CardinalDirection.WEST;
        } else if (22.5 <= rot && rot < 67.5) {
            return CardinalDirection.NORTHWEST;
        } else if (67.5 <= rot && rot < 112.5) {
            return CardinalDirection.NORTH;
        } else if (112.5 <= rot && rot < 157.5) {
            return CardinalDirection.NORTHEAST;
        } else if (157.5 <= rot && rot < 202.5) {
            return CardinalDirection.EAST;
        } else if (202.5 <= rot && rot < 247.5) {
            return CardinalDirection.SOUTHEAST;
        } else if (247.5 <= rot && rot < 292.5) {
            return CardinalDirection.SOUTH;
        } else if (292.5 <= rot && rot < 337.5) {
            return CardinalDirection.SOUTHWEST;
        } else if (337.5 <= rot && rot < 360.0) {
            return CardinalDirection.WEST;
        } else {
            return null;
        }
    }
}