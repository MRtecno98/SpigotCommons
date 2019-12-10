package com.spigot.libraries.permissions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.spigot.libraries.utility.ReflectionUtils;

public class PermManagers {
	private Map<String, PermissionsChecker> managers = new HashMap<>();
	
	public PermManagers() { register(DefaultPerms.getInstance()); }
	
	public boolean register(PermissionsChecker... checkers) {
		if(Arrays.asList(checkers).stream()
				.anyMatch((checker) -> 
						  clazz(checker.getClass()).isPresent())) return false;
		
		Arrays.asList(checkers).forEach((checker) -> 
			managers.put(ReflectionUtils.getClassSimpleName(
								checker.getClass()), 
					checker));
		
		return true;
	}
	
	public Optional<PermissionsChecker> clazz(Class<? extends PermissionsChecker> clazz) {
		return managers.values().stream()
				.filter((checker) -> 
						clazz.equals(checker.getClass()))
				.findFirst();
	}
	
	public Optional<PermissionsChecker> name(String name) {
		return Optional.ofNullable(managers.get(name));
	}
	
	public Optional<PermissionsChecker> highestPriority() {
		return managers.values().stream()
				.sorted(Comparator.comparingInt(
							PermissionsChecker::getPriority)
						.reversed())
				.findFirst();
	}
	
	private static final PermManagers INSTANCE = new PermManagers();
	public static synchronized PermManagers getGlobalInstance() {
		return INSTANCE;
	}
}
