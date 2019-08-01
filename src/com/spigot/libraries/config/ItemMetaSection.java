package com.spigot.libraries.config;

import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import com.spigot.libraries.plugin.PluginContainer;

public class ItemMetaSection extends MappedAbstractionSection {
	public static final String NAME_KEY = "name";
	public static final String LORE_KEY = "lore";
	public static final String ENCHANTS_KEY = "enchants";
	public static final String FLAGS_KEY = "flags";
	public static final String UNBREAKABLE_KEY = "unbreakable";
	
	private ItemMeta meta;

	public ItemMetaSection(ConfigurationSection sect, Material itemMat) {
		super(sect, itemMat);
	}
	
	@Override
	public void preCloning(Object... data) {
		super.preCloning(data);
		this.meta = PluginContainer.getInstance().getPlugin().getServer().getItemFactory().getItemMeta((Material) data[0]);
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
	
	protected ItemMeta getMetaReference() {
		return meta;
	}
	
	public ItemMeta getMeta() {
		return getMetaReference().clone();
	}

}
