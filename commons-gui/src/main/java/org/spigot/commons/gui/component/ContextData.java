package org.spigot.commons.gui.component;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.spigot.commons.util.delegator.DelegatorMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContextData extends DelegatorMap<String, Object> {
	private UUID id;

	public ContextData(UUID id, Map<String, Object> map) {
		super(map);
		this.id = id;
	}

	public ContextData(UUID id, int initialCapacity) {
		super(initialCapacity);
		this.id = id;
	}

	public ContextData(UUID id, int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		this.id = id;
	}

	@Override
	public int hashCode() {
		return getDelegate().hashCode() + getId().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof ContextData
				&& ((ContextData) o).getId().equals(getId()) && getDelegate().equals(o);
	}

	public static class EmptyData extends ContextData {
		public EmptyData(UUID id) {
			super(id, Collections.emptyMap());
		}
	}
}
