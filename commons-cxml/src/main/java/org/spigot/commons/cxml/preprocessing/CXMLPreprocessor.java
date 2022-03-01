package org.spigot.commons.cxml.preprocessing;

import java.util.ArrayList;
import java.util.Collection;

import org.spigot.commons.cxml.exceptions.UnknownDirectiveException;
import org.spigot.commons.cxml.parsing.ParsingEnvironment;
import org.w3c.dom.Node;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access=AccessLevel.PACKAGE)
public class CXMLPreprocessor {
	@Getter private Collection<Node> rawDirections = new ArrayList<>();
	
	private final Collection<PreprocessorDirective> directives;
	
	public void preprocess(ParsingEnvironment env) {
		getRawDirections().forEach((n) -> {
			directives.stream()
				.filter((d) -> d.getName().equals(n.getNodeName()))
				.findFirst()
				.orElseThrow(() -> 
					new UnknownDirectiveException("Unknown directive: \"" + n.getNodeName() + "\""))
				.accept(env, n.getNodeValue());
		});
	}
}
