package com.spigot.libraries.config;

import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Mapped section representing an {@link ItemMeta} object. Can be used for building items from config, as done by the {@link ItemSection} class.
 * 
 * @author MRtecno98
 * @see <a href="https://github.com/MRtecno98/SpigotCommons/blob/master/README.md">Example usages</a>
 */
public class ItemMetaSection extends MappedAbstractionSection {
	/** Item name configuration key */
	public static final String NAME_KEY = "name";
	
	/** Item lore configuration key, must contain a string list */
	public static final String LORE_KEY = "lore";
	
	/** Section key containing any enchantments, format must conform to {@link EnchantmentSection}'s */
	public static final String ENCHANTS_KEY = "enchants";
	
	/** Flags string list configuration key, all strings must be valid {@link ItemFlag ItemFlags} */
	public static final String FLAGS_KEY = "flags";
	
	/** Boolean value, determines if the item is unbreakable or not */
	public static final String UNBREAKABLE_KEY = "unbreakable";
	
	private ItemMeta meta;
	
	/**
	 * Constructs and clones the configuration section and builds the ItemMeta.
	 * 
	 * @param sect The {@link ConfigurationSection} containing the metadata.
	 * @param itemMat The material type of the ItemMeta.
	 */
	public ItemMetaSection(ConfigurationSection sect, Material itemMat) {
		super(sect, itemMat);
	}
	
	@Override
	public void preCloning(Object... data) {
		super.preCloning(data);
		this.meta = Bukkit.getServer().getItemFactory().getItemMeta((Material) data[0]);
	}
	
	@Override
	public void registerKeys(Object... data) {
		setKeys(new String[] {
			NAME_KEY,
			LORE_KEY,
			ENCHANTS_KEY,
			FLAGS_KEY,
			UNBREAKABLE_KEY
		});
	}

	
	@Override
	public void onKeywordSet(String key, Object value) {
		String valuestr = String.valueOf(value);
		
		boolean check;
		switch(key) {
		case NAME_KEY :
			getMetaReference().setDisplayName(valuestr);
			break;
			
		case LORE_KEY :
			check = true;
			if(value instanceof List) {
				@SuppressWarnings("unchecked")
				List<String> loreList = (List<String>) value;
				if(!loreList.isEmpty() && loreList.stream().allMatch(r -> r instanceof String)) {
					getMetaReference().setLore(loreList);
				}
				else check = false;
			} else check = false;
			
			if(!check) throw new RuntimeException("Cannot convert " + value.toString() + " (" + value.getClass().getName() + ") to Lore list");
			break;
			
		case ENCHANTS_KEY :
			if(!(value instanceof ConfigurationSection)) throw new RuntimeException("Cannot cast " + valuestr + " (" + value.getClass().getName() + ") to enchants map");
			EnchantmentSection enchsect = new EnchantmentSection((ConfigurationSection) value);
			
			for(Entry<Enchantment, Integer> ench : enchsect.getEnchantments().entrySet()) 
				getMetaReference().addEnchant(ench.getKey(), ench.getValue(), true);
			break;
			
		case FLAGS_KEY :
			check = true;
			if(value instanceof List) {
				@SuppressWarnings("unchecked")
				List<String> flagsStrings = (List<String>) value;
				if(!flagsStrings.isEmpty() && flagsStrings.stream().allMatch(r -> r instanceof String)) {
					for(String flagstr : flagsStrings) {
						try {
							ItemFlag flag = ItemFlag.valueOf(flagstr);
							getMetaReference().addItemFlags(flag);
						}catch(IllegalArgumentException exc) {
							throw new RuntimeException("Cannot cast " + flagstr + " (" + value.getClass().getName() + ") to Item Flag");
						}
					}
				}else check = false;
			} else check = false;
			
			if(!check) throw new RuntimeException("Cannot convert " + value.toString() + " (" + value.getClass().getName() + ") to Flags list");
			break;
			
		case UNBREAKABLE_KEY :
			getMetaReference().spigot().setUnbreakable(Boolean.parseBoolean(valuestr));
		}
	}
	
	/**
	 * @return A reference to the built ItemMeta, may be used by subclasses.
	 */
	protected ItemMeta getMetaReference() {
		return meta;
	}
	
	/**
	 * @return A copy of the built ItemMeta.
	 */
	public ItemMeta getMeta() {
		return getMetaReference().clone();
	}

}
