package org.spigot.commons.util;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<A, R, E extends Throwable> extends Function<A, R> {
	@Override
	default R apply(A a) {
		try {
			return applyThrows(a);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	R applyThrows(A a) throws E;
}
