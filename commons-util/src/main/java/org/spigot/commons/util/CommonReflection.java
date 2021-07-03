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

/**
 * Various common utils for reflection in a bukkit environment
 * 
 * @author MRtecno98
 * @since 2.0.0
 */
@SuppressWarnings("unchecked")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonReflection {
	/**
	 * Accesses the value of a private reflection field, then resets its access level
	 * 
	 * @param object the instance to access the field on, to access static fields use <code>null</code>
	 * @param field the name of the field to access
	 * @return The field's value
	 * 
	 * @throws NoSuchFieldException see {@link Field#get(Object)}
	 * @throws IllegalAccessException see {@link Field#get(Object)}
	 */
	public static <T> T getPrivateField(Object object, String field)
			throws NoSuchFieldException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		Field objectField = clazz.getDeclaredField(field);

		boolean wasAccessible = objectField.isAccessible();

		objectField.setAccessible(true);
		Object result = objectField.get(object);
		objectField.setAccessible(wasAccessible);

		return (T) result;
	}
	
	/**
	 * Instantiates a class using a private or otherwise inaccessible constructor using reflection,
	 * then resets the access level.
	 * 
	 * @param clazz the class to instantiate
	 * @param types target constructor's arguments class types
	 * @param args arguments to pass to the target constructor, must be of the same type 
	 * 			   and in the same order of the types passed previously
	 * @return A new instance of the provided class
	 * 
	 * @throws SecurityException see {@link Class#getDeclaredConstructor(Class...)}
	 * @throws NoSuchMethodException see {@link Class#getDeclaredConstructor(Class...)}
	 * @throws InstantiationException see {@link Constructor#newInstance(Object...)}
	 * @throws IllegalAccessException see {@link Constructor#newInstance(Object...)}
	 * @throws IllegalArgumentException see {@link Constructor#newInstance(Object...)}
	 * @throws InvocationTargetException see {@link Constructor#newInstance(Object...)}
	 */
	public static <T> T instancePrivateConstructor(Class<T> clazz, Class<?>[] types, Object... args)
			throws SecurityException, NoSuchMethodException, InstantiationException, 
			IllegalAccessException, InvocationTargetException {
		Constructor<T> constructor = clazz.getDeclaredConstructor(types);

		boolean wasAccessible = constructor.isAccessible();
		constructor.setAccessible(true);
		T result = constructor.newInstance(args);
		constructor.setAccessible(wasAccessible);
		return result;
	}
	
	/**
	 * @param clazz any class type
	 * @return A default value for the specified {@link Class}, for any non-primitive types this is <code>null</code>
	 */
	public static Object getDefaultValue(Class<?> clazz) {
		if(clazz == boolean.class) 
			   return false;
		else if(clazz == char.class)
			   return '\0';
		else if(clazz == int.class)
			   return (int) 0;
		else if(clazz == int.class)
			   return (int) 0;
		else if(clazz == long.class)
			   return (long) 0;
		else if(clazz == short.class)
			   return (short) 0;
		else if(clazz == byte.class)
			   return (byte) 0;
		else if(clazz == float.class)
			   return (float) 0;
		else if(clazz == double.class)
			   return (double) 0;
		else return null;
	}
	
	/**
	 * Accesses the {@link CommandMap} instance from the Bukkit {@link PluginManager}
	 * 
	 * @return an instance of {@link SimpleCommandMap}, the default Bukkit CommandMap implementation
	 * 
	 * @throws SecurityException see {@link #getPrivateField(Object, String)}
	 * @throws NoSuchFieldException see {@link #getPrivateField(Object, String)}
	 * @throws IllegalArgumentException see {@link #getPrivateField(Object, String)}
	 * @throws IllegalAccessException see {@link #getPrivateField(Object, String)}
	 * 
	 * @see #getPrivateField(Object, String)
	 */
	public static SimpleCommandMap getCommandMap()
			throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		return getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
	}
	
	/**
	 * Registers a Bukkit {@link Command} object to the command map, 
	 * making it available to plugins as if it was registered in the <code>plugin.yml</code>
	 * 
	 * @param plugin the plugin to link this command to
	 * @param cmd the command to register
	 * 
	 * @see #getCommandMap()
	 */
	public static void registerToCommandMap(JavaPlugin plugin, Command cmd) {
		try {
			SimpleCommandMap cmap = getCommandMap();
			cmap.register(plugin.getName(), cmd);
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructs a new instance of {@link PluginCommand} using its private constructor
	 * 
	 * @param label the label of the command to construct
	 * @param owner a plugin to which the newly created command will belong
	 * @return A new instance of PluginCommand
	 * 
	 * @see #instancePrivateConstructor(Class, Class[], Object...)
	 */
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
