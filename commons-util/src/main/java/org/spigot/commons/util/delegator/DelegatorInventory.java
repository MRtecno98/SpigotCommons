package org.spigot.commons.util.delegator;

import org.bukkit.inventory.Inventory;
import org.spigot.commons.util.delegator.abs.AbstractDelegatorInventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DelegatorInventory extends AbstractDelegatorInventory {
	private Inventory delegate;
}
