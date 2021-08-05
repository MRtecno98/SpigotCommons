package org.spigot.commons.tests;

import org.junit.After;
import org.junit.Before;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;

public class BukkitTests {
	protected ServerMock server;
	
	@Before
	public void setup() {
		server = MockBukkit.mock();
	}
	
	@After
	public void teardown() {
		MockBukkit.unload();
	}
}
