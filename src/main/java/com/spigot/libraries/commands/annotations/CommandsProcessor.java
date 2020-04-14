package com.spigot.libraries.commands.annotations;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.spigot.libraries.commands.CommandFlag;
import com.spigot.libraries.exceptions.NotCompatibleMethodException;
import com.spigot.libraries.utility.ReflectionUtils;

/**
 * Processes and registers commands declared using annotation framework.<br><br>
 * 
 * Example usage:<br>
 * <pre>
 * {@code
 * 		new CommandsProcessor()
 * 	.addClass(Example.class)
 * 	.process()
 * 	.register();
 * }
 * </pre>
 * 
 * @author MRtecno98
 * @since 1.4
 * @category CommandsAnnotations
 */
public class CommandsProcessor {
	private List<Class<?>> classes;
	private Method commandExecuteMethod;
	
	private Set<com.spigot.libraries.commands.Command> processed_commands;
	
	/**
	 * Constructs a new processor.
	 */
	public CommandsProcessor() {
		try {
			this.classes = new ArrayList<>();
			this.commandExecuteMethod = com.spigot.libraries.commands.Command.class.getMethod("execute", 
					CommandSender.class, org.bukkit.command.Command.class, java.util.List.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds classes to be scanned for parsable commands.
	 * 
	 * @param classes Array of classes to be scanned.
	 * @return This processor.
	 */
	public CommandsProcessor addClass(Class<?>... classes) {
		this.classes.addAll(Arrays.asList(classes));
		return this;
	}
	
	/**
	 * Processes all commands in registered classes and stores them inside this processor.
	 * 
	 * @return This processor.
	 */
	public CommandsProcessor process() {
		Set<com.spigot.libraries.commands.Command> commands = new HashSet<>();
		
		for(Class<?> clazz : classes) {
			ClassProcessResult result = processClass(clazz);
			if(result.hasRootCommand()) commands.add(result.getRootCommand());
			if(result.hasSubcommands()) commands.addAll(result.getRegisterSubcommands());
		}
		
		this.processed_commands = commands;
		return this;
	}
	
	/**
	 * Registers all commands contained in this processor using the given {@link JavaPlugin} instance.
	 * 
	 * @param pl {@code Plugin} instance used to register the commands.
	 * @return This processor.
	 * @see com.spigot.libraries.commands.Command#register(JavaPlugin)
	 */
	public CommandsProcessor register(JavaPlugin pl) {
		for(com.spigot.libraries.commands.Command cmd : results()) cmd.register(pl);
		return this;
	}
	
	/**
	 * Gets all the commands stored in this processor.
	 * 
	 * @return A collection containing the stored commands.
	 */
	public Collection<com.spigot.libraries.commands.Command> results() {
		return processed_commands;
	}
	
	/**
	 * Processes a single class for commands, does not return a directly usable result,
	 *  it's mainly used by the {@link #process} method or subclasses.
	 * 
	 * @param clazz The class to process
	 * @return A {@link ClassProcessResult} instance containing raw processing results.
	 * @see ClassProcessResult
	 */
	public ClassProcessResult processClass(Class<?> clazz) {
		Set<com.spigot.libraries.commands.Command> registerSubCommands = new HashSet<>();
		com.spigot.libraries.commands.Command main = null;
		
		try {
			Object inst = clazz.newInstance();
			
			if(clazz.isAnnotationPresent(Command.class)) {
				main = processAnnotations(clazz, inst, clazz.getMethod("execute", 
						CommandSender.class, org.bukkit.command.Command.class, List.class));
			}
			
			for(Method m : clazz.getMethods()) {
				if(m.isAnnotationPresent(Command.class)) {
					com.spigot.libraries.commands.Command subcmd = processAnnotations(m, inst, m);
					if(main == null) registerSubCommands.add(subcmd);
					else {
						if(m.isAnnotationPresent(SubRegister.class)) registerSubCommands.add(subcmd
								.clone()
								.label(m.getAnnotation(SubRegister.class)
										.value()));
						main.registerSubcommand(subcmd);
					}
				}
			}
			
			for(Class<?> subclass : clazz.getClasses()) {
				if(subclass.isAnnotationPresent(Command.class)) {
					ClassProcessResult subresult = processClass(subclass);
					if(subclass.isAnnotationPresent(SubRegister.class)) registerSubCommands.add(subresult.getRootCommand()
							.clone()
							.label(subclass.getAnnotation(SubRegister.class)
									.value()));
					registerSubCommands.addAll(subresult.getRegisterSubcommands());
					main.registerSubcommand(subresult.getRootCommand());
				}
			}
			
		}catch(InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		return new ClassProcessResult(main, registerSubCommands);
	}
	
	/**
	 * Processes a single element of a class representing a single command, the arguments of this method aren't mean for a direct usage,
	 *  this method it's mainly used by the {@link #processClass} method or subclasses.
	 * 
	 * @param element The element to process.
	 * @param obj The object instance the element is linked to.
	 * @param action The target {@link Method} containing the action of this command.
	 * @return The command processed
	 */
	public com.spigot.libraries.commands.Command processAnnotations(AnnotatedElement element, final Object obj, final Method action) {
		if(!ReflectionUtils.checkMethodSignature(action, commandExecuteMethod)) throw new NotCompatibleMethodException("Method not compatible");
		
		String label = element.getAnnotation(Command.class).value();
		CommandFlag[] flags = null;
		String permission = null, permission_message = null;
		String[] aliases = new String[0];
			
		if(element.isAnnotationPresent(CommandFlags.class)) flags = element.getAnnotation(CommandFlags.class).value();
		if(element.isAnnotationPresent(CommandAlias.class)) aliases = element.getAnnotation(CommandAlias.class).value();
		if(element.isAnnotationPresent(Permission.class)) {
			Permission permannot = element.getAnnotation(Permission.class);
			permission = permannot.value();
			permission_message = permannot.message().equals("") ? null : permannot.message();
		}
		
		return new com.spigot.libraries.commands.Command(label, permission, permission_message, flags) {
			@Override
			public boolean execute(CommandSender sender, org.bukkit.command.Command cmd, List<String> args) {
				try {
					return ((Boolean) action.invoke(obj, sender, cmd, args)).booleanValue();
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				return false;
			}
		}.aliases(aliases);
	}
	
	/**
	 * Stores the result of a class processed by {@link com.spigot.libraries.commands.annotations.CommandsProcessor#processClass(Class)}
	 * @author MRtecno98
	 * @since 1.4
	 * @category CommandsAnnotations
	 */
	class ClassProcessResult {
		private com.spigot.libraries.commands.Command rootCommand;
		private Collection<com.spigot.libraries.commands.Command> registerSubcommands;
		
		public ClassProcessResult(com.spigot.libraries.commands.Command rootCommand,
				Collection<com.spigot.libraries.commands.Command> registerSubcommands) {
			this.rootCommand = rootCommand;
			this.registerSubcommands = registerSubcommands == null ? registerSubcommands : 
				registerSubcommands.stream()
							.filter((cmd) -> cmd != null)
							.collect(Collectors.toList());
		}

		public com.spigot.libraries.commands.Command getRootCommand() {
			return rootCommand;
		}
		
		public boolean hasRootCommand() {
			return getRootCommand() != null;
		}

		public Collection<com.spigot.libraries.commands.Command> getRegisterSubcommands() {
			return registerSubcommands;
		}
		
		public boolean hasSubcommands() {
			return registerSubcommands != null && registerSubcommands.size() > 0;
		}
	}
}
