package org.spigot.commons.commands.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ TYPE })
public @interface Help {
	public static final String DEFAULT_FORMAT = "%s%s";
	
	String value();
	String format() default DEFAULT_FORMAT;
}
