package org.spigot.commons.cxml.preprocessing;

import java.util.function.BiConsumer;

import org.spigot.commons.cxml.parsing.ParsingEnvironment;

public interface PreprocessorDirective extends BiConsumer<ParsingEnvironment, String> {
	public String getName();
}
