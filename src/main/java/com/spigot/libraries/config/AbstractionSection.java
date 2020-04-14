package com.spigot.libraries.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;

/**
 * A generic configuration section, clones the base section and copies it onto itself, processing the data for specific usages.
 * 
 * @author MRtecno98
 * @category Configurations
 * @see <a href="https://github.com/MRtecno98/SpigotCommons/blob/master/README.md">Example usages</a>
 */
public abstract class AbstractionSection extends MemorySection {
	
	/**
	 * Constructs a new cloning section using a base section as reference.
	 * <p>User can't consturct a generic AbstractionSection, this is only used by subclasses.</p>
	 * 
	 * @param sect The base section to clone.
	 * @param preCloningData Additional data to be passed to the cloning routines.
	 */
	protected AbstractionSection(ConfigurationSection sect, Object... preCloningData) {
		super(sect.getParent(), sect.getCurrentPath());
		preCloning(preCloningData);
		for(String k : sect.getKeys(false)) set(k, sect.get(k));
	}
	
	/**
	 * Overridden for cloning, used mainly for processing the base section, shouldn't be used for editing values post-cloning.
	 */
	@Override
	public void set(String key, Object value) {
		super.set(key, value);
		onSetting(key, value);
	}
	
	/**
	 * Executed before the cloning process, may be used to setup receiving objects, or other setup actions.
	 * @param data The data passed from the constructor
	 */
	public void preCloning(Object... data) {}
	
	/**
	 * Called on every {@link #set} call, used by subclasses for cloning processing.
	 * 
	 * @param key Same as {@link set}
	 * @param value Same as {@link set}
	 * 
	 * @see ConfigurationSection#set(String, Object)
	 */
	public abstract void onSetting(String key, Object value);
}
