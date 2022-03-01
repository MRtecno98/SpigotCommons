package org.spigot.commons.util.delegator.abs;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class AbstractDelegatorMap<K, V> implements Map<K, V> {

	public abstract Map<K, V> getDelegate();

	@Override
	public void clear() {
		getDelegate().clear();
	}

	@Override
	public boolean containsKey(Object arg0) {
		return getDelegate().containsKey(arg0);
	}

	@Override
	public boolean containsValue(Object arg0) {
		return getDelegate().containsValue(arg0);
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return getDelegate().entrySet();
	}

	@Override
	public V get(Object arg0) {
		return getDelegate().get(arg0);
	}

	@Override
	public boolean isEmpty() {
		return getDelegate().isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return getDelegate().keySet();
	}

	@Override
	public V put(K arg0, V arg1) {
		return getDelegate().put(arg0, arg1);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> arg0) {
		getDelegate().putAll(arg0);
	}

	@Override
	public V remove(Object arg0) {
		return getDelegate().remove(arg0);
	}

	@Override
	public int size() {
		return getDelegate().size();
	}

	@Override
	public Collection<V> values() {
		return getDelegate().values();
	}
}
