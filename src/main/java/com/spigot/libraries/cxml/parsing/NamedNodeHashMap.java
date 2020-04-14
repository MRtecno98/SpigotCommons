package com.spigot.libraries.cxml.parsing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class NamedNodeHashMap implements NamedNodeMap, Map<UTI, Node> {
	private NamedNodeMap delegate;
	public NamedNodeHashMap(NamedNodeMap delegate) {
		this.delegate = Objects.requireNonNull(delegate);
	}
	
	public static NamedNodeHashMap fromNodeMap(NamedNodeMap map) {
		return new NamedNodeHashMap(map);
	}
	
	@Override
	public void clear() {
		for(int i = 0; i < getLength(); i++) {
			Node it = item(i);
			removeNamedItemNS(it.getNamespaceURI(), it.getLocalName());
		}
	}

	@Override
	public boolean containsKey(Object uti) {
		return get(uti) != null;
	}

	@Override
	public boolean containsValue(Object value) {
		return values().contains(value);
	}

	@Override
	public Set<Entry<UTI, Node>> entrySet() {
		Set<Entry<UTI, Node>> res = new HashSet<>();
		for(int i = 0; i < getLength(); i++) {
			final Node n = item(i);
			
			res.add(new Map.Entry<UTI, Node>() {
				@Override
				public UTI getKey() {
					return UTI.ofNode(n);
				}
	
				@Override
				public Node getValue() {
					return n;
				}
	
				@Override
				public Node setValue(Node value) {
					return put(getKey(), value);
				}
			});
		}
		
		return res;
	}

	@Override
	public Node get(Object outi) {
		UTI uti = (UTI) outi;
		return uti.getNamespace().isPresent() ? 
				getNamedItemNS(uti.getNamespace().get(), uti.getName()) : 
				getNamedItem(uti.getName());
	}

	@Override
	public boolean isEmpty() {
		return getLength() == 0;
	}

	@Override
	public Set<UTI> keySet() {
		return entrySet().stream()
				.map(Map.Entry::getKey)
				.collect(Collectors.toSet());
	}

	@Override
	public Node put(UTI uti, Node node) {
		if(node == null) return remove(uti);
		
		if(!UTI.ofNode(node).equals(uti)) 
			throw new IllegalArgumentException("Given and Node's UTI differs");
		
		Node prev = get(uti);
		setNamedItemNS(node);
		return prev;
	}
	
	public Node put(Node node) {
		return put(UTI.ofNode(node), node);
	}

	@Override
	public void putAll(Map<? extends UTI, ? extends Node> m) {
		m.values().forEach((node) -> put(node));
	}

	@Override
	public Node remove(Object uti) {
		return remove((UTI) uti);
	}
	
	public Node remove(UTI uti) {
		return uti.getNamespace().isPresent() ? 
				removeNamedItemNS(uti.getNamespace().get(), uti.getName()) : 
				removeNamedItem(uti.getName());
	}

	@Override
	public int size() {
		return getLength();
	}

	@Override
	public Collection<Node> values() {
		return entrySet().stream()
				.map(Map.Entry::getValue)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public int getLength() {
		return delegate.getLength();
	}

	@Override
	public Node getNamedItem(String name) {
		return delegate.getNamedItem(name);
	}

	@Override
	public Node getNamedItemNS(String namespaceURI, String localName) throws DOMException {
		return delegate.getNamedItemNS(namespaceURI, localName);
	}

	@Override
	public Node item(int index) {
		return delegate.item(index);
	}

	@Override
	public Node removeNamedItem(String name) throws DOMException {
		return delegate.removeNamedItem(name);
	}

	@Override
	public Node removeNamedItemNS(String namespaceURI, String localName) throws DOMException {
		return delegate.removeNamedItemNS(namespaceURI, localName);
	}

	@Override
	public Node setNamedItem(Node arg) throws DOMException {
		return delegate.setNamedItem(arg);
	}

	@Override
	public Node setNamedItemNS(Node arg) throws DOMException {
		return delegate.setNamedItemNS(arg);
	}

}
