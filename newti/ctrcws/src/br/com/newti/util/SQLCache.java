package br.com.newti.util;

import java.util.HashMap;
import java.util.Map;

public class SQLCache {
	private static Map<String, String> scripts = new HashMap<String, String>();
	
	public static void addScript(String nome, String sql) {
		scripts.put(nome, sql);
	}
	
	public static String getScript(String nome) {
		return scripts.get(nome);
	}
}
