package org.spigot.commons.tests.commands;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.spigot.commons.commands.HelpCommand;
import org.spigot.commons.tests.TrackSender;

public class HelpCommandTests {
	public static final String CONTROL_DESCRIPTION = "<arg1> <arg2>";
	public static final String CONTROL_FORMAT = "testBefore %s %s";

	public static final String CONTROL_HEADER = "Testing header";
	
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

	@Test
	public void helpHeaderTest() {
		HelpTestCommand base = new HelpTestCommand();
		HelpCommand cmd = HelpCommand.builder().label("help")
							.command(base)
							.header(CONTROL_HEADER)
							.build();
		
		TrackSender sender = new TrackSender();
		cmd.onCommand(sender, null, "help", new String[0]);
		
		assertEquals(Arrays.asList(
					CONTROL_HEADER,
					"/test"
				), sender.getMessages());
	}

	@Test
	public void helpFormatTest() {
		HelpAnnotationTestCommand base = new HelpAnnotationTestCommand();
		HelpCommand cmd = HelpCommand.builder().label("help")
							.command(base)
							.format(CONTROL_FORMAT)
							.build();
		
		TrackSender sender = new TrackSender();
		cmd.onCommand(sender, null, "help", new String[0]);
		
		assertEquals(Arrays.asList(
					String.format(CONTROL_FORMAT, "/test", CONTROL_DESCRIPTION)
				), sender.getMessages());
	}
}
