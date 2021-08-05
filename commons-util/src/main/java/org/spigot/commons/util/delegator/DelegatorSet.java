package org.spigot.commons.util.delegator;

import java.util.Set;

import org.spigot.commons.util.delegator.abs.AbstractDelegatorSet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DelegatorSet<T> extends AbstractDelegatorSet<T> {
	private Set<T> delegate;
}
