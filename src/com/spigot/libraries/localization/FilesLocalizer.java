package com.spigot.libraries.localization;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.spigot.libraries.exceptions.DuplicatedLocaleException;
import com.spigot.libraries.utility.IOUtils;

public class FilesLocalizer implements Localizer {
	List<Locale> locales = new ArrayList<>();
	File path;
	
	public FilesLocalizer(File path) {
		this.path = path;
		
		if(!path.isDirectory()) {
			if(path.isFile()) throw new InvalidPathException("Can't setup a localizer on a file", path.getAbsolutePath());
			else path.mkdirs();
		}
		
		for(File f : path.listFiles()) if(f.isDirectory()) {
			Collection<LocalizationSupport> supports = new ArrayList<>();
			for(File supfile : f.listFiles()) supports.add(new YamlSupport(supfile, f.getName()));
			locales.add(new Locale(this, f.getName(), supports));
		}
	}
	
	@Override
	public Collection<Locale> getLocales() {
		return locales;
	}

	@Override
	public void addLocale(Locale lc) {
		for(Locale local : locales) if(local.getName().equals(lc.getName())) 
			throw new DuplicatedLocaleException("Locale " + lc.getName() + " is already registered");
		File maindir = lc.getDirectory();
		maindir.mkdirs();
		for(LocalizationSupport support : lc.getSupports()) {
			if(support instanceof FileSupport) {
				FileSupport fp = (FileSupport) support;
				fp.setFile(new File(maindir, fp.getFile().getName()));
				if(fp.hasDefault()) IOUtils.writeToFile(fp.getDefault(), 
						fp.getFile());
				else IOUtils.createNewFile(fp.getFile());
				fp.reloadSupport();
			}else throw new IllegalArgumentException("Can't register a non-file support");
		}
		
		locales.add(lc);
	}

	@Override
	public void removeLocale(String name) {
		if(!isLocaleRegistered(name)) throw new IllegalArgumentException("Unable to remove locale " + name + ", locale is not registered");
		else {
			Locale lc = getLocale(name);
			IOUtils.deleteDirectory(lc.getDirectory());
			locales.remove(getLocaleIndex(name));
		}
	}

	@Override
	public Locale getLocale(String name) {
		int index = getLocaleIndex(name);
		return index > -1 ? locales.get(index) : null;
	}
	
	protected int getLocaleIndex(String name) {
		for(int i = 0; i < locales.size(); i++) if(locales.get(i).getName().equals(name)) return i;
		return -1;
	}

	@Override
	public boolean isLocaleRegistered(String name) {
		return getLocale(name) != null;
	}
	
	@Override
	public File getPath() { return path; }

	@Override
	public void reload() {
		for(Locale lc : getLocales()) for(LocalizationSupport sup : lc.getSupports()) sup.reloadSupport();
	}

}
