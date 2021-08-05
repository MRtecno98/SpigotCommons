package org.spigot.commons.tests.gui;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;
import org.spigot.commons.gui.InventoryGUI;
import org.spigot.commons.gui.component.ComponentInteraction;
import org.spigot.commons.gui.component.CraftComponent;
import org.spigot.commons.gui.component.DisplayContext;
import org.spigot.commons.gui.inventory.Vector;
import org.spigot.commons.tests.BukkitTests;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.entity.PlayerMock;

public class InteractionTests extends BukkitTests {
	@Test
	public void inventoryClickTest() {
		final Vector loc = new Vector(3, 2);
		final ItemStack it = new ItemStack(Material.STONE, 8);
		final boolean[] flag = { false };
		
		InventoryGUI gui = new InventoryGUI("Click GUI", 27);
		gui.getItems().setItem(loc, new CraftComponent(it) {
			@Override
			public boolean callback(ComponentInteraction inter, DisplayContext context) {
				return flag[0] = true;
			}
		});
		
		gui.register(MockBukkit.createMockPlugin());
		
		InventoryView view = gui.show(server.addPlayer());
		
		InventoryClickEvent event = new InventoryClickEvent(view, SlotType.CONTAINER, 
				loc.toSlot(), ClickType.LEFT, InventoryAction.PICKUP_ALL);
		server.getPluginManager().callEvent(event);

		System.out.printf("Flag: %s\n", String.valueOf(flag[0]));
		System.out.printf("Cancelled: %s\n", String.valueOf(event.isCancelled()));
		
		assertTrue(flag[0]);
		assertTrue(event.isCancelled());
		
		System.out.println();
	}
	
	@Test
	public void inventorySelectionTest() {
		final Vector loc = new Vector(3, 2);
		final ItemStack it = new ItemStack(Material.STONE, 8);
		
		InventoryGUI gui = new InventoryGUI("Unclicked GUI", 27);
		gui.getItems().setItem(loc, new CraftComponent(it) {
			@Override
			public boolean callback(ComponentInteraction inter, DisplayContext context) {
				fail("Executed callback even though we didn't click it");
				return true;
			}
		});
		
		gui.register(MockBukkit.createMockPlugin());
		
		PlayerMock p = server.addPlayer();
		InventoryView view = p.openInventory(Bukkit.createInventory(p, 27));
		
		InventoryClickEvent event = new InventoryClickEvent(view, SlotType.CONTAINER, 
				loc.toSlot(), ClickType.LEFT, InventoryAction.PICKUP_ALL);
		server.getPluginManager().callEvent(event);
	}
}
