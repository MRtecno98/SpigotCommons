package org.spigot.commons.tests.commands;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.junit.Test;
import org.spigot.commons.commands.Command;
import org.spigot.commons.tests.BukkitTests;

import static org.junit.Assert.assertEquals;

public class ExecutionTests extends BukkitTests {
	@Test
	public void testExecute() {
		Command c = new TestCommand();

		PlayerMock p = server.addPlayer();
		p.setName("Herobrine");

		server.addPlayer().setName("Notch");

		c.onCommand(p, null, "greet", new String[] {});
		c.onCommand(p, null, "greet", new String[] { "Notch" });
		c.onCommand(p, null, "greet", new String[] { "Dinnerbone" });

		assertEquals("Hello, Herobrine!", p.nextMessage());
		assertEquals("Hello, Notch!", p.nextMessage());
		assertEquals("Player not found", p.nextMessage());
	}
}
