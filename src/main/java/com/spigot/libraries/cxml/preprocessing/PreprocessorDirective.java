package com.spigot.libraries.cxml.preprocessing;

import java.util.function.BiConsumer;

import com.spigot.libraries.cxml.parsing.ParsingEnvironment;

public interface PreprocessorDirective extends BiConsumer<ParsingEnvironment, String> {
	public String getName();
}
