package br.com.yaw.utils;

import java.util.List;

import com.google.appengine.api.datastore.Key;

/**
 * Operações utilitárias para String.
 * @author rafael
 *
 */
public class StringUtilities {

	public static String listKeyToInClause(List<Key> network) {
		StringBuilder in = new StringBuilder();
		in.append("(");
		for (int i = 0; i < network.size(); i++) {
			Key key = network.get(i);
			in.append(key.getId());
			if(i < (network.size()-1)) {
				in.append(",");
			}
		}
		
		in.append(")");
		
		return in.toString();
	}

	public static String listLongToInClause(List<Long> network) {
		StringBuilder in = new StringBuilder();
		in.append("(");
		for (int i = 0; i < network.size(); i++) {
			Long id = network.get(i);
			in.append(id);
			if(i < (network.size()-1)) {
				in.append(",");
			}
		}
		
		in.append(")");
		
		return in.toString();
	}

}
