package org.spigot.commons.util;

import lombok.Value;

/**
 * Represents three different value in a single object
 * 
 * @author MRtecno98
 * @since 2.0.0
 *
 * @param <A> Type of the first element
 * @param <B> Type of the second element
 * @param <C> Type of the third element
 */
@Value
public class Triplet<A, B, C> {
	private A a;
	private B b;
	private C c;
}
