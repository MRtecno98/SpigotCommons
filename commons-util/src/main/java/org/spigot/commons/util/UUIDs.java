package org.spigot.commons.util;

import java.util.UUID;

public class UUIDs {
	/**
	 * Builds {@link UUID} object from a string representation in a generic format,
	 * adding dashes if necessary, trimming out unnecessary characters and taking
	 * care of case-sensitiveness
	 * 
	 * @param uuid a string representation of an UUID
	 * @return the parsed UUID
	 */
	public static UUID fromGenericFormat(String uuid) {
	    return fromDashless(uuid.trim().toLowerCase().replaceAll("-", ""));
	}

	/**
	 * Builds {@link UUID} object from string representation WITHOUT dashes<br>
	 * Useful as the usual {@link UUID#fromString(String)} method doesn't support dashless format
	 * 
	 * @param uuid String represantation of an UUID without dashes
	 * @return the parsed UUID
	 */
	public static UUID fromDashless(String uuid) {
	    StringBuilder builder = new StringBuilder(uuid);
	    
	    /* Backwards adding to avoid index adjustments */
	    try {
	        builder.insert(20, "-");
	        builder.insert(16, "-");
	        builder.insert(12, "-");
	        builder.insert(8, "-");
	    } catch (StringIndexOutOfBoundsException e){
	        throw new IllegalArgumentException("Invalid UUID");
	    }
	 
	    return UUID.fromString(builder.toString());
	}
}
