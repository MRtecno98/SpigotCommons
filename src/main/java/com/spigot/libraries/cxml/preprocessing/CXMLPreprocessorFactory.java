package com.spigot.libraries.cxml.preprocessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class CXMLPreprocessorFactory {
	private @Getter List<PreprocessorDirective> directives = new ArrayList<>();
	
	public CXMLPreprocessorFactory withDirectives(PreprocessorDirective... directives) {
		getDirectives().addAll(Arrays.asList(directives));
		return this;
	}
	
	public CXMLPreprocessor build() {
		return new CXMLPreprocessor(getDirectives());
	}
	
	private CXMLPreprocessorFactory() {}
	public static CXMLPreprocessorFactory newInstance() {
		return new CXMLPreprocessorFactory();
	}
}
