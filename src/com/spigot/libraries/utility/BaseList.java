package com.spigot.libraries.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class BaseList<E> extends ArrayList<E> {
	private static final long serialVersionUID = -2522094718325128705L;

	public BaseList() {
		super();
	}

	public BaseList(Collection<? extends E> c) {
		super(c);
	}

	public BaseList(int initialCapacity) {
		super(initialCapacity);
	}
	
	@SafeVarargs
	public BaseList(E... elements) {
		super(Arrays.asList(elements));
	}
}
