package org.spigot.commons.commands.exceptions;

import lombok.Getter;

@Getter
public class ParseException extends RuntimeException {
	private final String invalid;

	public ParseException(String invalid) {
		super("Could not parse " + invalid);

		this.invalid = invalid;
	}

	public ParseException(String invalid, Throwable cause) {
		super("Could not parse " + invalid, cause);

		this.invalid = invalid;
	}
}
