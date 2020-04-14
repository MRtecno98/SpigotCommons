package com.spigot.libraries.gui.defaults;

import java.util.Set;
import java.util.concurrent.Callable;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

import com.spigot.libraries.gui.CraftIGUI;
import com.spigot.libraries.gui.InventoryLocation;
import com.spigot.libraries.gui.components.ComponentAction;
import com.spigot.libraries.gui.components.CraftComponent;

import lombok.Getter;

public class BinaryQuestionGUI extends CraftIGUI {
	public static final InventoryLocation ACCEPT_LOC = new InventoryLocation(2, 1);
	public static final InventoryLocation INFO_LOC = new InventoryLocation(4, 1);
	public static final InventoryLocation DENY_LOC = new InventoryLocation(6, 1);
	
	private @Getter CraftComponent info, accept, deny;
	
	public BinaryQuestionGUI(JavaPlugin pl, Player p, String title) {
		super(pl, p, InventoryType.CHEST, title);
		
		info = new CraftComponent(Material.PAPER, ComponentAction.NOTHING)
				.setName(title)
				.addLore(ChatColor.RESET + "" + ChatColor.WHITE + "Reply yes or no");
		
		accept = new CraftComponent(Material.STAINED_CLAY, 1, (short) 13, ComponentAction.NOTHING)
				.setName(ChatColor.DARK_GREEN + "Accept");
		
		deny = new CraftComponent(Material.STAINED_CLAY, 1, (short) 14, ComponentAction.NOTHING)
				.setName(ChatColor.DARK_RED + "Deny");
		
		placeComponents();
	}
	
	public BinaryQuestionGUI placeComponents() {
		setComponent(ACCEPT_LOC, accept);
		setComponent(INFO_LOC, info);
		setComponent(DENY_LOC, deny);
		return this;
	}
	
	public BinaryQuestionGUI setAcceptAction(Callable<Boolean> action) {
		accept.setAction(action);
		return placeComponents();
	}
	
	public BinaryQuestionGUI setDenyAction(Callable<Boolean> action) {
		deny.setAction(action);
		return placeComponents();
	}
	
	public BinaryQuestionGUI setInfo(Set<String> lines) {
		info.setLore(lines);
		return placeComponents();
	}
}
