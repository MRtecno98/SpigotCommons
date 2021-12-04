package org.spigot.commons.tests.commands;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class TabCompleteTests {
	@Test
	public void testBasicComplete() {
		TabCompleteTestCommand cmd = new TabCompleteTestCommand();
		
		List<String> test;
		
		test = cmd.onTabComplete(null, null, "test", new String[0]);
		assertEquals(TabCompleteTestCommand.CONTROL_DATA, test);
		
		test = cmd.onTabComplete(null, null, "test", new String[] { "aaaa" });
		assertEquals(TabCompleteTestCommand.CONTROL_DATA, test);
	}

	@Test
	public void testSingleComplete() {
		ArgTabCompleteTestCommand cmd = new ArgTabCompleteTestCommand();
		
		List<String> test;
		
		test = cmd.onTabComplete(null, null, "testsingle", new String[0]);
		assertEquals(ArgTabCompleteTestCommand.CONTROL_DATA, test);
		
		test = cmd.onTabComplete(null, null, "testsingle", new String[] { "aaaa", "" });
		assertEquals(Collections.emptyList(), test);
	}
	
	@Test
	public void testSubcommandComplete() {
		TabCompleteTestCommand cmd = new TabCompleteTestCommand();
		
		List<String> test;

		test = cmd.onTabComplete(null, null, "test", new String[] { "aaaa", "bbbb", "" });
		assertEquals(Collections.emptyList(), test);
		
		cmd.registerSubcommand(new TabCompleteSubcommand());
		test = cmd.onTabComplete(null, null, "test", new String[] { "aaaa", "bbbb", "" });
		assertEquals(Arrays.asList("sub"), test);
	}
	
	@Test
	public void testRecursiveBasicComplete() {
		TabCompleteTestCommand cmd = new TabCompleteTestCommand();
		cmd.registerSubcommand(new TabCompleteSubcommand());
		
		List<String> test;

		test = cmd.onTabComplete(null, null, "test", new String[] { "aaaa", "bbbb", "sub", "" });
		assertEquals(TabCompleteSubcommand.CONTROL_DATA, test);
	}
}
