package org.spigot.commons.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;

public class LinkedQueue<T> extends LinkedList<T> {
	public LinkedQueue() {
	}

	public LinkedQueue(Collection<? extends T> c) {
		super(c);
	}

	public Optional<T> popIf(Predicate<T> predicate) {
		if(predicate.test(peek()))
			return Optional.of(pop());
		return Optional.empty();
	}

	public Optional<T> pollIf(Predicate<T> predicate) {
		if(predicate.test(peek()))
			return Optional.ofNullable(poll());
		return Optional.empty();
	}
}
