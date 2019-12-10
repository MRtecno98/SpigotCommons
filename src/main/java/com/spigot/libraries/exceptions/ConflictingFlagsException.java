package com.spigot.libraries.exceptions;

public class ConflictingFlagsException extends RuntimeException {
	private static final long serialVersionUID = -2404702074744163624L;
	public ConflictingFlagsException() { super(); }
	public ConflictingFlagsException(String message) { super(message); }
}
