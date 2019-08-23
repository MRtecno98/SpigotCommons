package com.spigot.libraries.utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class ReflectionUtils {
	@SuppressWarnings("unchecked")
	public static <T> T getPrivateField(Object object, String field) throws SecurityException,
	 NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = object.getClass();
    	Field objectField = clazz.getDeclaredField(field);
   		objectField.setAccessible(true);
    	Object result = objectField.get(object);
    	objectField.setAccessible(false);
    	return (T) result;
	}
	
	public static <T> T instancePrivateConstructor(Class<T> clazz, Object... args) throws NoSuchMethodException, 
	SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return instancePrivateConstructor(clazz, Arrays.asList(args)
				.stream()
				.map((arg) -> (Class<?>) arg.getClass())
				.collect(Collectors.toList())
				.toArray(new Class<?>[0]), args);
	}
	
	public static <T> T instancePrivateConstructor(Class<T> clazz, Class<?>[] types, Object... args) throws NoSuchMethodException, 
		SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<T> constructor = clazz.getDeclaredConstructor(types);
		
		boolean wasAccessible = constructor.isAccessible();
		constructor.setAccessible(true);
		T result = constructor.newInstance(args);
		constructor.setAccessible(wasAccessible);
		return result;
	}
	
	public static boolean checkMethodSignature(Method a, Method b) {
		if(!(a.getReturnType() == b.getReturnType() &&
				a.getParameterCount() == b.getParameterCount())) return false;
		int i = 0;
		for(Parameter par : a.getParameters()) {
			System.out.println(par);
			System.out.println(b.getParameters()[i]);
			if(!b.getParameters()[i].getType().equals(par.getType())) return false;
			i++;
		}
		
		return true;
	}
	
	public static SimpleCommandMap getCommandMap() throws SecurityException, 
		NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		return getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
	}
 
	public static void unRegisterBukkitCommand(JavaPlugin pl, PluginCommand cmd) {
		try {
			SimpleCommandMap commandMap = getCommandMap();
			HashMap<String, Command> knownCommands = getPrivateField(commandMap, "knownCommands");
			
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
	
	public static void registerToCommandMap(JavaPlugin pl, Command cmd) {
		try {
			SimpleCommandMap cmap = getCommandMap();
			cmap.register(pl.getName(), cmd);
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	public static PluginCommand constructPluginCommand(String label, Plugin owner) {
		System.out.println("Constructing new plugin command [LABEL: " + label + " , OWNER: " + owner.toString() + "]");
		try {
			return instancePrivateConstructor(PluginCommand.class, 
					new Class<?>[] { String.class, Plugin.class }, 
					label, owner);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
