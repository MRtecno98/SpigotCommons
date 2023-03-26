package org.spigot.commons.data.query;

import org.spigot.commons.data.query.builtin.TargetedQuery;

public interface Query extends Stringable {
	public String format(); // "override" to add documentation later
	public Target[] availableTargets();
	public short maxTargets();

	public default TargetedQuery target(Target... targets) {
		return new TargetedQuery(this, targets);
	}
}
