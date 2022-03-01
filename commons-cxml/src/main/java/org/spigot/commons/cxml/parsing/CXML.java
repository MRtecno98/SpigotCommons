package org.spigot.commons.cxml.parsing;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.spigot.commons.util.InstancesCache;

@Retention(RUNTIME)
public @interface CXML {
	public static class Utilities {
		public static Collection<Method> getAnnotatedMethods(Class<?> clazz) {
			return Arrays.asList(clazz.getMethods())
				.stream()
				.filter((m) -> m.isAnnotationPresent(CXML.class))
				.collect(Collectors.toSet());
		}
		
		public static Collection<Field> getAnnotatedFields(Class<?> clazz) {
			return Arrays.asList(clazz.getFields())
				.stream()
				.filter((f) -> f.isAnnotationPresent(CXML.class))
				.collect(Collectors.toSet());
		}
		
		public static Object getAnnotatedValue(String valueid, Class<?> clazz) {
			Optional<Field> field = getAnnotatedFields(clazz)
					.stream()
					.filter((f) -> f.getName().equals(valueid))
					.findFirst();
			
			Optional<Method> method = getAnnotatedMethods(clazz)
					.stream()
					.filter((m) -> m.getName().equals(valueid))
					.findFirst();
			
			Object inst = InstancesCache.getCache().getInstance(clazz);
			
			try {
				return field.isPresent() ? field.get().get(inst) : method.isPresent() ? method.get().invoke(inst) : null;
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
