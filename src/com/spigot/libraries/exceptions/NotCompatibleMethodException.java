package com.spigot.libraries.exceptions;

public class NotCompatibleMethodException extends RuntimeException {
	private static final long serialVersionUID = -2404702074744163624L;
	public NotCompatibleMethodException() { super(); }
	public NotCompatibleMethodException(String message) { super(message); }
}
