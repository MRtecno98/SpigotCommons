package org.spigot.commons.tests.cxml.preprocessing;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.spigot.commons.cxml.parsing.CXMLParser;
import org.spigot.commons.cxml.preprocessing.CXMLPreprocessorFactory;
import org.xml.sax.SAXException;

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
