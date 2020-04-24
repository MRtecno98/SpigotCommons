package com.spigot.libraries.cxml.preprocessing;

public class UnknownDirectiveException extends RuntimeException {
	private static final long serialVersionUID = 2513388236610005232L;

	public UnknownDirectiveException() {
		super();
	}

	public UnknownDirectiveException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownDirectiveException(String message) {
		super(message);
	}

	public UnknownDirectiveException(Throwable cause) {
		super(cause);
	}
}
