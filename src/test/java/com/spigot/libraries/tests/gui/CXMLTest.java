package com.spigot.libraries.tests.gui;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.spigot.libraries.cxml.parsing.CXMLParser;

public class CXMLTest {
	
	@Test
	public void parseTest() throws SAXException, IOException {
		CXMLParser parser = new CXMLParser();
		parser.loadData(getClass()
				.getClassLoader()
				.getResourceAsStream("testgui.xml"))
			.loadEnvironment();
	}
}
