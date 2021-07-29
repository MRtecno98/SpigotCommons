package org.spigot.commons.util.delegator;

import java.util.Collection;
import java.util.Iterator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DelegatorCollection<T> implements Collection<T> {
	private Collection<T> delegate;

	public int size() {
		return getDelegate().size();
	}

	public boolean isEmpty() {
		return getDelegate().isEmpty();
	}

	public boolean contains(Object o) {
		return getDelegate().contains(o);
	}

	public Iterator<T> iterator() {
		return getDelegate().iterator();
	}

	public Object[] toArray() {
		return getDelegate().toArray();
	}

	public <E> E[] toArray(E[] a) {
		return getDelegate().toArray(a);
	}

	public boolean add(T e) {
		return getDelegate().add(e);
	}

	public boolean remove(Object o) {
		return getDelegate().remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return getDelegate().containsAll(c);
	}

	public boolean addAll(Collection<? extends T> c) {
		return getDelegate().addAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		return getDelegate().removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return getDelegate().retainAll(c);
	}

	public void clear() {
		getDelegate().clear();
	}

	public boolean equals(Object o) {
		return getDelegate().equals(o);
	}

	public int hashCode() {
		return getDelegate().hashCode();
	}
}
