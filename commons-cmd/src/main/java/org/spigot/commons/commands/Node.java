package org.spigot.commons.commands;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
public abstract class Node implements CommandExecutable, Preconditioned {
	private final List<Node> children = new ArrayList<>();

	public <T> Optional<T> traverse(ExecutionContext context, NodeVisitor<T> visitor) {
		T result = null;

		Iterator<Node> iter = stream(context).iterator();
		while(iter.hasNext()) {
			Node next = iter.next();

			T nodeResult = visitor.apply(next, context);
			result = nodeResult == null ? result : nodeResult;

			if(context.cancelled().get()) break;
		}

		return Optional.ofNullable(result);
	}

	public Stream<Node> stream(ExecutionContext context) {
		return Stream.concat(Stream.of(this), children.stream()
				.filter(n -> n.precondition(context))
				.findFirst().stream()
				.flatMap(n -> n.stream(context)));
	}
}
