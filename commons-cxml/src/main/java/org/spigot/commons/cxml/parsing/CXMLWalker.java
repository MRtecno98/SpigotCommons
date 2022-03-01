package org.spigot.commons.cxml.parsing;

import java.util.List;
import java.util.stream.Collectors;

import org.spigot.commons.cxml.exceptions.AmbiguityException;
import org.spigot.commons.cxml.exceptions.UnknownClassException;
import org.spigot.commons.cxml.nodes.CXMLNode;
import org.w3c.dom.Node;

import lombok.Getter;

public class CXMLWalker {
	@Getter private ParsingEnvironment environment;
	@Getter private Node rawRoot;
	
	public CXMLWalker(ParsingEnvironment env, Node root) {
		this.environment = env;
		this.rawRoot = root;
	}
	
	public CXMLNode<?> walk() { return walk(null, getRawRoot()); } 
	
	public CXMLNode<?> walk(CXMLNode<?> parent, Node node) {
		UTI uti = UTI.ofNode(node);
		
		List<Class<?>> filteredImports = getEnvironment().getImports()
				.stream()
				.filter((c) -> CXMLNode.class.isAssignableFrom(c))
				.filter((c) -> c.getSimpleName().toLowerCase().equals(uti.getName()))
				.collect(Collectors.toList());
		
		if(filteredImports.size() > 1) throw new AmbiguityException("Ambiguous node class name based on imported classes");
		if(filteredImports.size() == 0) 
			throw new UnknownClassException("CXMLNode assignable class \"" + uti.getName() + "\" (case-insensitive) "
											+ "not found for node " + uti.toString());
		
		Class<?> clazz = filteredImports.get(0);
		
		CXMLNode<?> inst;
		try {
			inst = (CXMLNode<?>) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		
		inst.onInstancing(parent, node, getEnvironment());
		
		List<CXMLNode<?>> childs = 
				NodeArrayList.fromNodeList(node.getChildNodes())
					.stream()
					.filter((n) -> n.getNodeType() == Node.ELEMENT_NODE)
					.map((n) -> walk(inst, n))
					.collect(Collectors.toList());
		
		inst.onLoad(parent, childs, getEnvironment());
		
		return inst;
	}
}
