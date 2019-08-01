package com.spigot.libraries.reflection.resolver.minecraft;

import com.spigot.libraries.reflection.minecraft.Minecraft;
import com.spigot.libraries.reflection.resolver.ClassResolver;

/**
 * {@link ClassResolver} for <code>net.minecraft.server.*</code> classes
 */
public class NMSClassResolver extends ClassResolver {

	@Override
	@SuppressWarnings("rawtypes")
	public Class resolve(String... names) throws ClassNotFoundException {
		for (int i = 0; i < names.length; i++) {
			if (!names[i].startsWith("net.minecraft.server")) {
				names[i] = "net.minecraft.server." + Minecraft.getVersion() + names[i];
			}
		}
		return super.resolve(names);
	}
}
