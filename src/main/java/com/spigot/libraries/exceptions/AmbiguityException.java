package com.spigot.libraries.exceptions;

public class AmbiguityException extends RuntimeException {
	private static final long serialVersionUID = 1051701551516970711L;

	public AmbiguityException() {
		super();
	}

	public AmbiguityException(String message, Throwable cause) {
		super(message, cause);
	}

	public AmbiguityException(String message) {
		super(message);
	}

	public AmbiguityException(Throwable cause) {
		super(cause);
	}
}
