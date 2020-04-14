package com.spigot.libraries.commands.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.spigot.libraries.commands.CommandFlag;

/**
 * Defines the flags for this command.
 * 
 * @see com.spigot.libraries.commands.Command#Command(String, String, String, CommandFlag...)
 * @author MRtecno98
 * @since 1.4
 * @category CommandsAnnotations
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface CommandFlags {
	/**
	 * @return A {@link CommandFlag} array containing the flags.
	 */
	CommandFlag[] value();
}
