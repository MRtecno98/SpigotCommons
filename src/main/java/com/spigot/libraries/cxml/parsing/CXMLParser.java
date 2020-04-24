package com.spigot.libraries.cxml.parsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.spigot.libraries.cxml.preprocessing.CXMLPreprocessor;
import com.spigot.libraries.cxml.preprocessing.CXMLPreprocessorFactory;
import com.spigot.libraries.cxml.preprocessing.ImportDirective;
import com.spigot.libraries.cxml.preprocessing.PreprocessorNotLoadedException;

import lombok.Getter;
import lombok.Setter;

public class CXMLParser {
	@Getter private Document rawDocument;
	@Getter private Collection<Node> nodes;
	@Getter private ParsingEnvironment environment;
	
	@Getter @Setter private CXMLPreprocessor preprocessor;
	
	public void loadData(InputStream is) throws SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		Document doc = null;
		try {
			DocumentBuilder db = factory.newDocumentBuilder();
			doc = db.parse(is);
			this.rawDocument = doc;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void loadData(File f) throws SAXException, IOException {
		loadData(new FileInputStream(f));
	}
	
	public Object parse() {
		this.environment = new ParsingEnvironment(getRawDocument());
		
		preprocess(getEnvironment());
		
		return null;
	}
	
	protected Collection<Node> discern() {
		Collection<Node> prep_directions = new ArrayList<>();
		Collection<Node> nodes = new ArrayList<>();
		
		NodeArrayList.fromNodeList(getRawDocument().getChildNodes())
			.forEach((n) -> {
				if(n.getNodeType() == Node.PROCESSING_INSTRUCTION_NODE) 
					if(!n.getNodeName().equals("xml")) prep_directions.add(n);
				else nodes.add(n);
			});
		
		this.nodes = nodes;
		return prep_directions;
	}
	
	public void loadDefaultPreprocessor() {
		setPreprocessor(
			CXMLPreprocessorFactory.newInstance()
				.withDirectives(new ImportDirective())
				.build());
	}
	
	protected void preprocess(ParsingEnvironment env) {
		if(getPreprocessor() == null) throw new PreprocessorNotLoadedException();
		getPreprocessor().getRawDirections().addAll(discern());
		getPreprocessor().preprocess(env);
	}
	
	public static Object parse(InputStream is) throws SAXException, IOException {
		CXMLParser parser = new CXMLParser();
		parser.loadData(is);
		parser.loadDefaultPreprocessor();
		
		return parser.parse();
	}
	
	public static Object parse(File f) throws SAXException, IOException {
		return parse(new FileInputStream(f));
	}
}
