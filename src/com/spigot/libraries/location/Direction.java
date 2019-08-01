package com.spigot.libraries.location;

public enum Direction {
	PROGRADE(1), RETROGRADE(-1), STATIC(0);
	
	int value;
	
	Direction(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}