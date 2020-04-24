package com.spigot.libraries.cxml.parsing;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class ParsingEnvironment {
	@Getter private List<Class<?>> imports = new ArrayList<>();
	@Getter private final Document rawDocument;
}
