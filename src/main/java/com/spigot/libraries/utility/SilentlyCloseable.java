package com.spigot.libraries.utility;

public interface SilentlyCloseable extends AutoCloseable {
	public default void silentClose() {
		try {
			close();
		} catch (Exception e) {
			throw new RuntimeException("Error during silenced closing", e);
		}
	}
}
