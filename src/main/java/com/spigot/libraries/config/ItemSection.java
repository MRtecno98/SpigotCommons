package com.spigot.libraries.config;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

/**
 * Represents and processes a section containing information about an item.
 * 
 * @author MRtecno98
 * @see <a href="https://github.com/MRtecno98/SpigotCommons/blob/master/README.md">Example usages</a>
 */
public class ItemSection extends MappedAbstractionSection {
	/** Configuration key for the string name of the item {@link Material}, must be a valid Material. */
	public static final String MATERIAL_KEY = "type";
	
	/** Configuration key for the amount of items to include in the resulting ItemStack. */
	public static final String AMOUNT_KEY = "amount";
	
	/** Configuration key for the damage value of the Item. */
	public static final String DAMAGE_KEY = "damage";
	
	/** Configuration key for the {@link ItemMetaSection} representing the {@link org.bukkit.inventory.meta.ItemMeta} of this Item, must be a valid ItemMetaSection. */
	public static final String META_KEY = "metadata";
	
	private ItemStack it;
	
	/**
	 * Constructs and clones the configuration section, then builds the {@link ItemStack}.
	 * 
	 * @param sect The configuration section containing the data.
	 */
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
	
	/**
	 * @return A reference to the built {@link ItemStack} may be used by subclasses.
	 */
	protected ItemStack getItemReference() {
		return it;
	}
	
	/**
	 * @return A copy of the built {@link ItemStack}.
	 */
	public ItemStack getItem() {
		return new ItemStack(getItemReference());
	}
}
