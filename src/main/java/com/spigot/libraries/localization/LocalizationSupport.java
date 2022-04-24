package com.spigot.libraries.localization;

public interface LocalizationSupport {
	public String getLocalizedText(String key);
	
	public String getSupportName();
	public String getLocale();
	
	public void reloadSupport();
	
	public default String getRawText(String key) {
		return getLocalizedText(key);
	};
}
