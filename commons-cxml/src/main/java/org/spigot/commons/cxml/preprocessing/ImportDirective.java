package org.spigot.commons.cxml.preprocessing;

import org.spigot.commons.cxml.exceptions.UnknownClassException;
import org.spigot.commons.cxml.parsing.ParsingEnvironment;

public class ImportDirective implements PreprocessorDirective {

	@Override
	public void accept(ParsingEnvironment t, String value) {
		try {
			Class<?> clazz = Class.forName(value);
			t.getImports().add(clazz);
		} catch (ClassNotFoundException e) {
			throw new UnknownClassException(e);
		}
	}

	@Override
	public String getName() {
		return "import";
	}

}
