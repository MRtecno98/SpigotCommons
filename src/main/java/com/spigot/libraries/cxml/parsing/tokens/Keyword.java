package com.spigot.libraries.cxml.parsing.tokens;



public enum Keyword {
	IMPORT("import"),
	INVENTORY("inventory");
	
	private String v;
	
	Keyword(String v) {
		this.v = v;
	}
	
	@Override
	public String toString() {
		return v;
	}
}
