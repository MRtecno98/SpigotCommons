package com.spigot.libraries.utility;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor 
@ToString 
@EqualsAndHashCode
public class SimplePair<V, K> implements Pair<V, K> {
	private V first;
	private K second;
}
