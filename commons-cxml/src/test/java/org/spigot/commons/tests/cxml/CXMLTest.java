package org.spigot.commons.tests.cxml;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;
import org.spigot.commons.cxml.nodes.CXMLNode;
import org.spigot.commons.cxml.parsing.CXMLParser;
import org.spigot.commons.tests.BukkitTests;
import org.xml.sax.SAXException;

public class CXMLTest extends BukkitTests {
	@Test
	public void importTest() throws SAXException, IOException, ClassNotFoundException {
		CXMLParser parser = CXMLParser.getDefaultParser();
		parser.loadData(getClass()
				.getClassLoader()
				.getResourceAsStream("testgui.xml"));
		parser.prepare();
		
		assertTrue(parser.getEnvironment().getImports().contains(
				Class.forName(TestingGuiController.class.getName())));
	}
	
	@Test
	public void parseTest() throws SAXException, IOException {
		CXMLParser parser = CXMLParser.getDefaultParser();
		parser.loadData(getClass()
				.getClassLoader()
				.getResourceAsStream("testgui.xml"));
		
		Collection<CXMLNode<?>> results = parser.parse();
		
		System.out.println("testgui.xml parsing results:");
		for(CXMLNode<?> node : results) 
			System.out.println("\t- " + node.toString() + " => " + node.getValue().toString());
		
		System.out.println();
	}
}
