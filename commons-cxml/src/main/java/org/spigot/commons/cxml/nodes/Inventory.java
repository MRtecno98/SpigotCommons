package org.spigot.commons.cxml.nodes;

import java.util.Collection;
import java.util.Optional;

import org.spigot.commons.cxml.exceptions.UnknownClassException;
import org.spigot.commons.cxml.parsing.NamedNodeHashMap;
import org.spigot.commons.cxml.parsing.ParsingEnvironment;
import org.spigot.commons.cxml.parsing.UTI;
import org.spigot.commons.gui.InventoryGUI;
import org.w3c.dom.Node;

import lombok.Getter;

public class Inventory implements CXMLNode<InventoryGUI> {
	public static final int DEFAULT_ROWS = 3;
	
	@Getter private InventoryGUI gui;
	@Getter private Optional<Class<?>> controller;
	
	@Override
	public void onInstancing(CXMLNode<?> parent, Node raw, ParsingEnvironment env) {
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
		
		gui = new InventoryGUI(name, rows*9);
	}

	@Override
	public void onLoad(CXMLNode<?> parent, Collection<CXMLNode<?>> childs, ParsingEnvironment env) {
		
	}
	
	@Override
	public InventoryGUI getValue() {
		return getGui();
	}
}
