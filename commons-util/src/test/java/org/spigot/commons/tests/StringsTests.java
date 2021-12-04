package org.spigot.commons.tests;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;
import org.spigot.commons.util.Strings;

public class StringsTests {
	@Test
	public void stripAllTest() {
		final String[] in = {"", "a", " ", "b", ""};
		assertArrayEquals(new String[] {"a", "b"}, Strings.stripEmpty(in));
	}
}
