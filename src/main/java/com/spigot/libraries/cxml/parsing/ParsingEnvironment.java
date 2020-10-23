package com.spigot.libraries.cxml.parsing;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class ParsingEnvironment {
	@Getter private List<String> packagesPath = new ArrayList<>();
	@Getter private List<Class<?>> imports = new ArrayList<>();
	@Getter @NonNull @Setter private Document rawDocument;
}
