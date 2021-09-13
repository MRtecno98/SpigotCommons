package org.spigot.commons.tests.commands;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.junit.Test;
import org.spigot.commons.commands.Command;
import org.spigot.commons.commands.CommandDispatcher;
import org.spigot.commons.commands.ExecutionContext;

public class DispatcherTests {
	@Test
	public void dispatcherBasicTest() {
		int[] response = new int[1];

		CommandDispatcher dispatcher = new CommandDispatcher().addCommands(new Command("disp1") {
			@Override
			public boolean execute(CommandSender sender, ExecutionContext context) {
				response[0]++;
				return false;
			}
		}, new Command("disp2") {
			@Override
			public boolean execute(CommandSender sender, ExecutionContext context) {
				response[0]++;
				return false;
			}
		});

		dispatcher.onCommand(null, null, "disp1", new String[0]);
		dispatcher.onCommand(null, null, "disp2", new String[0]);
		
		assertEquals(2, response[0]);
	}
	
	@Test
	public void dispatcherTabCompleteTest() {
		int[] response = new int[1];

		CommandDispatcher dispatcher = new CommandDispatcher().addCommands(new Command("disp1", 1) {
			@Override
			public List<String> tabComplete(CommandSender sender, ExecutionContext context) {
				response[0]++;
				return null;
			}

			@Override
			public boolean execute(CommandSender sender, ExecutionContext context) {
				return false;
			}
		}, new Command("disp2", 1) {
			@Override
			public List<String> tabComplete(CommandSender sender, ExecutionContext context) {
				response[0]++;
				return null;
			}
			
			@Override
			public boolean execute(CommandSender sender, ExecutionContext context) {
				return false;
			}
		});

		dispatcher.onTabComplete(null, null, "disp1", new String[0]);
		dispatcher.onTabComplete(null, null, "disp2", new String[0]);
		
		assertEquals(2, response[0]);
	}
}
