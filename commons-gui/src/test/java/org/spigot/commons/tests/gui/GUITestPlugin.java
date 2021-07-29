package org.spigot.commons.tests.gui;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.spigot.commons.tests.QueuePool;

public class GUITestPlugin extends JavaPlugin implements Listener {
	public GUITestPlugin() {
        super();
    }

	protected GUITestPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void inventoryClickHandler(InventoryClickEvent event) {
		QueuePool.getInstance().getQueue("invclick").offer(event.getInventory());
	}
}
