package com.spigot.libraries.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;

public class AbstractionSection extends MemorySection {
	public AbstractionSection(ConfigurationSection sect, Object... preCloningData) {
		super(sect.getParent(), sect.getCurrentPath());
		preCloning(preCloningData);
		for(String k : sect.getKeys(false)) set(k, sect.get(k));
	}
	
	@Override
	public void set(String key, Object value) {
		super.set(key, value);
		onSetting(key, value);
	}
	
	public void preCloning(Object... data) {}
	public void onSetting(String key, Object value) {}
}
