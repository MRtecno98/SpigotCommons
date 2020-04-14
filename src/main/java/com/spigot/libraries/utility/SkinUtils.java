package com.spigot.libraries.utility;

import java.util.Base64;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SkinUtils {
	public static final String BASE_TEXTURE_URL = "http://textures.minecraft.net/texture/";
	
	public static String getIDfromBase64(String base64) {
		try {
			JSONObject parsed = (JSONObject) new JSONParser().parse( 
					new String( Base64.getDecoder().decode(base64 ) ));
			
			String url = (String) ((JSONObject) ((JSONObject) parsed.get("textures"))
																	.get("SKIN"))
																	.get("url");
			String[] splitted = url.split("/");
			
			return splitted[splitted.length-1];
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static String encodeBase64fromID(String id) {
		JSONObject url = new JSONObject();
		url.put("url", BASE_TEXTURE_URL + id);
		
		JSONObject skin = new JSONObject();
		skin.put("SKIN", url);
		
		JSONObject res = new JSONObject();
		res.put("textures", skin);
		
		return Base64.getEncoder().encodeToString(res.toJSONString().getBytes());
	}
}
