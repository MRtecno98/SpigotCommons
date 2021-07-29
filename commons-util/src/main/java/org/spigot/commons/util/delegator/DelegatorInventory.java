package org.spigot.commons.util.delegator;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DelegatorInventory implements Inventory {
	private Inventory delegate;

	public int getSize() {
		return getDelegate().getSize();
	}

	public int getMaxStackSize() {
		return getDelegate().getMaxStackSize();
	}

	public void setMaxStackSize(int size) {
		getDelegate().setMaxStackSize(size);
	}

	public String getName() {
		return getDelegate().getName();
	}

	public ItemStack getItem(int index) {
		return getDelegate().getItem(index);
	}

	public void setItem(int index, ItemStack item) {
		getDelegate().setItem(index, item);
	}

	public HashMap<Integer, ItemStack> addItem(ItemStack... items) throws IllegalArgumentException {
		return getDelegate().addItem(items);
	}

	public HashMap<Integer, ItemStack> removeItem(ItemStack... items) throws IllegalArgumentException {
		return getDelegate().removeItem(items);
	}

	public ItemStack[] getContents() {
		return getDelegate().getContents();
	}

	public void setContents(ItemStack[] items) throws IllegalArgumentException {
		getDelegate().setContents(items);
	}

	public ItemStack[] getStorageContents() {
		return getDelegate().getStorageContents();
	}

	public void setStorageContents(ItemStack[] items) throws IllegalArgumentException {
		getDelegate().setStorageContents(items);
	}
	
	@Deprecated
	public boolean contains(int materialId) {
		return getDelegate().contains(materialId);
	}

	public boolean contains(Material material) throws IllegalArgumentException {
		return getDelegate().contains(material);
	}

	public boolean contains(ItemStack item) {
		return getDelegate().contains(item);
	}
	
	@Deprecated
	public boolean contains(int materialId, int amount) {
		return getDelegate().contains(materialId, amount);
	}

	public boolean contains(Material material, int amount) throws IllegalArgumentException {
		return getDelegate().contains(material, amount);
	}

	public boolean contains(ItemStack item, int amount) {
		return getDelegate().contains(item, amount);
	}

	public boolean containsAtLeast(ItemStack item, int amount) {
		return getDelegate().containsAtLeast(item, amount);
	}
	
	@Deprecated
	public HashMap<Integer, ? extends ItemStack> all(int materialId) {
		return getDelegate().all(materialId);
	}

	public HashMap<Integer, ? extends ItemStack> all(Material material) throws IllegalArgumentException {
		return getDelegate().all(material);
	}

	public HashMap<Integer, ? extends ItemStack> all(ItemStack item) {
		return getDelegate().all(item);
	}
	
	@Deprecated
	public int first(int materialId) {
		return getDelegate().first(materialId);
	}

	public int first(Material material) throws IllegalArgumentException {
		return getDelegate().first(material);
	}

	public int first(ItemStack item) {
		return getDelegate().first(item);
	}

	public int firstEmpty() {
		return getDelegate().firstEmpty();
	}
	
	@Deprecated
	public void remove(int materialId) {
		getDelegate().remove(materialId);
	}

	public void remove(Material material) throws IllegalArgumentException {
		getDelegate().remove(material);
	}

	public void remove(ItemStack item) {
		getDelegate().remove(item);
	}

	public void clear(int index) {
		getDelegate().clear(index);
	}

	public void clear() {
		getDelegate().clear();
	}

	public List<HumanEntity> getViewers() {
		return getDelegate().getViewers();
	}

	public String getTitle() {
		return getDelegate().getTitle();
	}

	public InventoryType getType() {
		return getDelegate().getType();
	}

	public InventoryHolder getHolder() {
		return getDelegate().getHolder();
	}

	public ListIterator<ItemStack> iterator() {
		return getDelegate().iterator();
	}

	public ListIterator<ItemStack> iterator(int index) {
		return getDelegate().iterator(index);
	}

	public Location getLocation() {
		return getDelegate().getLocation();
	}
}
