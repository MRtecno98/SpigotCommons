package com.spigot.libraries.exceptions;

public class PluginNotProvidedException extends RuntimeException {
	private static final long serialVersionUID = -2404702074744163624L;
	public PluginNotProvidedException() { super(); }
	public PluginNotProvidedException(String message) { super(message); }
}
