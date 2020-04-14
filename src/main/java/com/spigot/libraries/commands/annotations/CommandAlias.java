package com.spigot.libraries.commands.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Used to mark aliases to an annotated command.
 * 
 * @author MRtecno98
 * @since 1.4
 * @category CommandsAnnotations
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface CommandAlias {
	/**
	 * @return A {@link String} array containing the aliases.
	 */
	String[] value();
}
