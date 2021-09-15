package org.spigot.commons.tests.commands;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.spigot.commons.commands.HelpCommand;
import org.spigot.commons.tests.TrackSender;

public class HelpCommandTests {
	public static final String CONTROL_DESCRIPTION = "<arg1> <arg2>";
	public static final String CONTROL_FORMAT = "testBefore\0%s %s";
	
	@Test
	public void basicHelpTest() {
		HelpTestCommand base = new HelpTestCommand();
		HelpCommand cmd = new HelpCommand("help", base);
		
		TrackSender sender = new TrackSender();
		cmd.onCommand(sender, null, "help", new String[0]);
		
		assertEquals(Arrays.asList(
					"/test"
				), sender.getMessages());
	}
	
	@Test
	public void annotationHelpTest() {
		HelpAnnotationTestCommand base = new HelpAnnotationTestCommand();
		HelpCommand cmd = new HelpCommand("help", base);
		
		TrackSender sender = new TrackSender();
		cmd.onCommand(sender, null, "help", new String[0]);
		
		assertEquals(Arrays.asList(
					"/test " + CONTROL_DESCRIPTION
				), sender.getMessages());
	}
	
	@Test
	public void subcommandHelpTest() {
		HelpTestCommand base = new HelpTestCommand();
		base.registerSubcommand(new HelpTestSubcommand());
		HelpCommand cmd = new HelpCommand("help", base);
		
		TrackSender sender = new TrackSender();
		cmd.onCommand(sender, null, "help", new String[0]);
		
		assertEquals(Arrays.asList(
					"/test",
					"/test sub"
				), sender.getMessages());
	}
}
