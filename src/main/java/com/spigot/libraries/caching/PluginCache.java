package com.spigot.libraries.caching;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.bukkit.plugin.Plugin;

import com.google.common.collect.Maps;

public class PluginCache implements Map<String, Serializable> {
	private static final String CACHE_FILENAME = ".plugin_cache";
	
	private Plugin pl;
	
	private PluginCache(Plugin pl) {
		this.pl = pl;
	}
	
	public static PluginCache getPluginCache(Plugin pl) {
		return new PluginCache(pl);
	}
	
	private void checkDirectory() {
		pl.getDataFolder().mkdirs();
	}
	
	protected File getCacheFile() {
		return new File(pl.getDataFolder(), CACHE_FILENAME);
	}
	
	protected void saveData(Map<String, Serializable> data) {
		ObjectOutputStream os = null;
		try {
			checkDirectory();
			os = new ObjectOutputStream(new FileOutputStream(getCacheFile()));
			os.writeObject(data);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(os != null) os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	protected Map<String, Serializable> readData() {
		ObjectInputStream os = null;
		try {
			checkDirectory();
			if(!getCacheFile().isFile()) return Maps.newHashMap();
			os = new ObjectInputStream(new FileInputStream(getCacheFile()));
			return (Map<String, Serializable>) os.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				if(os != null) os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	@Override
	public void clear() {
		Map<String, Serializable> data = readData();
		data.clear();
		saveData(data);
	}

	@Override
	public boolean containsKey(Object key) {
		return readData().containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return readData().containsValue(value);
	}

	@Override
	public Set<Entry<String, Serializable>> entrySet() {
		return readData().entrySet();
	}

	@Override
	public Serializable get(Object key) {
		return readData().get(key);
	}

	@Override
	public boolean isEmpty() {
		return readData().isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return readData().keySet();
	}

	@Override
	public Serializable put(String key, Serializable value) {
		Map<String, Serializable> data = readData();
		Serializable res = data.put(key, value);
		saveData(data);
		return res;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Serializable> m) {
		Map<String, Serializable> data = readData();
		data.putAll(m);
		saveData(data);
	}

	@Override
	public Serializable remove(Object key) {
		Map<String, Serializable> data = readData();
		Serializable res = data.remove(key);
		saveData(data);
		return res;
	}

	@Override
	public int size() {
		return readData().size();
	}

	@Override
	public Collection<Serializable> values() {
		return readData().values();
	}
}
