package org.spigot.commons.tests.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;
import org.spigot.commons.gui.InventoryGUI;
import org.spigot.commons.gui.InventoryGUI.GUIInventoryHolder;
import org.spigot.commons.gui.component.ComponentInteraction;
import org.spigot.commons.gui.component.CraftComponent;
import org.spigot.commons.gui.component.DisplayContext;
import org.spigot.commons.gui.inventory.CraftCartesianInventory;
import org.spigot.commons.gui.inventory.Vector;
import org.spigot.commons.tests.BukkitTests;

public class BuildingTests extends BukkitTests {
	@Test
	public void inventoryBuildTest() {
		final Vector loc = new Vector(4, 1);
		final ItemStack it = new ItemStack(Material.STONE, 5);
		
		InventoryGUI gui = new InventoryGUI("Test GUI", 27);
		gui.getItems().setItem(loc, new CraftComponent(it) {
			@Override
			public boolean callback(ComponentInteraction inter, DisplayContext context) {
				return true;
			}
		});
		
		CraftCartesianInventory inv = (CraftCartesianInventory) ((GUIInventoryHolder)
				gui.build(DisplayContext.NULL_CONTEXT).getHolder()).getLinkedInventory();
		
		ItemStack[] its = inv.getContents();
		for(int i = 0; i < its.length; i++) {
			if(i == loc.toSlot()) assertEquals(it, its[i]);
			else assertNull(its[i]);
			
			if(i > 0 && i % 9 == 0) System.out.println();
			System.out.print((its[i] != null ? its[i].getType().name() : "null") + "\t");
		}
		
		System.out.println("\n");
	}
	
	@Test
	public void inventoryClickmapTest() {
		final Vector loc = new Vector(4, 1);
		final ItemStack it = new ItemStack(Material.STONE, 5);
		
		InventoryGUI gui = new InventoryGUI("Clickmap GUI", 27);
		gui.getItems().setItem(loc, new CraftComponent(it) {
			@Override
			public boolean callback(ComponentInteraction inter, DisplayContext context) {
				return true;
			}
		});
		
		CraftCartesianInventory inv = (CraftCartesianInventory) ((GUIInventoryHolder)
				gui.build(DisplayContext.NULL_CONTEXT).getHolder()).getLinkedInventory();
		
		List<Set<Integer>> mapLayers = inv.getClickMap().getMapLayers();
		
		System.out.printf("Clickmap: %s\n", mapLayers.toString());
		assertEquals(1, mapLayers.size());
		assertEquals(1, mapLayers.get(0).size());
		assertEquals(loc.toSlot(), 
				(int) mapLayers.get(0).stream().findFirst().get());
		
		System.out.println();
	}
}
