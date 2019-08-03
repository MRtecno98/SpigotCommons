package com.spigot.libraries.utility;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import com.spigot.libraries.plugin.PluginContainer;

public final class ReflectionUtils {
	public static Object getPrivateField(Object object, String field) throws SecurityException,
	 NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		
		Class<?> clazz = object.getClass();
    	Field objectField = clazz.getDeclaredField(field);
   		objectField.setAccessible(true);
    	Object result = objectField.get(object);
    	objectField.setAccessible(false);
    	return result;
	}
 
	public static void unRegisterBukkitCommand(PluginCommand cmd) {
		JavaPlugin pl = PluginContainer.getInstance().getPlugin();
		
		try {
			Object result = getPrivateField(pl.getServer().getPluginManager(), "commandMap");
			SimpleCommandMap commandMap = (SimpleCommandMap) result;
			Object map = getPrivateField(commandMap, "knownCommands");
			@SuppressWarnings("unchecked")
			HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;
			knownCommands.remove(cmd.getName());
			for (String alias : cmd.getAliases()){
				if(knownCommands.containsKey(alias) && knownCommands.get(alias).toString().contains(pl.getName())){
					knownCommands.remove(alias);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
