package com.spigot.libraries.enchs;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.enchantments.Enchantment;

public class EnchantmentNameMapper {
	private static final Map<Enchantment, String> ENCHANTS = new HashMap<>();
	
	static {
		ENCHANTS.put(Enchantment.DAMAGE_ALL, "sharpness");
		ENCHANTS.put(Enchantment.DAMAGE_ARTHROPODS, "bane_of_arthropods");
		ENCHANTS.put(Enchantment.DAMAGE_UNDEAD, "smite");
		ENCHANTS.put(Enchantment.KNOCKBACK, "knockback");
		ENCHANTS.put(Enchantment.FIRE_ASPECT, "fire_aspect");
		ENCHANTS.put(Enchantment.LOOT_BONUS_MOBS, "looting");
		
		ENCHANTS.put(Enchantment.PROTECTION_ENVIRONMENTAL, "protection");
		ENCHANTS.put(Enchantment.PROTECTION_FIRE, "fire_protection");
		ENCHANTS.put(Enchantment.PROTECTION_FALL, "feather_falling");
		ENCHANTS.put(Enchantment.PROTECTION_EXPLOSIONS, "blast_protection");
		ENCHANTS.put(Enchantment.PROTECTION_PROJECTILE, "projectile_protection");
		ENCHANTS.put(Enchantment.OXYGEN, "respiration");
		ENCHANTS.put(Enchantment.WATER_WORKER, "aqua_affinity");
		ENCHANTS.put(Enchantment.THORNS, "thorns");
		
		ENCHANTS.put(Enchantment.DIG_SPEED, "efficency");
		ENCHANTS.put(Enchantment.SILK_TOUCH, "silk_touch");
		ENCHANTS.put(Enchantment.DURABILITY, "unbreaking");
		ENCHANTS.put(Enchantment.LOOT_BONUS_BLOCKS, "fortune");
		
		ENCHANTS.put(Enchantment.ARROW_DAMAGE, "power");
		ENCHANTS.put(Enchantment.ARROW_KNOCKBACK, "punch");
		ENCHANTS.put(Enchantment.ARROW_FIRE, "flame");
		ENCHANTS.put(Enchantment.ARROW_INFINITE, "infinity");
		
		ENCHANTS.put(Enchantment.LUCK, "luck_of_the_sea");
		ENCHANTS.put(Enchantment.LURE, "lure");
	}
	
	public static String getQualifiedName(Enchantment ench) {
		String n = ENCHANTS.get(ench);
		return n != null ? n : ench.getName();
	}
	
	public static Enchantment getFromQualifiedName(String qname) {
		for(Map.Entry<Enchantment, String> entry : ENCHANTS.entrySet()) if(entry.getValue().equals(qname)) return entry.getKey();
		return null;
	}
	
	private EnchantmentNameMapper() {}
}
