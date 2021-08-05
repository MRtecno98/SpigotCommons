package org.spigot.commons.util.delegator;

import java.util.Collection;

import org.spigot.commons.util.delegator.abs.AbstractDelegatorCollection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DelegatorCollection<T> extends AbstractDelegatorCollection<T> {
	private Collection<T> delegate;
}
