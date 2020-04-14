package com.spigot.libraries.cxml.parsing.tokens;

import java.util.Collection;

import org.w3c.dom.Node;

import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
@SuppressWarnings("deprecation")
public class ParsingEnvironment {
	private Node inventoryNode;
	private Collection<Class<?>> importedClasses;
}
