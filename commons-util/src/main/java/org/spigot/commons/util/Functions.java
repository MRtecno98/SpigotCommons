package org.spigot.commons.util;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class Functions {
	public static <T> Function<T, Void> voidFunction(Consumer<T> consumer) {
		return t -> {
			consumer.accept(t);
			return null;
		};
	}

	public static <X, Y> BiFunction<X, Y, Void> voidBiFunction(BiConsumer<X, Y> consumer) {
		return (x, y) -> {
			consumer.accept(x, y);
			return null;
		};
	}
}
