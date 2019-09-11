package com.spigot.libraries.environment;

public class JavaVersion {
	private int variant, version, iteration, build;

	public JavaVersion(int variant, int version, int iteration, int build) {
		this.variant = variant;
		this.version = version;
		this.iteration = iteration;
		this.build = build;
	}

	public int getVariant() {
		return variant;
	}

	public int getVersion() {
		return version;
	}

	public int getIteration() {
		return iteration;
	}

	public int getBuild() {
		return build;
	}
}
