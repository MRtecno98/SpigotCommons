package org.spigot.commons.cxml.exceptions;

public class PreprocessorNotLoadedException extends RuntimeException {
	private static final long serialVersionUID = -2221204205715577760L;

	public PreprocessorNotLoadedException() {
		super();
	}

	public PreprocessorNotLoadedException(String message, Throwable cause) {
		super(message, cause);
	}

	public PreprocessorNotLoadedException(String message) {
		super(message);
	}

	public PreprocessorNotLoadedException(Throwable cause) {
		super(cause);
	}
	
}
