package br.com.yaw.resource;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * Classe responsável por criar o acesso ao resource bundle para internacionalizaçãoo da aplicação
 * @author Rafael Nunes
 *
 */
public class ResourceBundleFactory {
	private static final Map<String , HashMap<String, String>> bundles = new HashMap<String, HashMap<String, String>>();
	
	/**
	 * Método que insere o ResourceBundle no contexto da aplicação
	 */
	public static void loadBundles(String... loc) {		
		for (String lang : loc) {
			ResourceBundle rb = ResourceBundle.getBundle("eqtalMsg", new Locale(lang));
			Enumeration<String> keys = rb.getKeys();
			HashMap<String, String> bundle = new HashMap<String, String>();
			
			while(keys.hasMoreElements()) {
				String key = keys.nextElement();
				bundle.put(key, rb.getString(key));
			}
			bundles.put(lang, bundle);
		}
	}
	
	/**
	 * Método que retorna um ResourceBundle específico baseado no locale do usuário.
	 * @param language
	 * @return
	 */
	public static HashMap<String, String> getBundleByLanguage(String language) {
		if(!bundles.containsKey(language)) {
			return bundles.get("pt");
		}
		return bundles.get(language);
	}
	
}
