package com.spigot.libraries.tests.cxml;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.spigot.libraries.cxml.parsing.CXMLParser;

public class CXMLTest {
	
	public CXMLParser getParser() throws SAXException, IOException {
		CXMLParser parser = new CXMLParser();
		parser.loadDefaultPreprocessor();
		parser.loadData(getClass()
				.getClassLoader()
				.getResourceAsStream("testgui.xml"));
		
		return parser;
	}
	
	@Test
	public void parseTest() throws SAXException, IOException {
		CXMLParser.parse(getClass()
				.getClassLoader()
				.getResourceAsStream("testgui.xml"));
	}
	
	@Test
	public void importTest() throws SAXException, IOException, ClassNotFoundException {
		CXMLParser parser = getParser();
		parser.parse();
		
		assertTrue(parser.getEnvironment().getImports().contains(
				Class.forName("com.spigot.libraries.tests.cxml.TestingGuiController")));
	}
}
