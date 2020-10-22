package com.spigot.libraries.config;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;

import com.spigot.libraries.enchs.EnchantmentNameMapper;

/**
 * Processes a configuration section containing a {@code name-level} mapping of various enchantments.
 * 
 * @author MRtecno98
 * @see <a href="https://github.com/MRtecno98/SpigotCommons/blob/master/README.md">Example usages</a>
 */
public class EnchantmentSection extends AbstractionSection {
	private Map<Enchantment, Integer> enchs;
	
	/**
	 * Constructs and processes a new EnchantmentSection, this starts the cloning process.
	 * 
	 * @param sect The configuration section containing the enchantments data.
	 */
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
	
	/**
	 * @return A copy of the processed enchantments map.
	 */
	public Map<Enchantment, Integer> getEnchantments() {
		return new HashMap<>(enchs);
	}
	
	/**
	 * @return A reference to the processed enchantments map, may be used by subclasses.
	 */
	protected Map<Enchantment, Integer> getEnchantmentsReference() {
		return enchs;
	}

}
