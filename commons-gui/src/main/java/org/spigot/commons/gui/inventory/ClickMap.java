package org.spigot.commons.gui.inventory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.spigot.commons.util.delegator.abs.AbstractDelegatorSet;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PROTECTED)
public class ClickMap extends AbstractDelegatorSet<Integer> implements Set<Integer> {
	private int currentIndex = 0;
	
	@Getter(AccessLevel.PUBLIC)
	private List<Set<Integer>> mapLayers = new ArrayList<>();
	
	@Override
	protected Set<Integer> getDelegate() {
		if(getMapLayers().size() <= getCurrentIndex())
			getMapLayers().add(new HashSet<>());
		
		return getMapLayers().get(getCurrentIndex());
	}
	
	public void finalizeLayer() {
		currentIndex++;
	}
}
