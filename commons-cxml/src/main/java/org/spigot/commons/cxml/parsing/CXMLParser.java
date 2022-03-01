package org.spigot.commons.cxml.parsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.spigot.commons.cxml.exceptions.PreprocessorNotLoadedException;
import org.spigot.commons.cxml.nodes.CXMLNode;
import org.spigot.commons.cxml.preprocessing.CXMLPreprocessor;
import org.spigot.commons.cxml.preprocessing.CXMLPreprocessorFactory;
import org.spigot.commons.cxml.preprocessing.ImportDirective;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import lombok.Getter;
import lombok.Setter;

public class CXMLParser {
	@Getter private Document rawDocument;
	@Getter private Collection<Node> nodes;
	@Getter private ParsingEnvironment environment = new ParsingEnvironment();
	
	@Getter @Setter private CXMLPreprocessor preprocessor;
	
	public CXMLParser loadData(InputStream is) throws SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		Document doc = null;
		try {
			DocumentBuilder db = factory.newDocumentBuilder();
			doc = db.parse(is);
			this.rawDocument = doc;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	public CXMLParser loadData(File f) throws SAXException, IOException {
		return loadData(new FileInputStream(f));
	}
	
	public Collection<CXMLNode<?>> parse() {
		prepare();
		
		return getNodes().stream()
			.map((n) -> new CXMLWalker(getEnvironment(), n).walk())
			.collect(Collectors.toList());
	}
	
	public void prepare() {
		getEnvironment().setRawDocument(getRawDocument());
		preprocess();
	}
	
	protected Collection<Node> discern() {
		Collection<Node> prep_directions = new ArrayList<>();
		Collection<Node> nodes = new ArrayList<>();
		
		NodeArrayList.fromNodeList(getRawDocument().getChildNodes())
			.forEach((n) -> {
				if(n.getNodeType() == Node.PROCESSING_INSTRUCTION_NODE) prep_directions.add(n);
				else if(n.getNodeType() == Node.ELEMENT_NODE) nodes.add(n);
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
	
	protected void preprocess() {
		if(getPreprocessor() == null) throw new PreprocessorNotLoadedException();
		getPreprocessor().getRawDirections().addAll(discern());
		getPreprocessor().preprocess(getEnvironment());
	}
	
	public static CXMLParser getDefaultParser() {
		CXMLParser parser = new CXMLParser();
		parser.loadDefaultPreprocessor();
	
		return parser;
	}
	
	public static Collection<CXMLNode<?>> parse(InputStream is) throws SAXException, IOException {
		CXMLParser parser = getDefaultParser();
		return parser.loadData(is).parse();
	}
	
	public static Collection<CXMLNode<?>> parse(File f) throws SAXException, IOException {
		return parse(new FileInputStream(f));
	}
}
