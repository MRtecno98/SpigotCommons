package com.spigot.libraries.utility;

import java.util.Collection;

public class IgnoreCaseList extends BaseList<String> {
	private static final long serialVersionUID = -4134288634499379912L;
	
    public boolean containsIgnoreCase(Object obj) {
        String object = (String) obj;
        for (String string : this) if (object.equalsIgnoreCase(string)) return true;
        return false;
    }

	public IgnoreCaseList() {
		super();
	}

	public IgnoreCaseList(Collection<? extends String> coll) {
		super(coll);
	}

	public IgnoreCaseList(int initialCapacity) {
		super(initialCapacity);
	}

	public IgnoreCaseList(String... elements) {
		super(elements);
	}
}
