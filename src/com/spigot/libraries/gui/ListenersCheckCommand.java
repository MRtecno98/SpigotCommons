package com.spigot.libraries.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

import com.spigot.libraries.commands.Command;
import com.spigot.libraries.commands.CommandFlag;

public class ListenersCheckCommand extends Command {

	public ListenersCheckCommand(String label) {
		super(label, CommandFlag.ONLY_PLAYER);
	}

	@Override
	public boolean execute(CommandSender sender, org.bukkit.command.Command cmd, List<String> args) {
		Player p = (Player) sender;
		List<String> listeners = new ArrayList<String>();
		for(HandlerList l : HandlerList.getHandlerLists()) {
			for(RegisteredListener s : l.getRegisteredListeners()) {
				Listener ls = s.getListener();
				if(ls instanceof IGUI) listeners.add(ls.toString());
			}
		}
		if(listeners.size() < 1) p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "SUCCESS:" + ChatColor.RESET + ChatColor.GREEN + " GUI Listeners are OK!");
		else p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "DANGER:" + ChatColor.RESET + ChatColor.DARK_RED + " There is a problem with listeners dealloc!");
		return true;
	}
	
	public static void initializeCommand(JavaPlugin pl) { new ListenersCheckCommand("checkListeners").register(pl); }
}
