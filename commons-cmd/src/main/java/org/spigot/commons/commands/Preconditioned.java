package org.spigot.commons.commands;

public interface Preconditioned {
	default boolean precondition(ExecutionContext ctx) {
		return true;
	}
}
