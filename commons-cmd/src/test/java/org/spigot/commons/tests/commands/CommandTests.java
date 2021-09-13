package org.spigot.commons.tests.commands;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommandTests {
	@Test
	public void basicCommandTest() {
		ExecutionTestCommand cmd = new ExecutionTestCommand();

		cmd.onCommand(null, null, "test", new String[] { "aaaa", "bbbb" });
		assertTrue(cmd.executed);
	}
	
	@Test
	public void dataCommandTest() {
		final byte testData = 42;
		DataTestCommand cmd = new DataTestCommand(testData);
		
		cmd.onCommand(null, null, "data", new String[] { "aaaa", "bbbb", "datasub", "cccc" });
		
		DataTestSubcommand subcmd = cmd.subcommand;
		
		assertEquals(testData, cmd.data1);
		assertEquals(testData, cmd.data2);
		assertEquals(testData, cmd.data3);
		
		assertThat(cmd.data1, not(equalTo(subcmd.data1)));
		assertEquals(cmd.data2, subcmd.data2);
		assertEquals(cmd.data3, subcmd.data3);
	}
	
	@Test
	public void minCommandTest() {
		MinArgTestCommand cmd = new MinArgTestCommand();
		
		cmd.onCommand(null, null, "test", new String[] { "aaaa", "sub" });
	}
	
	@Test(expected = AssertionError.class)
	public void minCommandTestFail() {
		MinArgTestCommand cmd = new MinArgTestCommand();
		
		cmd.onCommand(null, null, "test", new String[] { "aaaa", "bbbb", "sub" });
	}
}
