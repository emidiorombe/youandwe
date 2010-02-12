package br.com.yaw.util;

/**
 * Utilities method for users
 * @author Rafael nunes
 *
 */
public class UserUtilities {
	
	/**
	 * Generate a randomic password
	 * @return
	 */
	public static String generatePassword() {
	  return "genPass";	
	}
	
	public static String arrayToInClause(String... params) {
		String sql = "(";
		String aspas = "'";
		for (int i = 0; i < params.length; i++) {
			sql += aspas + params[i] + aspas;
			if(i != (params.length - 1)) {
				sql += ",";
			}
		}
		
		return sql + ")";
	}
	
	public static void main(String[] args) {
		System.out.println(arrayToInClause("1","2","3","4","5" ));
	}
}
