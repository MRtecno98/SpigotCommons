package org.spigot.commons.util;

import java.util.HashMap;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class InstancesCache extends HashMap<Class<?>, Object> {
	private static final long serialVersionUID = 3674501410065191834L;
	
	public Object getInstance(Class<?> clazz) {
		try {
			if(!containsKey(clazz)) put(clazz, clazz.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return get(clazz);
	}
	
	public void clearCache(Class<?> clazz) {
		put(clazz, null);
	}
	
	public void clearCache() {
		keySet().forEach(this::clearCache);
	}
	
	@Getter private static final InstancesCache cache = new InstancesCache();
}
