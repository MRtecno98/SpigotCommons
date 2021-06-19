package org.spigot.commons.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@SuppressWarnings("unchecked")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonReflection {
	public static <T> T getPrivateField(Object object, String field)
			throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		Field objectField = clazz.getDeclaredField(field);

		boolean wasAccessible = objectField.isAccessible();

		objectField.setAccessible(true);
		Object result = objectField.get(object);
		objectField.setAccessible(wasAccessible);

		return (T) result;
	}

	public static <T> T instancePrivateConstructor(Class<T> clazz, Class<?>[] types, Object... args)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Constructor<T> constructor = clazz.getDeclaredConstructor(types);

		boolean wasAccessible = constructor.isAccessible();
		constructor.setAccessible(true);
		T result = constructor.newInstance(args);
		constructor.setAccessible(wasAccessible);
		return result;
	}

	public static Object getDefaultValue(Class<?> t) {
		if(t == boolean.class) 
			   return false;
		else if(t == char.class)
			   return '\0';
		else if(t == int.class)
			   return (int) 0;
		else if(t == int.class)
			   return (int) 0;
		else if(t == long.class)
			   return (long) 0;
		else if(t == short.class)
			   return (short) 0;
		else if(t == byte.class)
			   return (byte) 0;
		else if(t == float.class)
			   return (float) 0;
		else if(t == double.class)
			   return (double) 0;
		else return null;
	}

	public static SimpleCommandMap getCommandMap()
			throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		return getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
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
		owner.getLogger()
				.info("Constructing new plugin command [LABEL: " + label + " , OWNER: " + owner.toString() + "]");
		try {
			return instancePrivateConstructor(PluginCommand.class, new Class<?>[] { String.class, Plugin.class }, label,
					owner);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
