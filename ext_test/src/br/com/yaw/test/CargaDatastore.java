package br.com.yaw.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

public class CargaDatastore {
	private static final String STR_DRIVER = "org.gjt.mm.mysql.Driver";
	private static final String DATABASE = "extjs";
	private static final String IP = "127.0.0.1";
	private static final String STR_CON = "jdbc:mysql://" + IP + ":3306/" + DATABASE;
	private static final String USER = "root";
	private static final String PASSWORD = "mysql";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Class.forName(STR_DRIVER);
		Connection conn = DriverManager.getConnection(STR_CON, USER, PASSWORD);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM presidents");
		
		
		
		while(rs.next()) {
			GetMethod get = new GetMethod("http://fxcaixa-sgwt.appspot.com/ext_test");
			HttpClient client = new HttpClient();
			NameValuePair params[] = {new NameValuePair("partyId", rs.getString("IDparty")), 
									  new NameValuePair("fname", rs.getString("firstname")), 
									  new NameValuePair("lname", rs.getString("lastname")),
									  new NameValuePair("dtIni", rs.getString("tookoffice")),
									  new NameValuePair("dtFim", rs.getString("leftoffice")),
									  new NameValuePair("inc", rs.getString("income")),
									  new NameValuePair("action", "ADD_PRESIDENT")};
			get.setQueryString(params);
			client.executeMethod(get);
			
			System.out.println(get.getStatusCode() + " > " + get.getResponseBodyAsString());
			get.releaseConnection();
		}

	}

}
