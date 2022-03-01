package org.spigot.commons.util.delegator;

import java.util.HashMap;
import java.util.Map;

import org.spigot.commons.util.delegator.abs.AbstractDelegatorMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DelegatorMap<K, V> extends AbstractDelegatorMap<K, V> {
	private Map<K, V> delegate;

	public DelegatorMap() {
		this(new HashMap<>());
	}

	public DelegatorMap(int initialCapacity) {
		this(new HashMap<>(initialCapacity));
	}

	public DelegatorMap(int initialCapacity, float loadFactor) {
		this(new HashMap<>(initialCapacity, loadFactor));
	}
}
