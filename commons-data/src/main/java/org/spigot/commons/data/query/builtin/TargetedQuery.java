package org.spigot.commons.data.query.builtin;

import org.spigot.commons.data.query.Query;
import org.spigot.commons.data.query.Target;

import java.util.Arrays;

public record TargetedQuery(Query query, Target[] targets) {
	public String format() {
		return String.format(query.format(), (Object[]) Arrays.stream(targets).map(Target::format).toArray(String[]::new));
	}

	public int hashCode() {
		return hashCode(query, targets);
	}

	public static int hashCode(Query query, Target[] targets) {
		return Arrays.hashCode(targets) ^ query.hashCode();
	}
}
