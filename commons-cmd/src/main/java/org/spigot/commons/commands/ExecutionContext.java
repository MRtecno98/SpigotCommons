package org.spigot.commons.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.spigot.commons.util.LinkedQueue;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public record ExecutionContext(CommandSender sender,
							   String alias,
							   Command bukkitCommand,
							   LinkedQueue<String> args,
							   Map<String, Object> data,
							   AtomicBoolean cancelled) {

	public ExecutionContext(CommandSender sender, String alias, Command bukkitCommand, LinkedQueue<String> args) {
		this(sender, alias, bukkitCommand, args, new HashMap<>(), new AtomicBoolean(false));
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) data.get(key);
	}

	public <T> T getOrDefault(String key, T defaultValue) {
		return data.containsKey(key) ? get(key) : defaultValue;
	}

	public <T> T getOrDefault(String key, Class<T> type, T defaultValue) {
		return type.isInstance(data.get(key)) ? get(key) : defaultValue;
	}

	public <T> Optional<T> getOptional(String key) {
		return Optional.ofNullable(get(key));
	}

	public <T> Optional<T> getOptional(String key, Class<T> type) {
		return type.isInstance(data.get(key)) ? getOptional(key) : Optional.empty();
	}

	public void set(String key, Object value) {
		data.put(key, value);
	}

	public void cancel() {
		cancelled.set(true);
	}

	public static ExecutionContext of(CommandSender sender, Command cmd, String label, String[] args) {
		return new ExecutionContext(sender, label, cmd, new LinkedQueue<>(List.of(args)));
	}
}
