package com.spigot.libraries.tests.cxml.preprocessing;

import com.spigot.libraries.cxml.parsing.ParsingEnvironment;
import com.spigot.libraries.cxml.preprocessing.PreprocessorDirective;

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
