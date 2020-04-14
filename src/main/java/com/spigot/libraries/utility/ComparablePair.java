package com.spigot.libraries.utility;

public class ComparablePair<V extends Comparable<V>, K> extends SimplePair<V, K> implements Pair<V, K>, Comparable<ComparablePair<V, ?>> {
	public ComparablePair(V first, K second) {
		super(first, second);
	}

	@Override
	public int compareTo(ComparablePair<V, ?> paramT) {
		return getFirst().compareTo(paramT.getFirst());
	}
}
