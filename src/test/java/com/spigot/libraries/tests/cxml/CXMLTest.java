package com.spigot.libraries.tests.cxml;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.xml.sax.SAXException;

import com.google.common.reflect.ClassPath;
import com.spigot.libraries.cxml.nodes.CXMLNode;
import com.spigot.libraries.cxml.parsing.CXMLParser;
import com.spigot.libraries.utility.ReflectionUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JavaPlugin.class, Server.class, Bukkit.class})
public class CXMLTest {
	@Test
	public void defaultImportTest() throws SAXException, IOException, ClassNotFoundException {
		CXMLParser parser = CXMLParser.getDefaultParser();
		parser.loadPackages();
		
		ClassPath classpath = ClassPath.from(CXMLNode.class.getClassLoader());
		Set<Class<? extends Object>> default_imports = classpath.getTopLevelClasses("com.spigot.libraries.cxml.nodes")
				.stream().map(ReflectionUtils::loadClassInfo)
				.collect(Collectors.toSet());
		
		default_imports.remove(CXMLNode.class);
		
		assertTrue(parser.getEnvironment()
				.getImports().containsAll(default_imports));
	}
	
	@Test
	public void importTest() throws SAXException, IOException, ClassNotFoundException {
		CXMLParser parser = CXMLParser.getDefaultParser();
		parser.loadData(getClass()
				.getClassLoader()
				.getResourceAsStream("testgui.xml"));
		parser.prepare();
		
		assertTrue(parser.getEnvironment().getImports().contains(
				Class.forName("com.spigot.libraries.tests.cxml.TestingGuiController")));
	}
	
	@Test
	public void parseTest() throws SAXException, IOException {
		mockBukkit();
		
		CXMLParser parser = CXMLParser.getDefaultParser();
		parser.loadData(getClass()
				.getClassLoader()
				.getResourceAsStream("testgui.xml"));
		
		Collection<CXMLNode> results = parser.parse();
		
		System.out.println("testgui.xml parsing results:");
		for(CXMLNode node : results) 
			System.out.println("\t- " + node.toString() + " => " + node.getValue().toString());
		
		System.out.println();
	}
	
	public void mockBukkit() {
		Bukkit.setServer(getMockServer());
	}
	
	public Server getMockServer() {
		Server s = PowerMockito.mock(Server.class);
		
		when(s.getName()).thenReturn("PowerMock Bukkit");
		when(s.getVersion()).thenReturn("MOCK");
		when(s.getBukkitVersion()).thenReturn("MOCK");
		
		Inventory i = PowerMockito.mock(Inventory.class);
		when(s.createInventory(any(), anyInt())).thenReturn(i);
		when(s.getLogger()).thenReturn(Logger.getGlobal());
		
		PluginManager m = PowerMockito.mock(PluginManager.class);
		PowerMockito.doNothing().when(m).registerEvents(any(), any());
		when(s.getPluginManager()).thenReturn(m);
		
		return s;
	}
}
