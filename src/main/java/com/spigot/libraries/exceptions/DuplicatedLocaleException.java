package com.spigot.libraries.exceptions;

public class DuplicatedLocaleException extends RuntimeException {
	private static final long serialVersionUID = -2404702074744163624L;
	public DuplicatedLocaleException() { super(); }
	public DuplicatedLocaleException(String message) { super(message); }
}
