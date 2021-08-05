package org.spigot.commons.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigot.commons.gui.component.ComponentInteraction;
import org.spigot.commons.gui.component.DisplayContext;
import org.spigot.commons.gui.component.LayerPlane;
import org.spigot.commons.gui.inventory.CraftCartesianInventory;
import org.spigot.commons.gui.inventory.Vector;

import lombok.Getter;
import lombok.Setter;

@Getter
public class InventoryGUI {
	@Setter
	private String title;
	private final int size;
	
	private LayerPlane items = new LayerPlane();
	private final GUIListener listener = new GUIListener();

	public InventoryGUI(String title, int size) {
		this.title = title;
		this.size = size;
	}
	
	public InventoryGUI(int size) {
		this("Untitled", size);
	}
	
	public Inventory build(DisplayContext context) {
		CraftCartesianInventory inv = new GUIInventory(
				Bukkit.createInventory(context.getHolder(), getSize(), getTitle()));
		getItems().draw(inv, Vector.ZERO, context);
		
		return inv;
	}
	
	public InventoryView show(Player p) {
		return show(p, DisplayContext.buildDefaultFor(p));
	}
	
	public InventoryView show(Player p, DisplayContext context) {
		return p.openInventory(build(context));
	}
	
	public void register(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(getListener(), plugin);
	}
	
	public void unregister(JavaPlugin plugin) {
		HandlerList.unregisterAll(getListener());
	}
	
	public class GUIListener implements Listener {
		@EventHandler
		public void onClick(InventoryClickEvent event) {
			if(event.getInventory() instanceof GUIInventory) {
				// This inventory is an IGUI registered by 
				// some instance of this class
				GUIInventory inv = (GUIInventory) event.getInventory();
				
				if(inv.getParent().equals(InventoryGUI.this)) {
					// This inventory is an IGUI registered by
					// THIS specific instance of this class, now we
					// can trigger callbacks
					
					ComponentInteraction inter = new ComponentInteraction(inv, event.getView(), 
							event.getClick(), Vector.fromSlot(event.getSlot()), event.getCurrentItem());
					
					// Provide a context if the Human is a Player
					DisplayContext context;
					if(event.getWhoClicked() instanceof Player)
						context = DisplayContext.buildDefaultFor((Player) event.getWhoClicked());
					else context = DisplayContext.NULL_CONTEXT;
					
					// Calling callback and ORing cancel status
					event.setCancelled(event.isCancelled() || 
							InventoryGUI.this.getItems().callback(inter, context));
				}
			}
		}
	}
	
	public class GUIInventory extends CraftCartesianInventory {
		public GUIInventory(Inventory delegate) {
			super(delegate);
		}
		
		public InventoryGUI getParent() {
			return InventoryGUI.this;
		}
	}
}
