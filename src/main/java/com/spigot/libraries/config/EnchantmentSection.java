package com.spigot.libraries.config;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;

import com.spigot.libraries.enchs.EnchantmentNameMapper;

public class EnchantmentSection extends AbstractionSection {
	private Map<Enchantment, Integer> enchs;

	public EnchantmentSection(ConfigurationSection sect) {
		super(sect);
	}
	
	@Override
	public void onSetting(String key, Object value) {
		String valuestr = String.valueOf(value);
		
		Enchantment ench;
		if((ench = EnchantmentNameMapper.getFromQualifiedName(key)) == null) throw new RuntimeException("Cannot match enchantment " + key);
		if(!valuestr.chars().allMatch(Character::isDigit)) throw new RuntimeException("Cannot cast " + valuestr + " to enchant level");
		Integer level = new Integer(Integer.parseInt(valuestr));
		
		enchs.put(ench, level);
	}
	
	@Override
	public void preCloning(Object... data) {
		this.enchs = new HashMap<>();
	}
	
	public Map<Enchantment, Integer> getEnchantments() {
		return new HashMap<>(enchs);
	}
	
	protected Map<Enchantment, Integer> getEnchantmentsReference() {
		return enchs;
	}

}
