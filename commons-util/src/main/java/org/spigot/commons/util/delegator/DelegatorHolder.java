package org.spigot.commons.util.delegator;

import org.bukkit.inventory.InventoryHolder;
import org.spigot.commons.util.delegator.abs.AbstractDelegatorHolder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DelegatorHolder extends AbstractDelegatorHolder {
	private InventoryHolder delegate;
}
