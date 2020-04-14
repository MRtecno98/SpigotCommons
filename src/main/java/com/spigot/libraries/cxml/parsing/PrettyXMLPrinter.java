package com.spigot.libraries.cxml.parsing;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.w3c.dom.Node;

public class PrettyXMLPrinter {
	public static final String ATTR_SEPCHAR = ">";
	public static final String CHLD_SEPCHAR = "-";
	
	public static List<String> formatNode(Node node) {
		int notdoc = node.getNodeName().equals("#document") ? 0 : 1;
		List<String> res = new ArrayList<String>();
		if(notdoc > 0) res.add(node.getNodeName() + ":" + node.getNodeValue());
		
		if(node.hasAttributes())
			NamedNodeHashMap.fromNodeMap(node.getAttributes())
				.forEach((uti, n) -> 
					res.add(String.join("\n", formatNode(n)
									.stream()
									.map((str) -> String.join("", Collections.nCopies(4*notdoc, ATTR_SEPCHAR)) + str)
									.collect(Collectors.toList()))));
		
		if(node.hasChildNodes())
			NodeArrayList.fromNodeList(node.getChildNodes())
				.stream()
				.filter((n) -> !n.getNodeName().startsWith("#"))
				.forEach((n) -> 
					res.add(String.join("\n", formatNode(n)
								.stream()
								.map((str) -> String.join("", Collections.nCopies(8*notdoc, CHLD_SEPCHAR)) + str)
								.collect(Collectors.toList()))));
		
		return res;
	}
	
	public static void printNode(Node node, PrintStream stream) {
		formatNode(node).forEach(stream::println);
	}
	
	public static void printNode(Node node) {
		printNode(node, System.out);
	}
}
