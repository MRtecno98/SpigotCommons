package com.spigot.libraries.localization;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

public interface Localizer {
	public Collection<Locale> getLocales();
	public Locale getLocale(String name);
	public void addLocale(Locale lc);
	public void removeLocale(String name);
	public boolean isLocaleRegistered(String name);
	public File getPath();
	public void reload();
	
	class Locale {
		private Localizer localizer;
		private Collection<LocalizationSupport> supports;
		private String name;
		
		public Locale(Localizer localizer, String name, Collection<LocalizationSupport> localizationSupports) {
			this.localizer = localizer;
			this.supports = localizationSupports;
			this.name = name;
		}
		
		public Locale(Localizer localizer, String name, LocalizationSupport... localizationSupports) {
			this.localizer = localizer;
			this.supports = Arrays.asList(localizationSupports);
			this.name = name;
		}
		
		public synchronized LocalizationSupport getSupport(String name) {
			for(LocalizationSupport support : getSupports()) if(support.getSupportName().equals(name)) return support;
			return null;
		}
		
		public synchronized String getName() {
			return name;
		}
		
		public synchronized File getDirectory() {
			return new File(getLocalizer().getPath(), getName());
		}
		
		public synchronized Localizer getLocalizer() {
			return localizer;
		}
		
		public synchronized Collection<LocalizationSupport> getSupports() {
			return supports;
		}
	}
}
