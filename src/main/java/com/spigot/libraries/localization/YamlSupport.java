package com.spigot.libraries.localization;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.regex.Pattern;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

public class YamlSupport extends FileSupport {
	private @Getter YamlConfiguration yaml;
	
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
			getYaml().load(getFile());
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getLocalizedText(String key) {
		return Optional.ofNullable(getYaml().getString(key))
					.map((text) -> text.replace('&', ChatColor.COLOR_CHAR))
					.map((text) -> text.replaceAll(
							Pattern.quote("\\t"), String.valueOf('\t')))
					.orElse(null);
	}
}
