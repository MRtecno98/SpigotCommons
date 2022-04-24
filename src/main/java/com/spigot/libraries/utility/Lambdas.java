package com.spigot.libraries.utility;

import java.util.function.BiFunction;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public final class Lambdas {

	public static <K, V, T> BiFunction<V, K, T> reverseBiFunction(BiFunction<K, V, T> base) {
		return new BiFunction<V, K, T>() {
			@Override
			public T apply(V t, K u) {
				return base.apply(u, t);
			}
		};
	}

}
