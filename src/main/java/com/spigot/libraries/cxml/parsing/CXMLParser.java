package com.spigot.libraries.cxml.parsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class CXMLParser {
	public IntermedialParsingResult loadData(InputStream is) throws SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		Document doc = null;
		try {
			DocumentBuilder db = factory.newDocumentBuilder();
			doc = db.parse(is);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	
		return new IntermedialParsingResult(doc, null);
	}
	
	public void loadData(File f) throws SAXException, IOException {
		loadData(new FileInputStream(f));
	}
}
