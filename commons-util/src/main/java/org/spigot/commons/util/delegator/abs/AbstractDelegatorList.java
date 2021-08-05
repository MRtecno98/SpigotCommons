package org.spigot.commons.util.delegator.abs;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import lombok.Getter;

@Getter
public abstract class AbstractDelegatorList<T> extends AbstractDelegatorCollection<T> implements List<T> {	
	
	@Override
	protected abstract List<T> getDelegate();
	
	public boolean addAll(int index, Collection<? extends T> c) {
		return getDelegate().addAll(index, c);
	}

	public T get(int index) {
		return getDelegate().get(index);
	}

	public T set(int index, T element) {
		return getDelegate().set(index, element);
	}

	public void add(int index, T element) {
		getDelegate().add(index, element);
	}

	public T remove(int index) {
		return getDelegate().remove(index);
	}

	public int indexOf(Object o) {
		return getDelegate().indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return getDelegate().lastIndexOf(o);
	}

	public ListIterator<T> listIterator() {
		return getDelegate().listIterator();
	}

	public ListIterator<T> listIterator(int index) {
		return getDelegate().listIterator(index);
	}

	public List<T> subList(int fromIndex, int toIndex) {
		return getDelegate().subList(fromIndex, toIndex);
	}
}
