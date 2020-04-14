package com.spigot.libraries.config;

import org.bukkit.configuration.ConfigurationSection;

/**
 * Adds keyword filter functionality to the default {@link AbstractionSection}, filtering the cloning events for specific keywords given by the subclasses.
 * 
 * @author MRtecno98
 * @category Configurations
 * @see <a href="https://github.com/MRtecno98/SpigotCommons/blob/master/README.md">Example usages</a>
 */
public abstract class MappedAbstractionSection extends AbstractionSection {
	private String[] keys;
	
	/**
	 * Constructs a new MappedAbstractionSection, similar to {@link AbstractionSection#AbstractionSection(ConfigurationSection, Object...)}
	 * 
	 * @param sect See {@link AbstractionSection#AbstractionSection(ConfigurationSection, Object...) AbstractionSection}
	 * @param preCloningData See {@link AbstractionSection#AbstractionSection(ConfigurationSection, Object...) AbstractionSection}
	 */
	public MappedAbstractionSection(ConfigurationSection sect, Object... preCloningData) {
		super(sect, preCloningData);
		postCloning(preCloningData);
	}
	
	@Override
	public void preCloning(Object... data) {
		registerKeys(data);
		keys = getKeywords() != null ? getKeywords() : keys;
		if(keys == null) keys = new String[0];
	}
	
	@Override
	public void onSetting(String key, Object value) {
		if(key != null) for(String keyword : keys) if(key.equals(keyword)) onKeywordSet(key, value);
	}
	
	/**
	 * Called when a keyword is set to the section.
	 * 
	 * @param key The keyword set.
	 * @param value The value set to the keyword.
	 */
	public abstract void onKeywordSet(String key, Object value);
	
	/**
	 * Called for registering keywords, normally using {@link #setKeys(String...)} by subclasses.
	 * 
	 * @param data The cloning data from the AbstractionSection {@link AbstractionSection#AbstractionSection(ConfigurationSection, Object...) constructor}.
	 * @deprecated Very confusing manner of setting keywords, override instead the method {@link #getKeywords}.
	 */
	@Deprecated
	public void registerKeys(Object... data) {}
	
	/**
	 * Returns the keywords marking the config sections of interest.
	 * 
	 * @param data The cloning data from the AbstractionSection {@link AbstractionSection#AbstractionSection(ConfigurationSection, Object...) constructor}.
	 * @return A String array containing the keywords.
	 */
	public String[] getKeywords(Object... data) { return null; }
	
	/**
	 * Called after the cloning process.
	 * 
	 * @param data The cloning data from the AbstractionSection {@link AbstractionSection#AbstractionSection(ConfigurationSection, Object...) constructor}.
	 */
	public void postCloning(Object... data) {}
	
	/**
	 * Manually sets the keywords array, mainly used for retrocompatibility by {@link #registerKeys} overrides in subclasses.
	 * <p>This will overwrite all previous keys</p>
	 * 
	 * @param keys The array of keywords to manually set.
	 */
	protected void setKeys(String... keys) {
		this.keys = keys;
	}
}
