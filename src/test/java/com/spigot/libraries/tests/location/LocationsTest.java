package com.spigot.libraries.tests.location;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.spigot.libraries.location.Coordinates;
import com.spigot.libraries.location.Location;
import com.spigot.libraries.location.RelativeCoordinates;

public class LocationsTest {
	@Test
	public void locationsTest() {
		Coordinates set1 = new RelativeCoordinates(new Location(null, 10, 10, 10), new Location(null, 1, 0, 4));
		Coordinates set2 = new Location(new Location(null, 11, 10, 14));
		
		assertTrue(set1.equals(set2));
	}
}
