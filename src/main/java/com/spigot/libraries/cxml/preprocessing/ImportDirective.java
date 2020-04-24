package com.spigot.libraries.cxml.preprocessing;

import com.spigot.libraries.cxml.parsing.ParsingEnvironment;

public class ImportDirective implements PreprocessorDirective {

	@Override
	public void accept(ParsingEnvironment t, String value) {
		try {
			Class<?> clazz = Class.forName(value);
			t.getImports().add(clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return "import";
	}

}
