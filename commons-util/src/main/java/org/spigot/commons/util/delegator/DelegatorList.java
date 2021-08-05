package org.spigot.commons.util.delegator;

import java.util.List;

import org.spigot.commons.util.delegator.abs.AbstractDelegatorList;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DelegatorList<T> extends AbstractDelegatorList<T> {
	private List<T> delegate;
}
