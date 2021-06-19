package org.spigot.commons.tests.util;

import java.lang.reflect.Field;

import org.junit.Test;
import org.spigot.commons.util.CommonReflection;

public class ReflectionTests {
	@Test
	public void defaultValuesTest() throws IllegalArgumentException, IllegalAccessException {
		ReflectionClass c = new ReflectionClass();
		
		for(Field f : c.getClass().getFields())
			f.set(c, CommonReflection.getDefaultValue(f.getType()));
	}
}
