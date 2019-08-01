package com.spigot.libraries.localization;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;

public class YamlSupport extends FileSupport {
	public YamlConfiguration yaml;
	
	public YamlSupport(File path, String locale, InputStream defaultResource) {
		super(path, locale, defaultResource);
		
		this.yaml = YamlConfiguration.loadConfiguration(path);
	}
	
	public YamlSupport(File path, String locale) {
		this(path, locale, null);
	}
	
	public void reloadSupport() {
		yaml = new YamlConfiguration();
		try {
			yaml.load(getFile());
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getLocalizedText(String key) {
		return yaml.getString(key).replace('&', ChatColor.COLOR_CHAR);
	}
}
