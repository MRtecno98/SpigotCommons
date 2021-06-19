package org.spigot.commons.tests.commands;

import static org.junit.Assert.assertTrue;

import org.bukkit.command.CommandSender;
import org.junit.Test;
import org.spigot.commons.commands.Command;
import org.spigot.commons.commands.ExecutionContext;

public class AnonymousTests {
	@Test
	public void anonymousClassBasicTest() {
		int[] response = new int[1];
		
		Command anonymousCommand = new Command("testc") {
			@Override
			public boolean execute(CommandSender sender, ExecutionContext context) {
				response[0]++;
				return false;
			}
		}.registerSubcommand(new Command("sub1") {
			@Override
			public boolean execute(CommandSender sender, ExecutionContext context) {
				response[0]++;
				return false;
			}
		});
		
		anonymousCommand.onCommand(null, null, "testc", new String[] { "aaaa", "bbbb", "sub1", "cccc" });
		assertTrue(response[0] == 2);
	}
	
	@Test
	public void anonymousClassBreakTest() {
		int[] response = new int[1];
		
		Command anonymousCommand = new Command("testc") {
			@Override
			public boolean execute(CommandSender sender, ExecutionContext context) {
				response[0]++;
				return true;
			}
		}.registerSubcommand(new Command("sub1") {
			@Override
			public boolean execute(CommandSender sender, ExecutionContext context) {
				throw new RuntimeException();
			}
		});
		
		anonymousCommand.onCommand(null, null, "testc", new String[] { "ciao", "bubu", "sub1", "gigi" });
		assertTrue(response[0] == 1);
	}
}
