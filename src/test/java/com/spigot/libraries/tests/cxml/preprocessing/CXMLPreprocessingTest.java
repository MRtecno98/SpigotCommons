package com.spigot.libraries.tests.cxml.preprocessing;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.spigot.libraries.cxml.parsing.CXMLParser;
import com.spigot.libraries.cxml.preprocessing.CXMLPreprocessorFactory;

public class CXMLPreprocessingTest {
	public static boolean worked = false;
	
	@Test
	public void preprocessorTest() throws SAXException, IOException {
		CXMLParser parser = new CXMLParser();
		parser.setPreprocessor(CXMLPreprocessorFactory.newInstance()
				.withDirectives(new TestDirective())
				.build());
		
		parser.loadData(getClass()
				.getClassLoader()
				.getResourceAsStream("testpreprocessor.xml"));
		
		parser.prepare();
		
		assertTrue(worked);
	}
}
