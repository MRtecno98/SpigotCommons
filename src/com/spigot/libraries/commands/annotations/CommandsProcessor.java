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

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.spigot.libraries.commands.CommandFlag;
import com.spigot.libraries.exceptions.NotCompatibleMethodException;
import com.spigot.libraries.utility.ReflectionUtils;

public class CommandsProcessor {
	private List<Class<?>> classes;
	private Method commandExecuteMethod;
	
	private Set<com.spigot.libraries.commands.Command> processed_commands;
	
	public CommandsProcessor() {
		try {
			this.classes = new ArrayList<>();
			this.commandExecuteMethod = com.spigot.libraries.commands.Command.class.getMethod("execute", 
					CommandSender.class, org.bukkit.command.Command.class, java.util.List.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public CommandsProcessor addClass(Class<?>... classes) {
		this.classes.addAll(Arrays.asList(classes));
		return this;
	}
	
	public CommandsProcessor process() {
		Set<com.spigot.libraries.commands.Command> commands = new HashSet<>();
		
		for(Class<?> clazz : classes) {
			ClassProcessResult result = processClass(clazz);
			commands.add(result.getRootCommand());
			commands.addAll(result.getRegisterSubcommands());
		}
		
		this.processed_commands = commands;
		return this;
	}
	
	public CommandsProcessor register(JavaPlugin pl) {
		for(com.spigot.libraries.commands.Command cmd : results()) cmd.register(pl);
		return this;
	}
	
	public Collection<com.spigot.libraries.commands.Command> results() {
		return processed_commands;
	}
	
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
				System.out.println("Subclass: " + subclass);
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
	
	public com.spigot.libraries.commands.Command processAnnotations(AnnotatedElement element, final Object obj, final Method action) {
		System.out.println(action);
		System.out.println(commandExecuteMethod);
		System.out.println(ReflectionUtils.checkMethodSignature(action, action));
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
	
	class ClassProcessResult {
		private com.spigot.libraries.commands.Command rootCommand;
		private Collection<com.spigot.libraries.commands.Command> registerSubcommands;
		
		public ClassProcessResult(com.spigot.libraries.commands.Command rootCommand,
				Collection<com.spigot.libraries.commands.Command> registerSubcommands) {
			this.rootCommand = rootCommand;
			this.registerSubcommands = registerSubcommands;
		}

		public com.spigot.libraries.commands.Command getRootCommand() {
			return rootCommand;
		}

		public Collection<com.spigot.libraries.commands.Command> getRegisterSubcommands() {
			return registerSubcommands;
		}
	}
}
