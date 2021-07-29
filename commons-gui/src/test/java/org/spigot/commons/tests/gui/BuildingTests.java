package org.spigot.commons.tests.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.spigot.commons.gui.InventoryGUI;
import org.spigot.commons.gui.component.CraftComponent;
import org.spigot.commons.gui.component.DisplayContext;
import org.spigot.commons.gui.inventory.CartesianLocation;

import be.seeseemelk.mockbukkit.MockBukkit;

public class BuildingTests {
	
	@Before
	public void setup() {
		MockBukkit.mock();
	}
	
	@After
	public void teardown() {
		MockBukkit.unload();
	}
	
	@Test
	public void inventoryBuildTest() {
		final CartesianLocation loc = new CartesianLocation(4, 1);
		final ItemStack it = new ItemStack(Material.STONE, 5);
		
		InventoryGUI gui = new InventoryGUI("Test GUI", 27);
		gui.getItems().setItem(loc, new CraftComponent(it));
		
		ItemStack[] its = gui.build(DisplayContext.NULL_CONTEXT).getContents();
		for(int i = 0; i < its.length; i++) {
			if(i == loc.toSlot()) assertEquals(it, its[i]);
			else assertNull(its[i]);
			
			if(i > 0 && i % 9 == 0) System.out.println();
			System.out.print((its[i] != null ? its[i].getType().name() : "null") + "\t");
		}
	}
}
