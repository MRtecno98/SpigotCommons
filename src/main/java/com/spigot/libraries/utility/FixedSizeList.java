package com.spigot.libraries.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class FixedSizeList<T> extends ArrayList<T> {
	private static final long serialVersionUID = 1542939029925246419L;

	public FixedSizeList(int paramInt) {
		super(paramInt);
	}

	public <K> K unsupported() {
		throw new UnsupportedOperationException("Unsupported operation on node list");
	}
	
	@Override
	public boolean add(T e) {
		return unsupported();
	}
	
	@Override
	public void add(int index, T e) {
		unsupported();
	}
	
	@Override
	public boolean addAll(Collection<? extends T> coll) {
		return unsupported();
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends T> coll) {
		return unsupported();
	}
	
	@Override
	public T remove(int index) {
		return unsupported();
	}
	
	@Override
	public boolean remove(Object o) {
		return unsupported();
	}
	
	@Override
	public boolean removeAll(Collection<?> coll) {
		return unsupported();
	}
	
	@Override
	public boolean removeIf(Predicate<? super T> p) {
		return unsupported();
	}
	
	@Override
	public void clear() {
		unsupported();
	}
}
