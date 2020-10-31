package com.spigot.libraries.cxml.parsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.google.common.reflect.ClassPath;
import com.spigot.libraries.cxml.nodes.CXMLNode;
import com.spigot.libraries.cxml.preprocessing.CXMLPreprocessor;
import com.spigot.libraries.cxml.preprocessing.CXMLPreprocessorFactory;
import com.spigot.libraries.cxml.preprocessing.ImportDirective;
import com.spigot.libraries.cxml.preprocessing.PreprocessorNotLoadedException;
import com.spigot.libraries.utility.ReflectionUtils;

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
	
	public Collection<CXMLNode> parse() {
		prepare();
		
		return getNodes().stream()
			.map((n) -> new CXMLWalker(getEnvironment(), n).walk())
			.collect(Collectors.toList());
	}
	
	public void prepare() {
		getEnvironment().setRawDocument(getRawDocument());
		
		loadPackages();
		preprocess();
	}
	
	public void loadPackages() {
		getEnvironment().getPackagesPath().forEach((packageName) -> {
			try {
				// Using reference classloader from which CXMLNode is loaded
				// This assures that we search for nodes in the correct classpath even in a Spigot environment
				ClassPath classpath = ClassPath.from(CXMLNode.class.getClassLoader());
				Set<Class<? extends CXMLNode>> s = classpath.getTopLevelClasses(packageName).stream()
						.map(ReflectionUtils::loadClassInfo)
						.filter(CXMLNode.class::isAssignableFrom)
						.map((clazz) -> clazz.<CXMLNode>asSubclass(CXMLNode.class))
						.collect(Collectors.toSet());
				
				getEnvironment().getImports().addAll(s);
			} catch(Exception e) {
				System.out.println("Error during load of package \"" + packageName + "\":");
				e.printStackTrace();
			}
		});
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
	
	public CXMLParser addPackage(String... packageNames) {
		getEnvironment().getPackagesPath().addAll(Arrays.asList(packageNames));
		return this;
	}
	
	protected void preprocess() {
		if(getPreprocessor() == null) throw new PreprocessorNotLoadedException();
		getPreprocessor().getRawDirections().addAll(discern());
		getPreprocessor().preprocess(getEnvironment());
	}
	
	public static CXMLParser getDefaultParser() {
		String defaultPackage = null;
		
		try {
			defaultPackage = ((Package) Class.class.getMethod("getPackage").invoke(CXMLNode.class)).getName();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		
		System.out.println("Default package: " + defaultPackage);
		
		CXMLParser parser = new CXMLParser();
		parser.addPackage(Objects.requireNonNull(defaultPackage));
		parser.loadDefaultPreprocessor();
	
		return parser;
	}
	
	public static Collection<CXMLNode> parse(InputStream is) throws SAXException, IOException {
		CXMLParser parser = getDefaultParser();
		return parser.loadData(is)
				.parse();
	}
	
	public static Collection<CXMLNode> parse(File f) throws SAXException, IOException {
		return parse(new FileInputStream(f));
	}
}
