package org.spigot.commons.util;

@FunctionalInterface
public interface ThrowingConsumer<T, E extends Throwable> extends java.util.function.Consumer<T> {
	@Override
	default void accept(T t) {
		try {
			acceptThrows(t);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	void acceptThrows(T t) throws E;
}
