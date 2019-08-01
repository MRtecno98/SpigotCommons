package com.spigot.libraries.manipulate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOManipulation {
	public static void writeToFile(InputStream in, File outfile) {
		OutputStream out = null;
		try {
			byte[] buffer = new byte[in.available()];
			in.read(buffer);
			
			out = new FileOutputStream(outfile);
			out.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void createNewFile(File f) {
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean deleteDirectory(File dir) {
	    File[] allContents = dir.listFiles();
	    if (allContents != null) {
	        for (File file : allContents) {
	            deleteDirectory(file);
	        }
	    }
	    return dir.delete();
	}
}
