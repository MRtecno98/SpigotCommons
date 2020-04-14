package com.spigot.libraries.cxml.parsing;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.diffplug.common.base.Errors;
import com.spigot.libraries.cxml.parsing.tokens.Keyword;
import com.spigot.libraries.cxml.parsing.tokens.ParsingEnvironment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Wither;

@Getter
@Wither
@RequiredArgsConstructor
@SuppressWarnings("deprecation")
public class IntermedialParsingResult {
	private final Document doc;
	private final ParsingEnvironment env;
	
	public IntermedialParsingResult loadEnvironment() {
		Node invn = NodeArrayList.fromNodeList(getDoc().getChildNodes()).stream()
				.filter((node) -> node.getNodeName().equals(Keyword.INVENTORY.toString()))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Inventory node not found"));
		
		List<Class<?>> imports = NodeArrayList.fromNodeList(doc.getChildNodes()).stream()
			.filter((node) -> node.getNodeName().equals(Keyword.IMPORT.toString()))
			.map(Errors.rethrow().wrapFunction((impnode) -> Class.forName(impnode.getNodeValue())))
			.collect(Collectors.toList());
		
		return withEnv(Optional.ofNullable(env)
					.orElse(new ParsingEnvironment(getDoc(), null))
					.withImportedClasses(imports)
					.withInventoryNode(invn));
	}
}
