package br.com.yaw.servlet.action;

import javax.servlet.http.HttpServlet;

import com.google.gson.Gson;

/**
 * Métodos genéricos a todos ActionServlet
 * @author Rafael Nunes
 *
 */
public class BaseActionServlet extends HttpServlet{
	protected Gson gson = new Gson();
	
	protected String getAction(String[] tokens) {
		String action = "";
		try{
			action = tokens[2];
			return action;
		}catch(ArrayIndexOutOfBoundsException ae) {
			throw new IllegalArgumentException("url.request.inexistente");
		}
	}
	
}
