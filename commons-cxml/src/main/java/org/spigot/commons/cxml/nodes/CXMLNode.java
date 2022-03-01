package org.spigot.commons.cxml.nodes;

import java.util.Collection;

import org.spigot.commons.cxml.parsing.ParsingEnvironment;
import org.w3c.dom.Node;

/*
 * This class also marks the default nodes package 
 * (see CXMLParser#getDefaultParser)
 * 
 * DO NOT MOVE IT OR IT WILL BREAK FREAKING EVERYTHING
 */

public interface CXMLNode<T> {
	public void onInstancing(CXMLNode<?> parent, Node raw, ParsingEnvironment env);
	public void onLoad(CXMLNode<?> parent, Collection<CXMLNode<?>> childs, ParsingEnvironment env);
	
	public default T getValue() {
		return null;
	};
}
