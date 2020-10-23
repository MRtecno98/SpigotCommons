package com.spigot.libraries.tests.cxml;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.powermock.api.mockito.PowerMockito;

import com.google.common.collect.Maps;
import com.spigot.libraries.cxml.parsing.CXML;

public class TestingGuiController {
	@CXML
	public JavaPlugin getPlugin() {
		JavaPlugin mockPlugin = PowerMockito.mock(JavaPlugin.class);
		
		when(mockPlugin.isEnabled()).thenReturn(true);
		
		PluginLoader mockLoader = PowerMockito.mock(PluginLoader.class);
		when(mockLoader.createRegisteredListeners(any(), any())).thenReturn(Maps.newHashMap());
		
		when(mockPlugin.getPluginLoader()).thenReturn(mockLoader);
		
		when(mockPlugin.getLogger()).thenReturn(Logger.getGlobal());
		
		return mockPlugin;
	}
}
