package org.spigot.commons.util.delegator.abs;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import lombok.Getter;

@Getter
public abstract class AbstractDelegatorSet<T> implements Set<T> {
	
	protected abstract Set<T> getDelegate();
	
	@Override
	public int size() {
		return getDelegate().size();
	}

	@Override
	public boolean isEmpty() {
		return getDelegate().isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return getDelegate().contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return getDelegate().iterator();
	}

	@Override
	public Object[] toArray() {
		return getDelegate().toArray();
	}

	@Override
	public <E> E[] toArray(E[] a) {
		return getDelegate().toArray(a);
	}

	@Override
	public boolean add(T e) {
		return getDelegate().add(e);
	}

	@Override
	public boolean remove(Object o) {
		return getDelegate().remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return getDelegate().containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return getDelegate().addAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return getDelegate().retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return getDelegate().removeAll(c);
	}

	@Override
	public void clear() {
		getDelegate().clear();
	}

	@Override
	public boolean equals(Object o) {
		return getDelegate().equals(o);
	}

	@Override
	public int hashCode() {
		return getDelegate().hashCode();
	}
}
