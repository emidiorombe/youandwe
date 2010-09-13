package br.com.yaw.utils;

import java.util.List;

import br.com.yaw.entity.CompanyTag;

import com.google.appengine.api.datastore.Key;

/**
 * Operações utilitárias para String.
 * @author rafael
 *
 */
public class StringUtilities {

	public static String listKeyToInClause(List<Key> keys) {
		StringBuilder in = new StringBuilder();
		in.append("(");
		for (int i = 0; i < keys.size(); i++) {
			Key key = keys.get(i);
			in.append(key.getId());
			if(i < (keys.size()-1)) {
				in.append(",");
			}
		}
		
		in.append(")");
		
		return in.toString();
	}

	public static String listLongToInClause(List<Long> ids) {
		StringBuilder in = new StringBuilder();
		in.append("(");
		for (int i = 0; i < ids.size(); i++) {
			Long id = ids.get(i);
			in.append(id);
			if(i < (ids.size()-1)) {
				in.append(",");
			}
		}
		
		in.append(")");
		
		return in.toString();
	}

	public static String listTagToString(List<CompanyTag> companyTags) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < companyTags.size(); i++) {
			str.append(companyTags.get(i).getName());
			if(i <= companyTags.size() -1) {
				str.append(", ");
			}
		}
		return str.toString();
	}

	
	
	public static String getSearchableString(String text) {
		String pattern = "[^A-Za-z0-9]";
        
        String result = text.replaceAll(pattern, "");
        
        return result.toLowerCase();
	}

	public static String createPassword(String senha) {
		/*<magia-negra>*/
		StringBuilder str = new StringBuilder(senha);
		str.reverse();
		str.append(Integer.toHexString(senha.hashCode()));
		str.append(senha.toUpperCase());
		/*</magia-negra>*/
		return str.toString();
	}

	public static String generateUserAuthKey() {
		/*número-randomico-hexadecimal baseado na variação sismológica das placas tectônicas*/
		StringBuilder key = new StringBuilder(Long.toHexString(System.currentTimeMillis()));
		key.append(Long.toHexString(System.nanoTime()));
		return key.toString();
	}
	
}
