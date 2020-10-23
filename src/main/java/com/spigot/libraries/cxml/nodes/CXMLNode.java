package com.spigot.libraries.cxml.nodes;

import java.util.Collection;

import org.w3c.dom.Node;

import com.spigot.libraries.cxml.parsing.ParsingEnvironment;

/*
 * This class also marks the default nodes package 
 * (see CXMLParser#getDefaultParser)
 * 
 * DO NOT MOVE IT OR IT WILL BREAK FREAKING EVERYTHING
 */

public interface CXMLNode {
	public void onInstancing(CXMLNode parent, Node raw, ParsingEnvironment env);
	public void onLoad(CXMLNode parent, Collection<CXMLNode> childs, ParsingEnvironment env);
	
	public default Object getValue() {
		return null;
	};
}
