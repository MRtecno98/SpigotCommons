package org.spigot.commons.cxml.parsing;

import java.io.Serializable;
import java.util.Optional;

import org.w3c.dom.Node;

//Unified Tag Identifier
public class UTI implements Serializable {
	private static final long serialVersionUID = 3975706451275282031L;
	
	private String name;
	private Optional<String> namespace;
	
	public UTI(Node n) {
		this(n.getNamespaceURI(), n.getNodeName());
	}
	
	public UTI(String namespace, String name) {
		this.namespace = Optional.ofNullable(namespace);
		this.name = name;
	}
	
	public UTI(String name) {
		this(null, name);
	}

	public Optional<String> getNamespace() {
		return namespace;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof UTI)) return false;
		UTI uti = ((UTI) other);
		return getName().equals(uti.getName()) && getNamespace().equals(uti.getNamespace());
	}
	
	@Override
	public String toString() {
		return "UTI[" 
				+ getNamespace().orElse("") 
				+ (getNamespace().isPresent() ? "::" : "") 
				+ getName() 
				+ "]";
	}
	
	public static UTI ofNode(Node n) {
		return new UTI(n);
	}
}
