package com.spigot.libraries.exceptions;

public class ConflictingMastersException extends RuntimeException {
	private static final long serialVersionUID = 6079763143291642812L;

	public ConflictingMastersException() {
		super();
	}
	
	public ConflictingMastersException(Object master1, Object master2) {
		super("Conflicting Masters [" + master1.toString() + " & " + master2.toString() + "]");
	}

	public ConflictingMastersException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ConflictingMastersException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConflictingMastersException(String message) {
		super(message);
	}

	public ConflictingMastersException(Throwable cause) {
		super(cause);
	}
}
