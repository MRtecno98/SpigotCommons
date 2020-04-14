package com.spigot.libraries.utility;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public interface ChunkIterator<T> extends Iterator<T> {
	public boolean hasNextChunk(int chunk);
	public Collection<T> nextChunk(int chunk);
	
	@Override
	public default T next() {
		return nextChunk(1).stream()
				.findFirst()
				.orElseThrow(() -> new NoSuchElementException());
	}
	
	@Override
	public default boolean hasNext() {
		return hasNextChunk(1);
	}
}
