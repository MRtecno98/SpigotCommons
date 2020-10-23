package com.spigot.libraries.exceptions;

public class UnknownClassException extends RuntimeException {
	private static final long serialVersionUID = 1915752035940206327L;

	public UnknownClassException() {
		super();
	}

	public UnknownClassException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownClassException(String message) {
		super(message);
	}

	public UnknownClassException(Throwable cause) {
		super(cause);
	}
	
}
