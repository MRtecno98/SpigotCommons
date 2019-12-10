package com.spigot.libraries.commands.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.spigot.libraries.commands.CommandFlag;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface CommandFlags {
	CommandFlag[] value();
}
