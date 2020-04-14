package com.spigot.libraries.utility;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class IndexedSet<T> implements Set<T> {
	private Set<T> delegate;
	
	public IndexedSet(Set<T> base) {
		this.delegate = base;
	}
	
	public int index(T entry) {
	     int result = 0;
	     for (T element : this) {
	       if (element.equals(entry)) return result;
	       result++;
	     }
	     return -1;
	}
	
	public T get(int index) {
		return stream()
			.filter((el) -> index(el) == index)
			.findAny()
			.orElse(null);
	}
	
	@Override
	public boolean add(T e) {
		return delegate.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return delegate.addAll(c);
	}

	@Override
	public void clear() {
		delegate.clear();
	}

	@Override
	public boolean contains(Object o) {
		return delegate.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return delegate.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		return delegate.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return delegate.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return delegate.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return delegate.retainAll(c);
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public Object[] toArray() {
		return delegate.toArray();
	}

	@Override
	public <K> K[] toArray(K[] a) {
		return delegate.toArray(a);
	}

}
