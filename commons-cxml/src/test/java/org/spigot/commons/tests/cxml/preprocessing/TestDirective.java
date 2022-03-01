package org.spigot.commons.tests.cxml.preprocessing;

import org.spigot.commons.cxml.parsing.ParsingEnvironment;
import org.spigot.commons.cxml.preprocessing.PreprocessorDirective;

public class TestDirective implements PreprocessorDirective {

	@Override
	public void accept(ParsingEnvironment t, String u) {
		CXMLPreprocessingTest.worked = true;
		System.out.println("Test directive ran");
	}

	@Override
	public String getName() {
		return "test";
	}

}
