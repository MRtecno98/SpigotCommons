package com.spigot.libraries.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import com.spigot.libraries.data.expressions.Value;

public class ArgumentedFieldType implements IFieldType {
	private FieldType basetype;
	private Collection<Value> arguments;
	
	public ArgumentedFieldType(FieldType basetype, Value... arguments) {
		this.basetype = basetype;
		this.arguments = Collections.unmodifiableCollection(Arrays.asList(arguments));
	}
	
	public String toString() {
		return basetype.toString() + "(" + arguments.stream()
											.map((value) -> value.toString())
											.collect(Collectors.joining(", ")) + ")";
	}
}
