package br.com.promove.utils;

import java.util.HashMap;
import java.util.Map;

public class Config {
	private static Map<String, String> dict = new HashMap<String, String>();
	
	private Config() {}
	
	public static void setConfig(String key, String value) {
		dict.put(key, value);
	}
	
	public static String getConfig(String key) {
		return dict.get(key);
	}
	
}
