package com.spigot.libraries.cxml.preprocessing;

import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.Node;

import com.spigot.libraries.cxml.parsing.ParsingEnvironment;

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
