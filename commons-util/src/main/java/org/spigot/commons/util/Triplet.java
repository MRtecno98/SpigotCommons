package org.spigot.commons.util;

import lombok.Value;

@Value
public class Triplet<A, B, C> {
	private A a;
	private B b;
	private C c;
}
