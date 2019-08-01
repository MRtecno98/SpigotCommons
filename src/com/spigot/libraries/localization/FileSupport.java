package com.spigot.libraries.localization;

import java.io.File;
import java.io.InputStream;
import java.util.regex.Pattern;

public abstract class FileSupport implements LocalizationSupport {
	private File path;
	private String locale;
	private InputStream defaultResource;
	
	public FileSupport(File path, String locale, InputStream defaultResource) {
		this.path = path;
		this.locale = locale;
		this.defaultResource = defaultResource;
	}
	
	public boolean hasDefault() {
		return defaultResource != null;
	}
	
	public InputStream getDefault() {
		return defaultResource;
	}
	
	public File getFile() {
		return path;
	}
	
	public void setFile(File path) {
		this.path = path;
	}

	@Override
	public String getSupportName() {
		return path.getName().split(Pattern.quote("."))[0];
	}

	@Override
	public String getLocale() {
		return locale;
	}

}
