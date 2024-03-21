package org.spigot.commons.commands;

import org.spigot.commons.util.Functions;

import java.util.List;
import java.util.function.BiFunction;

@FunctionalInterface
public interface NodeVisitor<T> extends BiFunction<Node, ExecutionContext, T> {
	NodeVisitor<Void> EXECUTE = (x, y) -> Functions.
			<Node, ExecutionContext>voidBiFunction(CommandExecutable::execute).apply(x, y);

	NodeVisitor<List<String>> TAB_COMPLETE = CommandExecutable::tabComplete;
}
