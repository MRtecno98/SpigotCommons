package com.spigot.libraries.cxml.nodes;

import java.util.Collection;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.w3c.dom.Node;

import com.spigot.libraries.cxml.parsing.CXML;
import com.spigot.libraries.cxml.parsing.NamedNodeHashMap;
import com.spigot.libraries.cxml.parsing.ParsingEnvironment;
import com.spigot.libraries.cxml.parsing.UTI;
import com.spigot.libraries.exceptions.PluginNotProvidedException;
import com.spigot.libraries.exceptions.UnknownClassException;
import com.spigot.libraries.gui.CraftIGUI;

import lombok.Getter;

public class Inventory implements CXMLNode {
	public static final int DEFAULT_ROWS = 3;
	
	@Getter private CraftIGUI IGUI;
	@Getter private Optional<Class<?>> controller;
	
	@Override
	public void onInstancing(CXMLNode parent, Node raw, ParsingEnvironment env) {
		if(parent != null) throw new RuntimeException("Inventory node must be root node");
		
		NamedNodeHashMap attributes = NamedNodeHashMap.fromNodeMap(raw.getAttributes());
		
		int rows = DEFAULT_ROWS;
		Node rowsnode = attributes.get(new UTI("rows"));
		if(rowsnode != null) rows = Integer.valueOf(rowsnode.getNodeValue());
		
		String name = null;
		Node namenode = attributes.get(new UTI("title"));
		if(namenode != null) name = namenode.getNodeValue();
		
		controller = Optional.empty();
		Node contrnode = attributes.get(new UTI("controller"));
		if(contrnode != null) controller = 
				Optional.of(env.getImports().stream()
					.filter((c) -> c.getName().equals(contrnode.getNodeValue()))
					.findFirst()
					.orElseThrow(() -> new UnknownClassException("Couldn't locate controller class \"" + contrnode.getNodeValue() + "\"")));
		
		
		Optional<Plugin> pl = Optional.empty();
		
		String plname = attributes.get(new UTI("plugin")).getNodeValue();
		if(plname.startsWith("#"))
			if(getController().isPresent())
				pl = Optional.ofNullable((JavaPlugin) CXML.Utilities.getAnnotatedValue(plname.substring(1), getController().get()));
		else pl = Optional.ofNullable(Bukkit.getPluginManager().getPlugin(plname));
		
		if(!pl.isPresent()) 
			throw new PluginNotProvidedException("Couldn't locate plugin instance by any means using indicator: \"" + plname + "\"");
		
		IGUI = new CraftIGUI((JavaPlugin) pl.get(), null, rows*9, name);
	}

	@Override
	public void onLoad(CXMLNode parent, Collection<CXMLNode> childs, ParsingEnvironment env) {
		
	}
	
	@Override
	public CraftIGUI getValue() {
		return getIGUI();
	}
}
