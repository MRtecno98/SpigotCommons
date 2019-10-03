package com.spigot.libraries.utility;

public class Signature {
	private Class<?>[] parameters;
	private Class<?> returnType;
	private Modifier[] modifiers;
	private Class<? extends Throwable>[] throwables;
	
	public Signature returns(Class<?> returnType) {
		this.returnType = returnType;
		return this;
	}
	
	public Signature modifiers(Modifier... modifiers) {
		this.modifiers = modifiers;
		return this;
	}
	
	@SafeVarargs
	public final Signature throwsTrowables(Class<? extends Throwable>... throwables) {
		for(Class<? extends Throwable> throwable : throwables) 
			if(!(throwable.equals(Throwable.class))) 
				throw new IllegalArgumentException("\"" + throwable.getName() + "\" isn't throwable");
		
		this.throwables = throwables;
		return this;
	}
	
	public Signature takes(Class<?>... parameters) {
		this.parameters = parameters;
		return this;
	}

	public Class<?>[] getParameters() {
		return parameters;
	}

	public Class<?> getReturnType() {
		return returnType;
	}

	public Modifier[] getModifiers() {
		return modifiers;
	}

	public Class<? extends Throwable>[] getThrowables() {
		return throwables;
	}
}
