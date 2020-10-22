package com.spigot.libraries.commands.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import org.bukkit.command.CommandSender;

/**
 * Marks the method or class as linked to a command.
 * <p>
 * If used on a class, it needs to have implemented an {@code execute(CommandSender, Command, List<String>)} method.<br>
 * If used on a method, the method needs to have three and only three arguments of type {@link CommandSender}, {@link org.bukkit.command.Command} and {@link List}
 * </p>
 * 
 * @author MRtecno98
 * @since 1.4
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface Command {
	/**
	 * Retrieves the label of the command linked by this annotation
	 * @return The label of the command.
	 */
	String value();
}
