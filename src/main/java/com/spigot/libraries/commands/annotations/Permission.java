package com.spigot.libraries.commands.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.spigot.libraries.commands.CommandFlag;

/**
 * Sets the permission needed to run this command and, optionally, the message to show in case of lack of it.
 * 
 * @see com.spigot.libraries.commands.Command#Command(String, String, String, CommandFlag...)
 * @author MRtecno98
 * @since 1.4
 * @category CommandsAnnotations
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface Permission {
	/** @return The permission node to check for. */
	String value();
	
	/** @return The message to send if the permission check fails. Defaults to null*/
	String message() default "";
}
