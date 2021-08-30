package org.spigot.commons.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Requests {
	/**
	 * Makes a GET HTTP request to the specified URL, reads all the response
	 * contents, and parses them using GSON
	 * 
	 * @param url the URL to make the request to
	 * @return the result of the request json parsed
	 * @throws IOException see {@link JsonParser#parse(Reader)}
	 */
	public static JsonElement makeGetJsonRequest(URL url) throws IOException {
		try (Reader r = new InputStreamReader(url.openStream())) {
			return new JsonParser().parse(r);
		}
	}

	/**
	 * Makes and HTTP GET request and returns the response status code
	 * 
	 * @param url the URL to make the request to
	 * @return an integer for the status code
	 * @throws IOException see {@link URL#openConnection()}
	 */
	public static int getStatusCode(URL url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		
		return connection.getResponseCode();
	}
}
