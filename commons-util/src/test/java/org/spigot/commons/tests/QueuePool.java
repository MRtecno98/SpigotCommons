package org.spigot.commons.tests;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.Getter;

@Getter
public class QueuePool {
	private Map<String, BlockingQueue<?>> pool = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public <T> BlockingQueue<T> getQueue(String name) {
		getPool().computeIfAbsent(name, (k) -> new LinkedBlockingQueue<T>());
		
		return (BlockingQueue<T>) getPool().get(name);
	}
	
	private static final QueuePool INSTANCE = new QueuePool();
	
	public static QueuePool getInstance() {
		return INSTANCE;
	}
}
