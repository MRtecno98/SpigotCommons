package com.spigot.libraries.config;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ItemSection extends MappedAbstractionSection {
	public static final String MATERIAL_KEY = "type";
	public static final String AMOUNT_KEY = "amount";
	public static final String DAMAGE_KEY = "damage";
	public static final String META_KEY = "metadata";
	
	private ItemStack it;
	
	public ItemSection(ConfigurationSection sect) {
		super(sect);
	}
	
	@Override
	public void registerKeys(Object... data) {
		this.it = new ItemStack(Material.AIR);
		setKeys(new String[] { 
				MATERIAL_KEY, 
				AMOUNT_KEY, 
				DAMAGE_KEY, 
				META_KEY
		});
	}
	
	public void onKeywordSet(String key, Object value) {
		String valuestr = String.valueOf(value);
		
		switch(key) {
		case MATERIAL_KEY :
			Material mt = Material.getMaterial(valuestr);
			if(mt == null) throw new RuntimeException("Cannot match type \"" + valuestr + "\"");
			getItemReference().setType(mt);
			break;
			
		case AMOUNT_KEY :
			if(!valuestr.chars().allMatch( Character::isDigit )) throw new RuntimeException("Cannot parse number \"" + valuestr + "\"");
			getItemReference().setAmount(Integer.parseInt(valuestr));
			break;
			
		case DAMAGE_KEY :
			if(!valuestr.chars().allMatch( Character::isDigit )) throw new RuntimeException("Cannot parse number \"" + valuestr + "\"");
			getItemReference().setDurability(Short.parseShort(valuestr));
			break;
			
		case META_KEY :
			if(!(value instanceof ConfigurationSection)) throw new RuntimeException("Cannot cast " + valuestr + " (" + value.getClass().getName() + ") to ItemMeta map");
			ItemMetaSection metasect = new ItemMetaSection((ConfigurationSection) value, getItemReference().getType());
			it.setItemMeta(metasect.getMetaReference());
			break;
		}
	}
	
	protected ItemStack getItemReference() {
		return it;
	}
	
	public ItemStack getItem() {
		return new ItemStack(getItemReference());
	}
}
